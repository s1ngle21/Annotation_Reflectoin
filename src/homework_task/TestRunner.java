package homework_task;

import homework_task.annotations.AfterSuite;
import homework_task.annotations.BeforeSuite;
import homework_task.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TestRunner {

    public static void start(Class<?> clazz) {
        Objects.requireNonNull(clazz);
        List<Method> beforeSuiteMethods = new ArrayList<>();
        List<Method> testMethods = new ArrayList<>();
        List<Method> afterSuiteMethods = new ArrayList<>();

        Method[] methods = clazz.getDeclaredMethods();

        for (Method method : methods) {
            if (method.isAnnotationPresent(BeforeSuite.class)) {
                beforeSuiteMethods.add(method);
            } else if (method.isAnnotationPresent(Test.class)) {
                testMethods.add(method);
            } else if (method.isAnnotationPresent(AfterSuite.class)) {
                afterSuiteMethods.add(method);
            }
        }

        if (beforeSuiteMethods.size() > 1 || afterSuiteMethods.size() > 1) {
            throw new RuntimeException("Methods with @BeforeSuite and @AfterSuite annotations must be in a single instance!");
        }

        invokeMethods(beforeSuiteMethods, clazz);

        invokeMethods(testMethods, clazz);

        invokeMethods(afterSuiteMethods, clazz);
    }

    public static void invokeMethods(List<Method> methods, Class<?> clazz) {
        if (methods.get(0).isAnnotationPresent(BeforeSuite.class) ||
                methods.get(0).isAnnotationPresent(AfterSuite.class)) {
            try {
                methods.get(0).invoke(clazz);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        } else {
            methods = methods
                    .stream()
                    .sorted(Comparator.comparing(method ->
                            method.getAnnotation(Test.class).priority()))
                    .collect(Collectors.toList());

            for (Method method : methods) {
                try {
                    method.invoke(clazz);

                } catch (InvocationTargetException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
