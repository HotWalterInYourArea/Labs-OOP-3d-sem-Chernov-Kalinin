package concurrent;

import functions.LinkedListTabulatedFunction;
import functions.UnitFunction;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MultiplyingTaskTest {
    @Test
     void funcToMultiply(){
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
        for(int i=0;i<funcToMultiply.getCount();i++){
            assertEquals(1024.0,funcToMultiply.getY(i));
        }
    }
}
