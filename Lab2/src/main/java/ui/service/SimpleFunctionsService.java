package ui.service;

import org.reflections.Reflections;
import functions.MathFunction;
import ui.annotations.SimpleFunction;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class SimpleFunctionsService {
    public static Map<String, byte[]> putSimpleFunctions() {
        Map<String, byte[]> allFunctions = new HashMap<String, byte[]>();
        List<Class<?>> classes = AccessClassesWithAnnotations.findAllClassesWithSimpleFunctionAnnotation("functions");

        try {
            for (Class<?> clazz : classes) {
                SimpleFunction annotation = clazz.getAnnotation(SimpleFunction.class);
                allFunctions.put(annotation.canonName(), FunctionSerializer.serializeCustomFunction((MathFunction) clazz.getConstructor().newInstance()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allFunctions;
    }
}