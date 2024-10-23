package io;

import functions.ArrayTabulatedFunction;
import operations.TabulatedDifferentialOperator;
import java.io.*;

import static io.FunctionsIO.deserialize;

public class ArrayTabulatedFunctionSerialization {
    public static void main(String[] args) {
        try (FileOutputStream out1 = new FileOutputStream("C:/Labbs/Labs-OOP-3d-sem-Chernov-Kalinin/Lab2/output/serialised/array/function.bin")) {
            BufferedOutputStream bos1 = new BufferedOutputStream(out1);
            ArrayTabulatedFunction test1 = new ArrayTabulatedFunction(new double[]{1, 2, 3, 4}, new double[]{1, 4, 9, 16});
            TabulatedDifferentialOperator help1 = new TabulatedDifferentialOperator();
            ArrayTabulatedFunction test1DO1 = (ArrayTabulatedFunction) help1.derive(test1);
            ArrayTabulatedFunction test1DO2 = (ArrayTabulatedFunction) help1.derive(test1DO1);
            FunctionsIO.serialize(bos1, test1);
            FunctionsIO.serialize(bos1, test1DO1);
            FunctionsIO.serialize(bos1, test1DO2);
            bos1.flush();
            bos1.close();
        } catch (IOException e) {

            e.printStackTrace();
        }
        try (FileInputStream out2 = new FileInputStream("C:/Labbs/Labs-OOP-3d-sem-Chernov-Kalinin/Lab2/output/serialised/array/function.bin")) {
            BufferedInputStream bos2 = new BufferedInputStream(out2);
            ArrayTabulatedFunction test1 = (ArrayTabulatedFunction) FunctionsIO.deserialize(bos2);
            ArrayTabulatedFunction test1DO1 = (ArrayTabulatedFunction) FunctionsIO.deserialize(bos2);
            ArrayTabulatedFunction test1DO2 = (ArrayTabulatedFunction) FunctionsIO.deserialize(bos2);
            System.out.println(test1.toString());
            System.out.println(test1DO1.toString());
            System.out.println(test1DO2.toString());
        } catch (IOException e) {

            e.printStackTrace();

        } catch (ClassNotFoundException e) {

            e.printStackTrace();
        }

    }
}
