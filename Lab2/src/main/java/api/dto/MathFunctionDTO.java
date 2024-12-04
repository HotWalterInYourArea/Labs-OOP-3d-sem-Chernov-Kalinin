package api.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.Instant;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class MathFunctionDTO {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    private Instant created_at;
    private Instant updated_at;
    @JsonProperty("points")
    private List<PointDTO> listOfPoints;
    @Override
    public boolean equals(Object obj){
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        final MathFunctionDTO mathDTO = (MathFunctionDTO) obj;
        if(this.listOfPoints==null || mathDTO.listOfPoints==null)return this.name.equals(mathDTO.getName()) && this.id.equals(mathDTO.getId());
        return this.name.equals(mathDTO.getName()) && this.id.equals(mathDTO.getId())&&this.listOfPoints.equals(mathDTO.getListOfPoints());
    }
}
