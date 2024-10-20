package concurrent;
import functions.TabulatedFunction;

import java.util.concurrent.Callable;

public class IntegralTask implements Callable<Double>{
    private final double lowerBound;
    private final double upperBound;
    private final TabulatedFunction intFunc;
    private int sections=10000;
    public IntegralTask(TabulatedFunction func,double lower, double upper, int secs){
        if(func==null)throw new NullPointerException();
        if(secs<=0)throw new IllegalArgumentException();
        if(lower>upper) {
            double temp = lower;
            lower = upper;
            upper=lower;
        }
        this.lowerBound=lower;
        this.upperBound=upper;
        this.intFunc=func;
        this.sections=secs;
    }
    public IntegralTask(TabulatedFunction func,double lower, double upper){
        if(func==null)throw new NullPointerException();
        if(lower>upper) {
            double temp = lower;
            lower = upper;
            upper=temp;
        }
        this.lowerBound=lower;
        this.upperBound=upper;
        this.intFunc=func;
    }
    private double TrapezoidMethod(int sections){
        double totalArea=0.0;
        double sectionWidth =(upperBound-lowerBound)/sections;
        for(int i=0;i<sections;i++){
            double x0=lowerBound+i* sectionWidth;
            double x1=lowerBound+(i+1)* sectionWidth;
            double area =(intFunc.apply(x0)+intFunc.apply(x1))*sectionWidth/2;
            totalArea+=area;
        }
        return totalArea;
    }
    @Override
    public Double call(){
        return TrapezoidMethod(sections);
    }
}
