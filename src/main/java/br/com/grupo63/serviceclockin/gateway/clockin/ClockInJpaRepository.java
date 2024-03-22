package br.com.grupo63.serviceclockin.gateway.clockin;

import br.com.grupo63.serviceclockin.gateway.clockin.entity.ClockInPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClockInJpaRepository extends JpaRepository<ClockInPersistenceEntity, String> {

}
