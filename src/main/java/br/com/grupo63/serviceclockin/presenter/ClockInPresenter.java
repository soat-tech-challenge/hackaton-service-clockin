package br.com.grupo63.serviceclockin.presenter;

import br.com.grupo63.serviceclockin.controller.dto.ClockInControllerDTO;
import br.com.grupo63.serviceclockin.entity.clockin.ClockIn;

import java.time.format.DateTimeFormatter;

public class ClockInPresenter {

    public static ClockInControllerDTO toDto(ClockIn entity) {
        ClockInControllerDTO dto = new ClockInControllerDTO();

        dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        dto.setClockIn(entity.getClockInString());

        return dto;
    }

}
