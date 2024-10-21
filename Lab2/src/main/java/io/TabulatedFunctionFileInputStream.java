package io;

import functions.ArrayTabulatedFunction;
import functions.LinkedListTabulatedFunction;
import functions.TabulatedFunction;
import functions.factory.LinkedListTabulatedFunctionFactory;

import java.io.*;

public class TabulatedFunctionFileInputStream {
    public static void main(String[] args){
        try(BufferedInputStream bufferedInputStream=new BufferedInputStream(new FileInputStream("Lab2/output/linked list.bin"))){
            var factory= new LinkedListTabulatedFunctionFactory();
            System.out.println(FunctionsIO.readTabulatedFunction(bufferedInputStream,factory).toString());
        }
        catch(IOException e){
            e.printStackTrace();
        }
        /**try {
            BufferedReader buffInpStream = new BufferedReader(new InputStreamReader(System.in));
            TabulatedFunction func=FunctionsIO.readTabulatedFunction(buffInpStream,new LinkedListTabulatedFunctionFactory());
        }
        catch (IOException e){
            e.printStackTrace();
        }**/
    }
}
