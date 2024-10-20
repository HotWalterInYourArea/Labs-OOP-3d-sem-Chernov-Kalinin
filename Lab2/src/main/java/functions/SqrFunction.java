package functions;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class SqrFunction implements MathFunction {
    public double apply(double x) {
        return sqrt(x);
    }

}