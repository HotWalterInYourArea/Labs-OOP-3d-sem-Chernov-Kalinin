package functions.factory;

import functions.ArrayTabulatedFunction;
import functions.TabulatedFunction;

public class ArrayTabulatedFunctionFactory implements TabulatedFunctionFactory {
    @Override
    public ArrayTabulatedFunction create(double[] xValues, double[] yValues){
        return new ArrayTabulatedFunction(xValues,yValues);
    }
}
