package persistence.entity;

import api.dto.PointDTO;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="functions")
@FieldDefaults(level=AccessLevel.PRIVATE)
public class MathFunctionEntity {
    @Id
    @Column(name="hash_id")
    Long function_id;
    @Column(nullable = false)
    String name;
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL,orphanRemoval = true)
    List<PointEntity> points;
    Instant created_at;
    Instant updated_at;
    @PrePersist
    public void pre_persist(){
        points.sort(new Comparator<PointEntity>() {
            @Override
            public int compare(PointEntity o1, PointEntity o2) {
                return Double.compare(o1.getX(), o2.getX());
            }
        });
        created_at=Instant.now();
    }
    @PreUpdate
    public void pre_update(){
        points.sort(new Comparator<PointEntity>() {
            @Override
            public int compare(PointEntity o1, PointEntity o2) {
                return Double.compare(o1.getX(), o2.getX());
            }
        });
        updated_at=Instant.now();
    }
    public void addPoint(PointEntity pointEntity){
        points.add(pointEntity);
    }
    public void removeLastPoint(){
        points.remove(points.getLast());
    }
}
