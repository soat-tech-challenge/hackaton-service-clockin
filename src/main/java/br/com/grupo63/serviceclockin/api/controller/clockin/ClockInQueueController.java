package br.com.grupo63.serviceclockin.api.controller.clockin;

import br.com.grupo63.serviceclockin.controller.ClockInController;
import br.com.grupo63.serviceclockin.controller.dto.ClockInControllerDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor

@Component
public class ClockInQueueController {
    private final ClockInController controller;

    @SqsListener(value = "clockIn.fifo")
    public void processClockIn(String message) throws JsonProcessingException {
        controller.save(new ObjectMapper().readValue(message, ClockInControllerDTO.class));
    }
}
