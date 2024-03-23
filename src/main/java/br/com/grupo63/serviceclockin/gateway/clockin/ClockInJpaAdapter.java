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
    public List<ClockIn> findByUserIdAndPeriod(int userId, LocalDateTime start, LocalDateTime end) {
        return repository.findByUserIdAndPeriod(userId, start, end).
                stream().map(ClockInPersistenceEntity::toModel).toList();
    }

}
