package homework_task;

import homework_task.annotations.AfterSuite;
import homework_task.annotations.BeforeSuite;
import homework_task.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TestRunner {

    public static void start(Class<?> clazz) {
        List<Method> beforeSuiteMethods = new ArrayList<>();
        List<Method> testMethods = new ArrayList<>();
        List<Method> afterSuiteMethod = new ArrayList<>();

        Method[] methods = clazz.getDeclaredMethods();

        for (Method method : methods) {
            if (method.isAnnotationPresent(BeforeSuite.class)) {
                beforeSuiteMethods.add(method);
            } else if (method.isAnnotationPresent(Test.class)) {
                testMethods.add(method);
            } else if (method.isAnnotationPresent(AfterSuite.class)) {
                afterSuiteMethod.add(method);
            }
        }

        if (beforeSuiteMethods.size() > 1 && afterSuiteMethod.size() > 1) {
            throw new RuntimeException("Methods with @homework_task.annotations.BeforeSuite and @homework_task.annotations.AfterSuite annotations must be in a single instance!");
        }

        if (!beforeSuiteMethods.isEmpty()) {
            try {
                beforeSuiteMethods.get(0).invoke(clazz.newInstance());
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                throw new RuntimeException(e);
            }
        }

        testMethods
                .sort(Comparator.comparing(method1 -> method1.getAnnotation(Test.class).priority()));
        for (Method method : testMethods) {
            try {
                method.invoke(clazz.newInstance());
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                throw new RuntimeException(e);
            }
        }

        if (!afterSuiteMethod.isEmpty()) {
            try {
                afterSuiteMethod.get(0).invoke(clazz.newInstance());
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
