package br.com.grupo63.serviceclockin.gateway.clockin;

import br.com.grupo63.serviceclockin.entity.clockin.ClockIn;

public interface IClockInGateway {

    ClockIn saveAndFlush(ClockIn entity);

}
