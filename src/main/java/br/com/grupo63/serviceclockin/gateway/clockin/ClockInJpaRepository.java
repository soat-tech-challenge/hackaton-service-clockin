package br.com.grupo63.serviceclockin.gateway.clockin;

import br.com.grupo63.serviceclockin.gateway.clockin.entity.ClockInPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ClockInJpaRepository extends JpaRepository<ClockInPersistenceEntity, String> {

    List<ClockInPersistenceEntity> findByUserId(int userId);

    @Query("SELECT c FROM ClockInPersistenceEntity c WHERE c.userId = :userId AND c.clockIn BETWEEN :start AND :end")
    List<ClockInPersistenceEntity> findByUserIdAndPeriod(int userId, LocalDateTime start, LocalDateTime end);

}
