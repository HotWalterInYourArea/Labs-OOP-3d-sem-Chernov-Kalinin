package io;

import functions.factory.LinkedListTabulatedFunctionFactory;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class TabulatedFunctionFileInputStream {
    public static void main(String[] args){
        try(BufferedInputStream bufferedInputStream=new BufferedInputStream(new FileInputStream("Lab2/input/binary function.bin"))){
            var factory= new LinkedListTabulatedFunctionFactory();
            System.out.println(FunctionsIO.readTabulatedFunction(bufferedInputStream,factory).toString());
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
