package functions;


import exceptions.ArrayIsNotSortedException;
import exceptions.DifferentLengthOfArraysException;
import exceptions.InterpolationException;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayTabulatedFunction extends AbstractTabulatedFunction implements Insertable, Removable {
    protected double[] xValues = new double[4];
    protected double[] yValues = new double[4];
    protected int count;
    public double[] copyXValue = Arrays.copyOf(xValues, xValues.length);
    public double[] copyYValue = Arrays.copyOf(yValues, yValues.length);

    public ArrayTabulatedFunction(double[] xValues, double[] yValues) throws DifferentLengthOfArraysException, ArrayIsNotSortedException {
        checkLengthIsTheSame(xValues, yValues);
        checkSorted(xValues);
        this.xValues = xValues;
        this.yValues = yValues;
        count = xValues.length;
    }

    public ArrayTabulatedFunction(MathFunction source, double xFrom, double xTo, int count) {
        this.count = count;
        if (xFrom > xTo) {
            double temp = xFrom;
            xFrom = xTo;
            xTo = temp;
        }
        xValues = new double[count];
        yValues = new double[count];
        if (xFrom == xTo) {
            double help = source.apply(xFrom);
            for (int i = 0; i <= (count - 1); i++) {
                xValues[i] = xFrom;
                yValues[i] = help;
            }
        } else {
            xValues[0] = xFrom;
            xValues[count - 1] = xTo;
            yValues[0] = source.apply(xFrom);
            yValues[count - 1] = source.apply(xTo);
            double step = (xTo - xFrom) / (count - 1);
            for (int i = 1; i <= (count - 2); i++) {
                xValues[i] = xFrom + i * step;
                yValues[i] = source.apply(xFrom + i * step);
            }
        }
    }

    public int getCount() {
        return count;
    }

    @Override
    public double getX(int index) {
        return xValues[index];
    }

    @Override
    public double getY(int index) {
        return yValues[index];
    }

    @Override
    public void setY(int index, double value) {
        yValues[index] = value;
    }

    @Override
    public double leftBound() {
        return xValues[0];
    }

    @Override
    public double rightBound() {
        return xValues[count - 1];
    }

    @Override
    public int indexOfX(double x) {
        for (int i = 0; i <= (count - 1); i++) {
            if (xValues[i] == x) {
                return i;
            }
        }
        return -1;
    }

    public int indexOfY(double y) {
        for (int i = 0; i <= (count - 1); i++) {
            if (yValues[i] == y) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected int floorIndexOfX(double x) {
        for (int i = 0; i < (count - 1); i++) {
            if ((x > xValues[i]) && (x < xValues[i + 1])) {
                return i;
            }
        }
        if (x < xValues[0]) {
            return 0;
        }

        return count;
    }

    @Override
    protected double extrapolateRight(double x) {
        if (count == 1) {
            return yValues[0];
        }
        return (yValues[count - 2] + ((yValues[count - 1] - yValues[count - 2]) / (xValues[count - 1] - xValues[count - 2])) * (x - xValues[count - 2]));
    }

    @Override
    protected double extrapolateLeft(double x) {
        if (count == 1) {
            return yValues[0];
        }
        return (yValues[0] + ((yValues[1] - yValues[0]) / (xValues[1] - xValues[0])) * (x - xValues[0]));
    }

    @Override
    protected double interpolate(double x, int floorIndex) throws InterpolationException {
        if (floorIndex >= (count - 1)) {
            throw new InterpolationException();
        }
        for (int i = 0; i <= (count - 1); i++) {
            if (x == xValues[i]) {
                throw new InterpolationException();
            }
        }
        if (!(x > xValues[floorIndex]) && (x < xValues[floorIndex + 1])) {
            throw new InterpolationException();
        }

        if (count == 1) {
            return yValues[0];
        }
        return (yValues[floorIndex] + ((yValues[floorIndex + 1] - yValues[floorIndex]) / (xValues[floorIndex + 1] - xValues[floorIndex])) * (x - xValues[floorIndex]));
    }

    @Override
    public void insert(double x, double y) {
        int index = indexOfX(x);
        if (index == -1) {
            int before;
            int after;
            double[] nu_arrX = new double[count + 1];
            double[] nu_arrY = new double[count + 1];
            if (x < leftBound()) {
                before = 0;
            } else if (x > rightBound()) {
                before = count;
            } else {
                before = floorIndexOfX(x) + 1;
            }
            nu_arrX[before] = x;
            nu_arrY[before] = y;
            for (int i = 0; i < before; i++) {
                nu_arrX[i] = this.xValues[i];
                nu_arrY[i] = this.yValues[i];
            }
            for (int i = before; i < count; i++) {
                nu_arrX[i + 1] = this.xValues[i];
                nu_arrY[i + 1] = this.yValues[i];
            }
            count++;
            this.xValues = nu_arrX;
            this.yValues = nu_arrY;
        } else {
            this.yValues[index] = y;
        }
    }

    public void remove(int index) {
        this.xValues = ArrayUtils.remove(this.xValues, index);
        this.yValues = ArrayUtils.remove(this.yValues, index);

    }

    @Override
    public Iterator<Point> iterator() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
