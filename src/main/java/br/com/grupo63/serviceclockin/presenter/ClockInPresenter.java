package br.com.grupo63.serviceclockin.presenter;

import br.com.grupo63.serviceclockin.controller.dto.ClockInControllerDTO;
import br.com.grupo63.serviceclockin.controller.dto.ClockinGroupedByDateDTO;
import br.com.grupo63.serviceclockin.entity.clockin.ClockIn;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class ClockInPresenter {

    public static ClockInControllerDTO toDto(ClockIn entity) {
        ClockInControllerDTO dto = new ClockInControllerDTO();

        dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        dto.setClockIn(entity.getClockInString());

        return dto;
    }

    public static List<ClockinGroupedByDateDTO> groupByDate(List<ClockIn> entities) {
        List<ClockinGroupedByDateDTO> dtos = new ArrayList<>();

        Map<LocalDate, List<LocalDateTime>> timesMap = new HashMap<>();

        entities.forEach(clockIn -> {
            LocalDate currentDate = LocalDate.from(clockIn.getClockIn());

            timesMap.computeIfAbsent(currentDate, key -> new ArrayList<>())
                    .add(clockIn.getClockIn());
        });

        for (Map.Entry<LocalDate, List<LocalDateTime>> entry : timesMap.entrySet()) {
            List<LocalDateTime> sortedTimes = entry.getValue().stream().sorted().toList();

            ClockinGroupedByDateDTO dto = new ClockinGroupedByDateDTO();

            dto.setDate(entry.getKey());
            dto.setClockIns(sortedTimes);

            int workedMinutes = 0;

            for (int i = 0; i < sortedTimes.size(); i++) {
                if (i % 2 != 0) {
                    Duration difference = Duration.between(sortedTimes.get(i), sortedTimes.get(i - 1));

                    workedMinutes += (int) Math.abs(difference.toMinutes());
                }
            }

//            double hours = workedMinutes / 60.0;
//            double remainingMinutes = workedMinutes % 60.0;

//            LocalDateTime workedTime = LocalDateTime.now().withHour((int) hours).withMinute((int) remainingMinutes);

            dto.setWorkedMinutes(workedMinutes);

            dtos.add(dto);
        }

        return dtos;
    }

}
