package br.com.grupo63.serviceclockin.usecase.clockin;

import br.com.grupo63.serviceclockin.config.EmailService;
import br.com.grupo63.serviceclockin.entity.clockin.ClockIn;
import br.com.grupo63.serviceclockin.gateway.clockin.IClockInGateway;
import com.amazonaws.util.json.Jackson;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ClockInUseCase implements IClockInUseCase {

    private final IClockInGateway gateway;
    private final SqsTemplate sqsTemplate;
    private final EmailService emailService;

    @Override
    public ClockIn queue(ClockIn clockIn) {
        sqsTemplate.send(sqsSendOptions ->
                sqsSendOptions
                        .queue("clockIn.fifo")
                        .payload(clockIn.toJson())
                        .messageDeduplicationId(clockIn.getUserId() + clockIn.getClockInString())
                        .messageGroupId(clockIn.getUserId() + clockIn.getClockInString()));
        return clockIn;
    }

    @Override
    public ClockIn save(ClockIn clockIn) {
        return gateway.saveAndFlush(clockIn);
    }

    @Override
    public List<ClockIn> listByUser(int userId) {
        return gateway.findByUserId(userId);
    }

    @Override
    public void generateReport(int userId, String userEmail) {
        LocalDateTime beginningOfTheLastMonth = LocalDateTime.now().minusMonths(1).withDayOfMonth(1);
        LocalDateTime endOfTheLastMonth = LocalDateTime.now().withDayOfMonth(1).minusDays(1);

        List<ClockIn> clockIns = gateway.findByPeriod(beginningOfTheLastMonth, endOfTheLastMonth);

        List<String> formattedDates = clockIns.stream().map(clockIn -> DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm").format(clockIn.getClockIn())).toList();

        StringBuilder emailContentStringBuilder = new StringBuilder();

        emailContentStringBuilder.append("Relatório referente ao mês fechado (").append(DateTimeFormatter.ofPattern("MM/yyyy").format(beginningOfTheLastMonth)).append(")").append("\n\n\n");

        formattedDates.forEach(s -> emailContentStringBuilder.append(s).append("\n\n"));

        emailService.sendSimpleMessage(userEmail, "Espelho de ponto mensal", emailContentStringBuilder.toString());
    }

}
