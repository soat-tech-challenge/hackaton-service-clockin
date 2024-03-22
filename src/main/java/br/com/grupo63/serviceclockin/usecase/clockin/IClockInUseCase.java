package br.com.grupo63.serviceclockin.usecase.clockin;

import br.com.grupo63.serviceclockin.entity.clockin.ClockIn;

import java.util.List;

public interface IClockInUseCase {

    ClockIn queue(ClockIn clockIn);

    ClockIn save(ClockIn clockIn);

    List<ClockIn> listByUser(int userId);

    void generateReport(int userId, String userEmail);

}
