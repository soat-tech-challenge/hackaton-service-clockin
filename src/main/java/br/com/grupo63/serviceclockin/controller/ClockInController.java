package br.com.grupo63.serviceclockin.controller;

import br.com.grupo63.serviceclockin.adapter.ClockInAdapter;
import br.com.grupo63.serviceclockin.controller.dto.ClockInControllerDTO;
import br.com.grupo63.serviceclockin.entity.clockin.ClockIn;
import br.com.grupo63.serviceclockin.presenter.ClockInPresenter;
import br.com.grupo63.serviceclockin.usecase.clockin.ClockInUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ClockInController {

    private final ClockInUseCase statusUseCase;

    public ClockInControllerDTO queue(int userId) {
        ClockIn clockIn = new ClockIn();
        ClockInAdapter.fillEntity(userId, clockIn);
        clockIn = statusUseCase.queue(clockIn);
        return ClockInPresenter.toDto(clockIn);
    }

    public ClockInControllerDTO save(ClockInControllerDTO clockInControllerDTO) {
        ClockIn clockIn = new ClockIn();
        ClockInAdapter.fillEntity(clockInControllerDTO, clockIn);
        clockIn = statusUseCase.save(clockIn);
        return ClockInPresenter.toDto(clockIn);
    }

}
