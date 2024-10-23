package io;

import functions.ArrayTabulatedFunction;
import functions.LinkedListTabulatedFunction;
import functions.TabulatedFunction;
import functions.factory.ArrayTabulatedFunctionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class FunctionsIOTest {
    private static final Path TEMP= Paths.get("temp");
    @BeforeEach
    public void createDirectory() throws IOException{
        Files.createDirectories(TEMP);
    }
    @AfterEach
    public void cleanUp() throws IOException{
        try(Stream<Path> files = Files.walk(TEMP)){
            files.sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(file->{
                if(!file.delete()){
                    System.err.println("Failed to delete file"+ file.getPath());
                }
            });
        }
    }
    @Test
    public void writeTabulatedFunctions_ExpectEqualToString_Binary()throws IOException{
        Path tempFile=TEMP.resolve("test_function_binary.bin");
        TabulatedFunction resultingFunc;
        ArrayTabulatedFunction arrFunc = new ArrayTabulatedFunction(new double[]{0, 1, 3, 4}, new double[]{2, 4, 5, 3});
        try(BufferedOutputStream arrStream=new BufferedOutputStream(Files.newOutputStream(tempFile))){
            FunctionsIO.writeTabulatedFunction(arrStream,arrFunc);
        }
        try(BufferedInputStream bufferedInputStream=new BufferedInputStream(Files.newInputStream(tempFile))) {
            resultingFunc=FunctionsIO.readTabulatedFunction(bufferedInputStream,new ArrayTabulatedFunctionFactory());
        }
        assertEquals(arrFunc.toString(),resultingFunc.toString());
    }
}