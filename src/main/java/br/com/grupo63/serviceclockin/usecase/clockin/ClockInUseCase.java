package br.com.grupo63.serviceclockin.usecase.clockin;

import br.com.grupo63.serviceclockin.config.EmailService;
import br.com.grupo63.serviceclockin.controller.dto.ClockinGroupedByDateDTO;
import br.com.grupo63.serviceclockin.entity.clockin.ClockIn;
import br.com.grupo63.serviceclockin.gateway.clockin.IClockInGateway;
import br.com.grupo63.serviceclockin.presenter.ClockInPresenter;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
        LocalDateTime beginningOfTheCurrentMonth = LocalDateTime.now().withDayOfMonth(1);
        LocalDateTime endOfTheCurrentMonth = LocalDateTime.now().plusMonths(1).minusDays(1);

        return gateway.findByUserIdAndPeriod(userId, beginningOfTheCurrentMonth, endOfTheCurrentMonth);
    }

    @Override
    public void generateReport(int userId, String userEmail) {
        LocalDateTime beginningOfTheLastMonth = LocalDateTime.now().minusMonths(1).withDayOfMonth(1);
        LocalDateTime endOfTheLastMonth = LocalDateTime.now().withDayOfMonth(1).minusDays(1);

        List<ClockIn> clockIns = gateway.findByUserIdAndPeriod(userId, beginningOfTheLastMonth, endOfTheLastMonth);

        List<ClockinGroupedByDateDTO> groupedByDateDTOS = ClockInPresenter.groupByDate(clockIns);

        StringBuilder emailContentStringBuilder = new StringBuilder();

        emailContentStringBuilder.append("Relatório referente ao mês fechado (").append(DateTimeFormatter.ofPattern("MM/yyyy").format(beginningOfTheLastMonth)).append(")").append("\n\n\n");

        groupedByDateDTOS.forEach(dto -> {
            emailContentStringBuilder.append("Dia: ");
            emailContentStringBuilder.append(DateTimeFormatter.ofPattern("dd/MM/yyyy").format(dto.getDate()));
            emailContentStringBuilder.append("\n\n");
            emailContentStringBuilder.append("Batimentos de ponto: ");

            for (int i = 0; i < dto.getClockIns().size(); i++) {
                emailContentStringBuilder.append(DateTimeFormatter.ofPattern("HH:mm:ss").format(dto.getClockIns().get(i)));
                emailContentStringBuilder.append(i < dto.getClockIns().size() - 1 ? ", " : "");
            }

            emailContentStringBuilder.append("\n\n");
            emailContentStringBuilder.append("Tempo trabalhado: ");
            emailContentStringBuilder.append(DateTimeFormatter.ofPattern("HH:mm:ss").format(dto.getWorkedMinutes()));
            emailContentStringBuilder.append("\n\n");
            emailContentStringBuilder.append("---");
            emailContentStringBuilder.append("\n\n");
        });

        emailService.sendSimpleMessage(userEmail, "Espelho de ponto mensal", emailContentStringBuilder.toString());
    }

}
