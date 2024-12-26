package ui.dto;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class FunctionTabulatedFunctionRequestDTO {
    private String functionName;
    private double from;
    private double to;
    private int amountOfPoints;
}
