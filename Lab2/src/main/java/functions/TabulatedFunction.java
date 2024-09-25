package functions;

public interface TabulatedFunction extends MathFunction {
     int getCount();
     double getX(int index);
     double getY(int index);public void setY(int index,double value);
     int indexOfX(double x);
     int IndexOfY(double y);
     double leftBound();
     double rightBound();
}
