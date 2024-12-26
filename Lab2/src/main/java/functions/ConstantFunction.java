package functions;

import java.io.Serial;

public class ConstantFunction implements MathFunction {

    @Serial
    private static final long serialVersionUID = -4689371820401686270L;
    private final double C;

    public ConstantFunction(double C) {
        this.C = C;
    }

    public double apply(double x) {
        return C;
    }
    public double getC() {
        return C;
    }
    @Override
    public int HashName() {
        return this.Name().hashCode() + Double.valueOf(this.C).hashCode();
    }
}