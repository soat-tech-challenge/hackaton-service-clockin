package br.com.grupo63.serviceclockin.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClockInControllerDTO {

    private int id;
    private int userId;
    private String clockIn;

}
