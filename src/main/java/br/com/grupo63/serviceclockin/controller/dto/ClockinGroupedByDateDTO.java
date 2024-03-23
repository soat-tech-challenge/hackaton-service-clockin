package br.com.grupo63.serviceclockin.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClockinGroupedByDateDTO {

    private LocalDate date;
    private List<LocalDateTime> clockIns;
    private LocalDateTime workedTime;
    private String workedTimeFormatted;

}
