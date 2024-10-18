package io;

import java.io.*;

import functions.Point;
import functions.TabulatedFunction;
import functions.factory.TabulatedFunctionFactory;

public final class FunctionsIO {
    private FunctionsIO(){
        throw new UnsupportedOperationException();
    }
    static void writeTabulatedFunction(BufferedOutputStream outputStream,TabulatedFunction function) throws IOException{
        DataOutputStream nu_stream=new DataOutputStream(outputStream);
        nu_stream.writeInt(function.getCount());
        for(Point p:function){
            nu_stream.writeDouble(p.x);
            nu_stream.writeDouble(p.y);
        }
        nu_stream.flush();
    }
    static TabulatedFunction readTabulatedFunction(BufferedInputStream inputStream, TabulatedFunctionFactory factory)throws IOException{
        DataInputStream nu_inputStream=new DataInputStream(inputStream);
        int count=nu_inputStream.readInt();
        double[] xValues=new double[count];
        double[] yValues=new double[count];
        for(int i=0;i<count;i++){
            xValues[i]=nu_inputStream.readDouble();
            yValues[i]=nu_inputStream.readDouble();
        }
        return factory.create(xValues,yValues);
    }
    static TabulatedFunction deserialize(BufferedInputStream inStream)throws IOException,ClassNotFoundException{
        ObjectInputStream nu_inStream=new ObjectInputStream(inStream);
        Object function=nu_inStream.readObject();
        return (TabulatedFunction) function;
    }
}
