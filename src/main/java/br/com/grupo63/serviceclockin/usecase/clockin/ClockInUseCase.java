package br.com.grupo63.serviceclockin.usecase.clockin;

import br.com.grupo63.serviceclockin.entity.clockin.ClockIn;
import br.com.grupo63.serviceclockin.gateway.clockin.IClockInGateway;
import com.amazonaws.util.json.Jackson;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Service
public class ClockInUseCase implements IClockInUseCase {

    private final IClockInGateway gateway;
    private final SqsTemplate sqsTemplate;

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

}
