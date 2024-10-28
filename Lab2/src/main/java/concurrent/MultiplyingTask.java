package concurrent;

import functions.TabulatedFunction;

public class MultiplyingTask implements Runnable{
    private final TabulatedFunction funcToMultiply;
    public MultiplyingTask(TabulatedFunction func){
        this.funcToMultiply=func;
    }
    @Override
    public void run(){
        for(int i=0;i<funcToMultiply.getCount();i++){
            synchronized (funcToMultiply) {
                double cur_Y=funcToMultiply.getY(i);
                funcToMultiply.setY(i, cur_Y * 2);
            }
            System.out.println("Thread" + Thread.currentThread().getName() + " completed the task");
        }
    }
}
