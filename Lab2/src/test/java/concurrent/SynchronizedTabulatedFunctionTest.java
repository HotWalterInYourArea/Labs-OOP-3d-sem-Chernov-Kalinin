package concurrent;

import exceptions.ArrayIsNotSortedException;
import functions.ArrayTabulatedFunction;
import functions.LinkedListTabulatedFunction;
import functions.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class SynchronizedTabulatedFunctionTest {
        private  SynchronizedTabulatedFunction synchronizedLinked;
        private  SynchronizedTabulatedFunction synchronizedArray;
        @BeforeEach
        void t_constructor() {
            LinkedListTabulatedFunction linked = new LinkedListTabulatedFunction(new double[]{1, 2, 3}, new double[]{1, 2, 3});
            ArrayTabulatedFunction array = new ArrayTabulatedFunction(new double[]{1, 2, 3}, new double[]{1, 2, 3});
            synchronizedLinked = new SynchronizedTabulatedFunction(linked);
            synchronizedArray = new SynchronizedTabulatedFunction(array);
        }
        @Test
        void constructor_NotSortedException_ArraySynchronized(){
            assertThrows(ArrayIsNotSortedException.class,()->new SynchronizedTabulatedFunction(new ArrayTabulatedFunction(new double[]{1, 3, 3}, new double[]{1, 2, 3})));
        }
        @Test
        void getCount_ExpectThree_LinkedStrict() {
            assertEquals(3, synchronizedLinked.getCount());
        }
        @Test
        void getCount_ExpectThree_ArrayStrict() {
            assertEquals(3, synchronizedArray.getCount());
        }
        @Test
        void getX_ExpectOne_LinkedStrict(){
            assertEquals(1, synchronizedLinked.getX(0));
        }
        @Test
        void getX_ExpectOne_ArrayStrict(){
            assertEquals(1, synchronizedArray.getX(0));
        }
        @Test
        void getX_ExpectException_LinkedSynchronizedOutOfBounds(){
            assertThrows(IllegalArgumentException.class,()-> synchronizedLinked.getX(3));
        }
        @Test
        void getX_ExpectException_LinkedSynchronizedLessThanZero(){
            assertThrows(IllegalArgumentException.class,()-> synchronizedLinked.getX(-1));
        }
        @Test
        void getY_ExpectOne_LinkedStrict(){
            assertEquals(1, synchronizedLinked.getY(0));
        }
        @Test
        void getY_ExpectOne_ArrayStrict(){
            assertEquals(1, synchronizedArray.getY(0));
        }
        @Test
        void getY_ExpectException_LinkedSynchronizedOutOfBounds(){
            assertThrows(IllegalArgumentException.class,()-> synchronizedLinked.getY(3));
        }
        @Test
        void getY_ExpectException_LinkedSynchronizedLessThanZero(){
            assertThrows(IllegalArgumentException.class,()-> synchronizedLinked.getY(-1));
        }
        @Test
        void setY_ExpectZero_LinkedSynchronized(){
            synchronizedLinked.setY(0,0);
            assertEquals(0, synchronizedLinked.getY(0));
        }
        @Test
        void setY_ExpectZero_ArraySynchronized(){
            synchronizedArray.setY(0,0);
            assertEquals(0, synchronizedArray.getY(0));
        }

        @Test
        void setY_ExpectException_LinkedSynchronizedOutOfBounds(){
            assertThrows(IllegalArgumentException.class,()-> synchronizedLinked.setY(3,0));
        }
        @Test
        void setY_ExpectException_LinkedSynchronizedLessThanZero(){
            assertThrows(IllegalArgumentException.class,()-> synchronizedLinked.setY(-1,0));
        }
        @Test
        void indexOfX_ExpectZero_LinkedSynchronized(){
            assertEquals(0, synchronizedLinked.indexOfX(1));
        }
        @Test
        void indexOfX_ExpectZero_ArraySynchronized(){
            assertEquals(0, synchronizedArray.indexOfX(1));
        }
        @Test
        void indexOfX_ExpectNotFound_LinkedSynchronized(){
            assertEquals(-1, synchronizedLinked.indexOfX(-1));
        }
        @Test
        void indexOfX_ExpectNotFound_ArraySynchronized(){
            assertEquals(-1, synchronizedArray.indexOfX(-1));
        }
        @Test
        void indexOfY_ExpectZero_LinkedSynchronized(){
            assertEquals(0, synchronizedLinked.indexOfY(1));
        }
        @Test
        void indexOfY_ExpectZero_ArraySynchronized(){
            assertEquals(0, synchronizedArray.indexOfY(1));
        }
        @Test
        void leftBound_ExpectOne_LinkedSynchronized(){
            assertEquals(1, synchronizedLinked.leftBound());
        }
        @Test
        void leftBound_ExpectOne_ArraySynchronized(){
            assertEquals(1, synchronizedArray.leftBound());
        }
        @Test
        void rightBound_ExpectThree_LinkedSynchronized(){
            assertEquals(3, synchronizedLinked.rightBound());
        }
        @Test
        void rightBound_ExpectThree_ArraySynchronized(){
            assertEquals(3, synchronizedArray.rightBound());
        }
        @Test
        void apply_ExpectOne_LinkedSynchronizedFound(){
            assertEquals(1, synchronizedLinked.apply(1));
        }
        @Test
        void apply_ExpectOne_ArraySynchronizedFound(){
            assertEquals(1, synchronizedArray.apply(1));
        }
        @Test
        void iterator_ExpectEqual_LinkedSynchronized(){
            Iterator<Point> iterator= synchronizedLinked.iterator();
            Point point=new Point(0,0);
            while (iterator.hasNext()){
                point=iterator.next();
            }
            assertEquals(point.x, synchronizedLinked.rightBound());
        }
        @Test
        void iterator_ExpectException_LinkedSynchronized(){
            assertThrows(NoSuchElementException.class,()->{
                Iterator<Point> iterator= synchronizedLinked.iterator();
                Point point=new Point(0,0);
                while (iterator.hasNext()){
                    point=iterator.next();
                }
                point=iterator.next();
            });
        }
        @Test
        void doSynchronously_Multiply_LinkedSynchronized(){
            synchronizedLinked.doSynchronously((synchronizedLinked)->{
                double nu_val= synchronizedArray.getY(0)*2;
                synchronizedLinked.setY(0,nu_val);
                return null;
            });
            assertEquals(2, synchronizedLinked.getY(0));
        }

    }
