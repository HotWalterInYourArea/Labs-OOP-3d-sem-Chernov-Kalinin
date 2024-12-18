package functions;
//import org.testng.annotations.Test;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SqrFunctionTest {
    SqrFunction c = new SqrFunction();
    @Test
    public void apply() {

        double[] expected = new double[]{6.60117372, 87.6977934, 6241, 14641};

        double[] actual = new double[4];
        actual[0] = c.apply(2.569274945);
        actual[1] = c.apply(-9.36471);
        actual[2] = c.apply(-79);
        actual[3] = c.apply(121);

        assertArrayEquals(expected,actual,0.00001);

    }
}