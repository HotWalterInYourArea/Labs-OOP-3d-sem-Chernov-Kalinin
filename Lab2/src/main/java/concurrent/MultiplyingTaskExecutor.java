package concurrent;

import functions.LinkedListTabulatedFunction;
import functions.UnitFunction;

import java.util.ArrayList;

public class MultiplyingTaskExecutor {
    public static  void main(String[] args){
        LinkedListTabulatedFunction funcToMultiply=new LinkedListTabulatedFunction(new UnitFunction(),1,1000,1001);
        ArrayList<Thread> threadList=new ArrayList<>();
        for(int i=0;i<10;i++){
            Thread thread=new Thread(new MultiplyingTask(funcToMultiply));
            threadList.add(thread);
        }
        for(Thread thread:threadList){
            thread.start();
        }
        while(!threadList.isEmpty()){
            threadList.removeIf(thread->!thread.isAlive());
        }
        System.out.println(funcToMultiply);
    }
}
