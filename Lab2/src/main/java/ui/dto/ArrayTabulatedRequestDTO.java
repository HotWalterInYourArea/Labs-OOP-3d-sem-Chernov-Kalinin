package ui.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.util.Arrays;
@Getter
@Setter
@NoArgsConstructor
public class ArrayTabulatedRequestDTO {
    private double[] xValues;
    private double[] yValues;
    @JsonCreator
    public ArrayTabulatedRequestDTO(@JsonProperty(value = "xValues") double[] xValues, @JsonProperty(value = "yValues") double[] yValues) throws IllegalArgumentException {
        this.xValues = Arrays.copyOf(xValues, xValues.length);
        this.yValues = Arrays.copyOf(yValues, yValues.length);
    }
}
