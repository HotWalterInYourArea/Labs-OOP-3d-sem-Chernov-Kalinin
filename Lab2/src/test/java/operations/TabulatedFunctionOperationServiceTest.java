package operations;
import exceptions.InconsistentFunctionsException;
import functions.Point;
import functions.TabulatedFunction;
import functions.ArrayTabulatedFunction;
import functions.LinkedListTabulatedFunction;
import functions.factory.LinkedListTabulatedFunctionFactory;
import functions.factory.ArrayTabulatedFunctionFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;


class TabulatedFunctionOperationServiceTest {

    @Test
    void asPoints() {
        ArrayTabulatedFunctionFactory test1 = new ArrayTabulatedFunctionFactory();
        TabulatedFunction help1 = test1.create(new double[]{1, 2, 3, 4}, new double[]{1, 2, 3, 4});
        Point[] help2 = TabulatedFunctionOperationService.asPoints(help1);
        Point h1 = help2[0];
        Point h2 = help2[1];
        Point h3 = help2[2];
        Point h4 = help2[3];
        assertEquals(1,h1.x);
        assertEquals(2,h2.x);
        assertEquals(3,h3.x);
        assertEquals( 4,h4.x);
        assertEquals(1,h1.y);
        assertEquals(2,h2.y);
        assertEquals(3,h3.y);
        assertEquals( 4,h4.y);
    }
    @Test
    void multiply_ExpectArray(){
        TabulatedFunctionOperationService serviceT=new TabulatedFunctionOperationService();
        TabulatedFunction factRes=serviceT.multiply(new LinkedListTabulatedFunction((double x)->{return x/2.0;},0,1000,1001),
                          new ArrayTabulatedFunction((double x)->{return 2.0;},0,1000,1001));
        assertInstanceOf(ArrayTabulatedFunction.class,factRes);
        assertEquals(400,factRes.getY(400));
    }
    @Test
    void divide_ExpectLinkedList(){
        TabulatedFunctionOperationService serviceT=new TabulatedFunctionOperationService(new LinkedListTabulatedFunctionFactory());
        TabulatedFunction factRes=serviceT.divide(new LinkedListTabulatedFunction((double x)->{return 2.0*x;},0,1000,1001),
                new ArrayTabulatedFunction((double x)->{return 2.0;},0,1000,1001));
        assertInstanceOf(LinkedListTabulatedFunction.class,factRes);
        assertEquals(400,factRes.getY(400));
    }
    @Test
    void multiply_ExpectException_DifferentX(){
        assertThrows(InconsistentFunctionsException.class,()->{
            TabulatedFunctionOperationService serviceT=new TabulatedFunctionOperationService();
            TabulatedFunction factRes=serviceT.multiply(new LinkedListTabulatedFunction((double x)->{return 2.0*x;},0,100,1001),
                    new ArrayTabulatedFunction((double x)->{return 2.0;},0,1000,1001));
        });
    }
    @Test
    void divide_ExpectException_DifferentX(){
        assertThrows(InconsistentFunctionsException.class,()->{
            TabulatedFunctionOperationService serviceT=new TabulatedFunctionOperationService();
            TabulatedFunction factRes=serviceT.divide(new LinkedListTabulatedFunction((double x)->{return 2.0*x;},0,100,1001),
                    new ArrayTabulatedFunction((double x)->{return 2.0;},0,1000,1001));
        });
    }
    @Test
    void divide_ExpectException_DifferentNumberOfElem(){
        assertThrows(InconsistentFunctionsException.class,()->{
            TabulatedFunctionOperationService serviceT=new TabulatedFunctionOperationService();
            TabulatedFunction factRes=serviceT.divide(new LinkedListTabulatedFunction((double x)->{return 2.0*x;},0,100,1001),
                    new ArrayTabulatedFunction((double x)->{return 2.0;},0,1000,100));
        });
    }
    @Test
    void multiply_ExpectException_DifferentNumberOfElem(){
        assertThrows(InconsistentFunctionsException.class,()->{
            TabulatedFunctionOperationService serviceT=new TabulatedFunctionOperationService();
            TabulatedFunction factRes=serviceT.multiply(new LinkedListTabulatedFunction((double x)->{return 2.0*x;},0,100,1001),
                    new ArrayTabulatedFunction((double x)->{return 2.0;},0,1000,100));
        });
    }

}