package operations;

import functions.IdentityFunction;
import functions.MathFunction;
import functions.SqrFunction;
import org.junit.jupiter.api.Test;

import static java.lang.Float.NaN;
import static java.lang.Float.POSITIVE_INFINITY;
import static org.junit.jupiter.api.Assertions.*;
class MiddleSteppingDifferentialOperatorTest {

    @Test
    void derive() {
        MiddleSteppingDifferentialOperator test1 = new MiddleSteppingDifferentialOperator(1);
        SqrFunction test1S = new SqrFunction();
        MathFunction test1Function = test1.derive(test1S);
        assertEquals(test1Function.apply(1), 2);
        assertEquals(test1Function.apply(5), 10);

        MiddleSteppingDifferentialOperator test2 = new MiddleSteppingDifferentialOperator(0.00000001);
        SqrFunction test2S = new SqrFunction();
        MathFunction test2Function = test2.derive(test2S);
        assertEquals(test2Function.apply(1), 2, 0.0001);
        assertEquals(test2Function.apply(5), 10, 0.0001);

        MiddleSteppingDifferentialOperator test3 = new MiddleSteppingDifferentialOperator(1);
        IdentityFunction test3S = new IdentityFunction();
        MathFunction test3Function = test3.derive(test3S);
        assertEquals(test3Function.apply(1), 1);
        assertEquals(test3Function.apply(5), 1);

        MiddleSteppingDifferentialOperator test4 = new MiddleSteppingDifferentialOperator(0.00000001);
        IdentityFunction test4S = new IdentityFunction();
        MathFunction test4Function = test4.derive(test4S);
        assertEquals(test4Function.apply(1), 1, 0.0001);
        assertEquals(test4Function.apply(5), 1, 0.0001);
    }
    @Test
    void constructor_Exception_StepIsNegative(){
        assertThrows(IllegalArgumentException.class,()-> new MiddleSteppingDifferentialOperator (-1));
    }
    @Test
    void constructor_Exception_StepIsInf(){
        assertThrows(IllegalArgumentException.class,()-> new MiddleSteppingDifferentialOperator (POSITIVE_INFINITY));
    }
    @Test
    void constructor_Exception_StepIsNan(){
        assertThrows(IllegalArgumentException.class,()-> new MiddleSteppingDifferentialOperator (NaN));
    }
    @Test
    void getStep_Equal_StepIsTwo(){
        MiddleSteppingDifferentialOperator test4 = new MiddleSteppingDifferentialOperator(2);
        assertEquals(2,test4.getStep());
    }
    @Test
    void getStep_Equal_StepIsThree(){
        MiddleSteppingDifferentialOperator test4 = new MiddleSteppingDifferentialOperator(2);
        test4.setStep(3);
        assertEquals(3,test4.getStep());
    }
}