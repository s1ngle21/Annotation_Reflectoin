package homework_task;

import homework_task.annotations.AfterSuite;
import homework_task.annotations.BeforeSuite;
import homework_task.annotations.Test;

import java.util.logging.Logger;

public class MethodTests {
    private static Logger logger = Logger.getLogger(MethodTests.class.getName());

    @BeforeSuite
    public static void beforeMethod() {
        logger.info("Running before suite");
    }

    @Test(priority = 2)
    public static void test1() {
        logger.info("Running test1");
    }

    @Test(priority = 3)
    public static void test2() {
        logger.info("Running test2");
    }

    @Test(priority = 1)
    public static void test3() {
        logger.info("Running test3");
    }

    @AfterSuite
    public static void afterMethod() {
        logger.info("Running after suite");
    }
}
