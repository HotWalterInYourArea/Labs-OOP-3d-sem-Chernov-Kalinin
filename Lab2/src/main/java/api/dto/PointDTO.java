package api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PointDTO {
    @JsonProperty("x")
    private Double x;
    @JsonProperty("y")
    private Double y;
    private Long mathId;
    @Override
    public boolean equals(Object obj){
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        final PointDTO pointDTO = (PointDTO) obj;
        return this.x.equals(pointDTO.getX()) && this.y.equals(pointDTO.getY())&&this.mathId.equals(pointDTO.getMathId());
    }
}
