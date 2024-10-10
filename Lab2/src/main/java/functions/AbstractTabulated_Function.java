package functions;
public abstract class AbstractTabulated_Function implements TabulatedFunction {
    protected abstract int floorIndexOfX(double x);
    protected abstract double extrapolateLeft(double x);
    protected abstract double extrapolateRight(double x);
    protected abstract double interpolate(double x, int floorIndex);
    protected int count = 0;
    protected double interpolate(double x, double leftX, double rightX, double leftY, double rightY) {
        return (leftY + ((rightY-leftY)/(rightX-leftX))*(x-leftX));
    }
    public double apply(double x) {
        if (x < leftBound()) {
            return extrapolateLeft(x);
        } else {
            if (x > rightBound()) {
                return extrapolateRight(x);
            }
        }
        if (0 < indexOfX(x)) {
            return getY(indexOfX(x));
        }
        else{
            return interpolate(x, floorIndexOfX(x));
            }
            }


    }




