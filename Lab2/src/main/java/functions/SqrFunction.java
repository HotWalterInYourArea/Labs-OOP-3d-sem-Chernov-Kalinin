package functions;

import ui.annotations.SimpleFunction;

import java.io.Serial;

import static java.lang.Math.pow;
@SimpleFunction(canonName = "Квадратичная функция")
public class SqrFunction implements MathFunction {
    @Serial
    private static final long serialVersionUID = 1612461680788184242L;

    public double apply(double x) {
        return pow(x, 2);
    }

}