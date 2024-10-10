package functions;
public class Main {
    public static void main(String[] args){
        ArrayTabulatedFunction cd = new ArrayTabulatedFunction(new double[]{1.0, 2.0, 3.0, 4.0}, new double[]{1.0, 2.0, 3.0, 4.0});
        System.out.println(cd.getX(2));
    }
}
