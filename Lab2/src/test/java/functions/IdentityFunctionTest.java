package functions;
import functions.IdentityFunction;
import org.junit.jupiter.api.*;

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
        Assertions.assertEquals(3,result);

    }
    @Test
    void applyToDouble_ExpectEqual_State6PointNine()
    {
        double result = t_func.apply(6.9);
        Assertions.assertEquals(6.9,result);

    }
}