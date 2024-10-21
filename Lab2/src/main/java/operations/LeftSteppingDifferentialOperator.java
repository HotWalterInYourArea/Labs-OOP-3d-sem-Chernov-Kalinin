/**package operations;

import functions.MathFunction;
import functions.Point;

import static java.lang.Double.POSITIVE_INFINITY;

public class LeftSteppingDifferentialOperator extends SteppingDifferentialOperator {

    public LeftSteppingDifferentialOperator(double step) throws IllegalArgumentException{
        if(step <= 0){
            throw new IllegalArgumentException();
        }
        if(step == POSITIVE_INFINITY){
            throw new IllegalArgumentException();
        }
        if(Double.isNaN(step)){
            throw new IllegalArgumentException();
        }
        this.step = step;
        SteppingDifferentialOperator steppingDifferentialOperator = new LeftSteppingDifferentialOperator(step);
    }


    @Override
    public MathFunction derive(MathFunction function) {

        MathFunction derivative = new MathFunction() {
            @Override
            public Point apply(){


            }
        };
        return derivative;
    }
}
**/