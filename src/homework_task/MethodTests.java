package homework_task;

import homework_task.annotations.AfterSuite;
import homework_task.annotations.BeforeSuite;
import homework_task.annotations.Test;

public class MethodTests {

    @BeforeSuite
    public static void beforeMethod() {
        System.out.println("Running before suite");
    }

    @Test(priority = 2)
    public static void test1() {
        System.out.println("Running test1");
    }

    @Test(priority = 3)
    public static void test2() {
        System.out.println("Running test2");
    }

    @Test(priority = 1)
    public static void test3() {
        System.out.println("Running test3");
    }

    @AfterSuite
    public static void afterMethod() {
        System.out.println("Running after suite");
    }
}
