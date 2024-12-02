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
}
