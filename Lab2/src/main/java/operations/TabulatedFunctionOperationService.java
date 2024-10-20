package operations;

import functions.TabulatedFunction;

public class TabulatedFunctionOperationService {

    @FunctionalInterface
    private interface BiOperation{
        double apply(double u,double v);
    }
    TabulatedFunction doOperation(TabulatedFunction a,TabulatedFunction b, BiOperation op){return null;};
    public TabulatedFunction multiply (TabulatedFunction a,TabulatedFunction b){
        return doOperation(a,b,(u,v)->u*v);
    }
    public TabulatedFunction divide (TabulatedFunction a,TabulatedFunction b){
        return doOperation(a,b,(u,v)->u/v);
    }
}
