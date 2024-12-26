package functions.factory;

import functions.ArrayTabulatedFunction;
import functions.MathFunction;
import functions.StrictTabulatedFunction;
import functions.TabulatedFunction;

public interface TabulatedFunctionFactory {
    TabulatedFunction create(double[] xValues,double[] yValues);
    default TabulatedFunction createStrictTabulatedFunction(double[] xValues,double[] yValues){
        return new StrictTabulatedFunction(this.create(xValues,yValues));
    }
    TabulatedFunction create(MathFunction source, double xFrom, double xTo, int count);
}
