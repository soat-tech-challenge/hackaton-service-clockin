package br.com.grupo63.serviceclockin.gateway.clockin;

import br.com.grupo63.serviceclockin.entity.clockin.ClockIn;
import br.com.grupo63.serviceclockin.gateway.clockin.entity.ClockInPersistenceEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

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

    @Override
    public List<ClockIn> findByUserId(int userId) {
        return repository.findByUserId(userId).
                stream().map(ClockInPersistenceEntity::toModel).toList();
    }

    @Override
    public List<ClockIn> findByPeriod(LocalDateTime start, LocalDateTime end) {
        return repository.findByPeriod(start, end).stream().map(ClockInPersistenceEntity::toModel).toList();
    }

}
