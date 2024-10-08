package functions;


import java.util.Arrays;

public class ArrayTabulatedFunction extends AbstractTabulated_Function {
    protected double[] xValues = new double[4];
    protected double[] yValues = new double[4];
    protected int count = xValues.length;
    public double[] copyXValue = Arrays.copyOf(xValues, xValues.length);
    public double[] copyYValue = Arrays.copyOf(yValues, yValues.length);
    public ArrayTabulatedFunction(double[] xValues, double[] yValues) {
        for(int i = 0; i < (xValues.length-1); i++) {
            if (xValues.length != yValues.length) {
                return;
            }
        }
        for(int i = 0; i < (xValues.length-1); i++){
            if(xValues[i] >= xValues[i+1]){
                return;
            }
        }
        this.xValues = xValues;
        this.yValues = yValues;
    }
    public ArrayTabulatedFunction(MathFunction source, double xFrom, double xTo, int count) {
        this.count = count;
        if (xFrom > xTo) {
            double temp = xFrom;
            xFrom = xTo;
            xTo = temp;
        } else if (xFrom == xTo) {
            for (int i = 0; i < (count - 1); i++) {
                xValues[i] = xFrom;
            }
            for (int i = 0; i < (count - 1); i++) {
                yValues[i] = source.apply(xFrom);
            }

        } else {
            xValues[0] = xFrom;
            xValues[count - 1] = xTo;
            yValues[0] = source.apply(xFrom);
            yValues[count - 1] = source.apply(xTo);
            double step = (xTo - xFrom)/(count - 1);
            for (int i = 1; i < (count - 2); i++) {
                xValues[i] = i * step;
            }
            for (int i = 1; i < (count - 2); i++) {
                yValues[i] = source.apply(i * step);
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
        for (int i = 0; i < (count - 1); i++) {
            if (xValues[i] == x) {
                return i;
            }
        }
        return -1;
    }

    public int indexOfY(double y) {
        for (int i = 0; i < (count - 1); i++) {
            if (yValues[i] == y) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected int floorIndexOfX(double x) {
        for (int i = 0; i < (count - 1); i++) {
            if (x == xValues[i]) {
                return i;
            }
        }
        for (int i = 0; i < (count - 1); i++) {
            if ((x > xValues[i]) && (x < xValues[i + 1])) {
                return i;
            }
        }
        for (int i = 0; i < (count-1); i++) {
            if (x < xValues[i]){
                return 0;
            }
        }
        return count;
    }

    @Override
    protected double extrapolateRight(double x) {
        if(count == 1){
            return yValues[0];
        }
    return (yValues[count-2] + ((yValues[count-1]-yValues[count-2])/(xValues[count-1]-xValues[count-2]))*(x-xValues[count-2]));
    }

    @Override
    protected double extrapolateLeft(double x) {
        if(count == 1){
            return yValues[0];
        }
        return (yValues[0] + ((yValues[1]-yValues[0])/(xValues[1]-xValues[0]))*(x-xValues[0]));
    }

    @Override
    protected double interpolate(double x, int floorIndex) {
        if(count == 1){
            return yValues[0];
        }
        return (yValues[floorIndex] + ((yValues[floorIndex+1]-yValues[floorIndex])/(xValues[floorIndex+1]-xValues[floorIndex]))*(x-xValues[floorIndex]));
    }

}

