package ui.service;

import functions.MathFunction;
import functions.TabulatedFunction;
import io.FunctionsIO;

import java.io.*;

public class FunctionSerializer {
    public static byte[] serializeCustomFunction(MathFunction function) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream out = new ObjectOutputStream(bos)) {
            out.writeObject(function);
            return bos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Failed to serialize function", e);
        }
    }

    public static byte[] serializeByte(TabulatedFunction function) {
        byte[] serializedFunction = null;
        try(ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
            BufferedOutputStream outputStream = new BufferedOutputStream(byteOutputStream)) {
            FunctionsIO.serialize(outputStream, function);
            serializedFunction = byteOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return serializedFunction;
    }

    public static String serializeJson(TabulatedFunction function) {
        String serializedFunction = null;
        try(StringWriter stringWriter = new StringWriter();
            BufferedWriter bufferedWriter = new BufferedWriter(stringWriter)) {
            FunctionsIO.serializeJson(bufferedWriter, function);
            serializedFunction = stringWriter.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return serializedFunction;
    }

    public static String serializeXml(TabulatedFunction function) {
        String serializedFunction = null;
        try(StringWriter stringWriter = new StringWriter();
            BufferedWriter bufferedWriter = new BufferedWriter(stringWriter)) {
            FunctionsIO.serializeXml(bufferedWriter, function);
            serializedFunction = stringWriter.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return serializedFunction;
    }

    public static MathFunction deserializeFunction(byte[] data) {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
             ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
            return (MathFunction) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to deserialize function", e);
        }
    }
}
