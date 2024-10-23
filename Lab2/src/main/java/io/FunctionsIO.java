package io;

import java.io.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Iterator;
import java.util.Locale;

import functions.Point;
import functions.TabulatedFunction;
import functions.factory.TabulatedFunctionFactory;

public final class FunctionsIO {
    private FunctionsIO() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    public static TabulatedFunction readTabulatedFunction(BufferedReader reader, TabulatedFunctionFactory factory) throws IOException, ParseException {
        double[] xValues = new double[0];
        double[] yValues = new double[0];
        try {
            String line;
            NumberFormat numberFormatter = NumberFormat.getInstance(Locale.forLanguageTag("ru"));
            int count = Integer.parseInt(reader.readLine());
            xValues = new double[count];
            yValues = new double[count];
            int i = 0;
            while ((line = reader.readLine()) != null) {
                String[] twoString = line.split(" ");
                Number numberXValues = numberFormatter.parse(twoString[0]);
                Number numberYValues = numberFormatter.parse(twoString[1]);
                xValues[i] = numberXValues.doubleValue();
                yValues[i] = numberYValues.doubleValue();
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            new IOException(e);
        }
        return factory.create(xValues, yValues);

    }

    public static void writeTabulatedFunction(BufferedWriter writer, TabulatedFunction function){
        PrintWriter wrapper = new PrintWriter(writer);
        wrapper.println(function.getCount());
        Iterator<Point> iterator = function.iterator();
        Point[] array = new Point[function.getCount()];
        for (Point point : array ) {
            point = iterator.next();
            wrapper.printf("%f %f\n", point.x, point.y);
        }
        wrapper.flush();
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
