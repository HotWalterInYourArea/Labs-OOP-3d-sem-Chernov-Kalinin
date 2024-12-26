package ui.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateComplexFunctionDTO {
    @JsonProperty("bytelist")
    String sessionByteFunctions;
    @JsonProperty("functionlist")
    String sessionFunctionNames;
    @JsonProperty("functions")
    String functionsToApply;
}
