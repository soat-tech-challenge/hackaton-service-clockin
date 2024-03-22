package br.com.grupo63.serviceclockin.adapter;

import br.com.grupo63.serviceclockin.controller.dto.ClockInControllerDTO;
import br.com.grupo63.serviceclockin.entity.clockin.ClockIn;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ClockInAdapter {

    public static void fillEntity(ClockInControllerDTO clockInControllerDTO, ClockIn entity) {
        entity.setUserId(clockInControllerDTO.getUserId());
        entity.setClockIn(LocalDateTime.parse(clockInControllerDTO.getClockIn(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
    }

    public static void fillEntity(int userId, ClockIn entity) {
        entity.setUserId(userId);
        entity.setClockIn(LocalDateTime.now());
    }

}
