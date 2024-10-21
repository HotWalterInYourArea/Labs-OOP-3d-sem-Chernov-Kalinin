package operations;
import functions.Point;
import functions.TabulatedFunction;
import functions.factory.ArrayTabulatedFunctionFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


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
        assertEquals(h1.x, 1);
        assertEquals(h2.x, 2);
        assertEquals(h3.x, 3);
        assertEquals(h4.x, 4);
        assertEquals(h1.y, 1);
        assertEquals(h1.y, 2);
        assertEquals(h1.y, 3);
        assertEquals(h1.y, 4);





    }

}