package br.com.grupo63.serviceclockin.entity.clockin;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClockIn {

    int id;
    int userId;
    LocalDateTime clockIn;

    public ClockIn(int userId, LocalDateTime clockIn) {
        this.userId = userId;
        this.clockIn = clockIn;
    }

    public String getClockInString() {
        return clockIn.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    public String toJson() {
        return "{\"userId\":" + userId + ",\"clockIn\":\"" + clockIn.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "\"}";
    }
}
