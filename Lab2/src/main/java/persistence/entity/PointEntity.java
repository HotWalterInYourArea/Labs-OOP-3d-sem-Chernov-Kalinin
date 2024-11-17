package persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
@Entity
@Table(name="points")
@IdClass(PointKey.class)
public class PointEntity {
    @Id
    @Column(nullable = false)
    Double x;
    Double y;
    @ManyToOne
    @Id
    @JoinColumn(name="OWNER_ID", referencedColumnName="hash_id")
    private MathFunctionEntity mathFunctionEntity;
}
