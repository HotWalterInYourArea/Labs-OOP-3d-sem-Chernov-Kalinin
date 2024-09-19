package functions;

public class IdentityFunction implements MathFunction{
    @Override
    public double apply(double arg){
        return arg;
    }
}
