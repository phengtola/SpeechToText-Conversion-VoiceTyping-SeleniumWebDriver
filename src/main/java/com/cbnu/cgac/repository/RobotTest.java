package com.cbnu.cgac.repository;

import java.awt.*;
import java.awt.event.KeyEvent;

public class RobotTest {

    public static void main(String args[]) {
        System.out.println("Hello");
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_SHIFT);
            robot.keyPress(KeyEvent.VK_F10);
            robot.keyRelease(KeyEvent.VK_SHIFT);
            robot.keyRelease(KeyEvent.VK_F10);
            robot.keyPress(KeyEvent.VK_R);
            robot.keyRelease(KeyEvent.VK_R);
            Thread.sleep(100);
        } catch (AWTException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
