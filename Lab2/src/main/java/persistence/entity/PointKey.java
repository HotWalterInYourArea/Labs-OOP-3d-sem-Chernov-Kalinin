package persistence.entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PointKey implements Serializable {
    private Double x;
    private Long mathFunctionEntity;
    @Override
    public boolean equals(Object key){
        if(this == key)return true;
        return Double.compare(((PointKey) key).x,this.x )==0 && Long.compare(((PointKey) key).mathFunctionEntity,this.mathFunctionEntity)==0;
    }
}
