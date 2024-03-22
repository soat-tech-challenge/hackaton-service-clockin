package br.com.grupo63.serviceclockin.gateway.clockin;

import br.com.grupo63.serviceclockin.entity.clockin.ClockIn;

import java.time.LocalDateTime;
import java.util.List;

public interface IClockInGateway {

    ClockIn saveAndFlush(ClockIn entity);

    List<ClockIn> findByUserId(int userId);

    List<ClockIn> findByPeriod(LocalDateTime start, LocalDateTime end);

}
