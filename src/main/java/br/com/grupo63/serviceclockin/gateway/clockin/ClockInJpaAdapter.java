package br.com.grupo63.serviceclockin.gateway.clockin;

import br.com.grupo63.serviceclockin.entity.clockin.ClockIn;
import br.com.grupo63.serviceclockin.gateway.clockin.entity.ClockInPersistenceEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClockInJpaAdapter implements IClockInGateway {

    private final ClockInJpaRepository repository;

    @Override
    public ClockIn saveAndFlush(ClockIn status) {
        ClockInPersistenceEntity entity = new ClockInPersistenceEntity(status);

        entity = repository.save(entity);

        return entity.toModel();
    }
}
