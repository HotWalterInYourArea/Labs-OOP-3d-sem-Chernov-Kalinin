package functions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serial;
import java.util.List;

public class CompositeFunction implements MathFunction {
    @Serial
    private static final long serialVersionUID = 7592473532529642199L;
    private final MathFunction firstFunction;
    private final MathFunction secondFunction;
    @JsonCreator
    public CompositeFunction(@JsonProperty("firstFunction") MathFunction f1,@JsonProperty("secondFunction") MathFunction f2){
        this.firstFunction=f1;
        this.secondFunction=f2;
    }
    @Override
    public double apply(double x)
    {
        return secondFunction.apply(firstFunction.apply(x));
    }
    @Override
    public String Name() {
        return this.getClass().getSimpleName() + " " + firstFunction.Name() + " " +secondFunction.Name();
    }
    public static CompositeFunction createCompositeFunctionFromList(List<MathFunction> functionList) {
        CompositeFunction function = new CompositeFunction(functionList.get(0), functionList.get(1));
        for (int i = 2; i < functionList.size(); i++) {
            function = new CompositeFunction(function, functionList.get(i));
        }
        return function;
    }
}
