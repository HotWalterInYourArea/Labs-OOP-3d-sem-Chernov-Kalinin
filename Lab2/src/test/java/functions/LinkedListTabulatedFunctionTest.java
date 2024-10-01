package functions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListTabulatedFunctionTest {
    private LinkedListTabulatedFunction t_func1;
    private LinkedListTabulatedFunction t_func2;
    private final MathFunction exp=new MathFunction() {
        @Override
        public double apply(double arg) {
            return Math.exp(arg);
        }
    };
    @BeforeEach
    void t_constructor(){
        t_func1= new LinkedListTabulatedFunction(new double[] {1.0,2.5,4.0,5.0},
        new double[] {4.0,5.0,6.0,2.0});
        t_func2= new LinkedListTabulatedFunction(exp,2.0,4.0,3);
    }
    @Test
    void getCount_ExpectEqual_4countFunc(){
        assertEquals(4,t_func1.getCount());
        assertEquals(4,t_func2.getCount());
    }
    @Test
    void getX_ExpectFirstElem_4ElemFunc(){
        assertEquals(1.0,t_func1.getX(0));
        assertEquals(2.0,t_func2.getX(0));
    }
    @Test
    void getY_ExpectFirstElem_4ElemFunc(){
        assertEquals(4.0,t_func1.getY(0));
        assertEquals(exp.apply(2.0),t_func2.getY(0),0.00001);
    }
    @Test
    void setY_ExpectUnequal_SettingFirstElem(){
        double oldYTfunc1=t_func1.getY(0);
        double oldYTfunc2=t_func2.getY(0);
        t_func1.setY(0,2.0);
        t_func2.setY(0,3.0);
        assertTrue(oldYTfunc1!=t_func1.getY(0));
        assertTrue(oldYTfunc2!=t_func2.getY(0));
    }
    @Test
    void leftBound_ExpectFirstElem_4countFunc(){
        assertEquals(1.0,t_func1.leftBound());
        assertEquals(2.0,t_func2.leftBound());
    }
    @Test
    void leftBound_ExpectLastElem_4countFunc(){
        assertEquals(5.0,t_func1.rightBound());
        assertEquals(4.0,t_func2.rightBound());
    }

}