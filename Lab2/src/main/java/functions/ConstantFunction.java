package functions;

public class ConstantFunction implements MathFunction {

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