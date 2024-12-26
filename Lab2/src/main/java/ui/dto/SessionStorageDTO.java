package ui.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
public class SessionStorageDTO {
    @JsonProperty("bytelist")
    String sessionByteFunctions;
    @JsonProperty("functionlist")
    String sessionFunctionNames;
}
