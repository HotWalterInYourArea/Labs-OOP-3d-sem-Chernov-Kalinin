package operations;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.*;
import java.util.List;

import concurrent.IntegralTask;
import functions.TabulatedFunction;

public class TabulatedIntegralFunctional {
    private final ExecutorService exec;
    private final int numberOfThreads;
    public TabulatedIntegralFunctional(){
        this.numberOfThreads=Runtime.getRuntime().availableProcessors()-1;
        this.exec= Executors.newFixedThreadPool(numberOfThreads);
    }
    public TabulatedIntegralFunctional(int numThreads){
        if (numThreads<0) throw new IllegalArgumentException();
        this.numberOfThreads=numThreads;
        this.exec= Executors.newFixedThreadPool(numberOfThreads);
    }
    public double integrate(TabulatedFunction function,double lowerBound,double upperBound) throws ExecutionException, InterruptedException{
        double delta=(upperBound-lowerBound)/numberOfThreads;
        double integral=0;
        List<Future<Double>> futureList=new ArrayList<>();
        for(int i=0;i<numberOfThreads;i++){
            double lower=lowerBound+i*delta;
            double upper=lower+delta*i;
            IntegralTask task=new IntegralTask(function,lower,upper);
            futureList.add(exec.submit(task));
        }
        for(Future<Double> point: futureList){
            integral+=point.get();
        }
        exec.shutdown();
        return integral;
    }
}
