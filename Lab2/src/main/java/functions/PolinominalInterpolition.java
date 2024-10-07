package functions;
import java.util.Scanner;
public class PolinominalInterpolition implements MathFunction {
        public double apply ( double x){
            Scanner sc = new Scanner(System.in);
            System.out.println("Введите число:");

            int n = sc.nextInt();
            if (n <= 1) {
                System.out.println("Введено некорректное значение");
                return (-1);
            } else {

                double[] xz = new double[n];
                for (int i = 0; i <= n - 1; i++) {
                    Scanner sd = new Scanner(System.in);
                    System.out.println("Введите число xz, абсциссу:");
                    xz[i] = sd.nextDouble();
                }
                if ((x < xz[0]) || (x > xz[n - 1])) {
                    System.out.println("Число x не принадлежит области определения функции y");
                    return -1;
                } else {
                    for (int i = 0; i <= n - 1; i++) {
                        if (x == xz[i]) {
                            System.out.println("x совпал с узлом интерполяции");
                            return -1;
                        }
                    }
                    double[] y = new double[n];
                    for (int i = 0; i <= n - 1; i++) {
                        Scanner sg = new Scanner(System.in);
                        System.out.println("Введите число y, ординату:");
                        y[i] = sg.nextDouble();
                    }
                    double S = 0;
                    for (int i = 0; i <= n - 1; i++) {
                        double tn = 1;
                        double d = 1;
                        for (int j = 0; j <= n - 1; j++) {
                            if (j == i) continue;
                            tn = tn * (x - xz[j]);
                            d = d * (xz[i] - xz[j]);
                        }
                        double ir = tn / d;
                        S = S + y[i] * ir;
                    }
                    System.out.println(S);
                    return S;
                }
            }
        }
    }


