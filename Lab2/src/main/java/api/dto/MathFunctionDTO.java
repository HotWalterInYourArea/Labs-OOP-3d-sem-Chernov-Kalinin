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
}
