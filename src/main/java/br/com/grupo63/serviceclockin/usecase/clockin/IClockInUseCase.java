package br.com.grupo63.serviceclockin.usecase.clockin;

import br.com.grupo63.serviceclockin.entity.clockin.ClockIn;

public interface IClockInUseCase {

    ClockIn queue(ClockIn clockIn);

    ClockIn save(ClockIn clockIn);

}
