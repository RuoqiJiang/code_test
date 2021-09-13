package com.logTest.log4j.test;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.HashMap;


public class Log4jTest01 {
    private static HashMap<String, Integer> booking;


   /* @BeforeAll
    static void InputInitial() {
        ArrayList<String> userInput = new ArrayList<>();
        userInput.add("10 IMG");
        userInput.add("15 Vid");
        userInput.add("30 flac");

        InputItem getInput = new InputItem();
        booking = getInput.OrderParser(userInput);
    }
*/

    @Test
    public void test01() {

        BasicConfigurator.configure();
        Logger logger = Logger.getLogger(Log4jTest01.class);
        logger.info("info信息");
        }
    }

