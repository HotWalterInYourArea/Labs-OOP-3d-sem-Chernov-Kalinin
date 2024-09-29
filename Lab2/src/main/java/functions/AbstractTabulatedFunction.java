package functions;

public abstract class AbstractTabulatedFunction implements TabulatedFunction{
    protected int count=0;
    protected abstract int floorIndexOfX(double x);
    protected abstract double extrapolateRight(double x);
    protected abstract double extrapolateLeft(double x);
    protected abstract double interpolate(double x,int floorIndex);
    protected double interpolate(double x,double leftX,double rightX,double leftY,double rightY){
        if(rightX==leftX)return leftY;
        return leftY + (rightY-leftY)/(rightX-leftX)*(x-leftX);
    }
    @Override
    public double apply(double x){
        if(x<this.leftBound())return extrapolateLeft(x);
        else if(x>this.rightBound())return extrapolateRight(x);
        else if(indexOfX(x)==-1)return interpolate(x,floorIndexOfX(x));
        return getY(indexOfX(x));
    }

}
