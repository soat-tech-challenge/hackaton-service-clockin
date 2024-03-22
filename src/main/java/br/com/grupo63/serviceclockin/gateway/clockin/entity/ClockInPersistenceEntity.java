package br.com.grupo63.serviceclockin.gateway.clockin.entity;

import br.com.grupo63.serviceclockin.entity.clockin.ClockIn;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "clo_clock_in", indexes = {})
public class ClockInPersistenceEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Access(AccessType.FIELD)
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "clock_in", nullable = false)
    private LocalDateTime clockIn;


    public ClockInPersistenceEntity(ClockIn clockIn) {
        this.userId = clockIn.getUserId();
        this.clockIn = clockIn.getClockIn();
    }

    public ClockIn toModel() {
        return new ClockIn(this.getId(),
                this.getUserId(),
                this.getClockIn());
    }
}
