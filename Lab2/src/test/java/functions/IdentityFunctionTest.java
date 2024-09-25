package functions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class IdentityFunctionTest {
    private IdentityFunction t_func;
    @BeforeEach
    void tFunConstructor()
    {
        t_func=new IdentityFunction();
    }
    @Test
    void applyToInt_ExpectEqual_StateThree()
    {
        double result = t_func.apply(3);
        assertEquals(3,result);

    }
    @Test
    void applyToDouble_ExpectEqual_State6PointNine()
    {
        double result = t_func.apply(6.9);
        assertEquals(6.9,result);

    }
}