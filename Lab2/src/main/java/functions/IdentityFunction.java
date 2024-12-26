package functions;

import ui.annotations.SimpleFunction;

@SimpleFunction(canonName ="Тождественная функция")
public class IdentityFunction implements MathFunction{
    @Override
    public double apply(double x){
        return x;
    }
}
