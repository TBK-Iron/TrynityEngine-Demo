package no.uib.inf101.sem2.gameEngine.controller;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.MouseEvent;

import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;

public class MouseHandler {

    int width;
    int height;

    int centerX;
    int centerY;

    protected MouseHandler(int width, int height){
        this.width = width;
        this.height = height;

        centerX = width / 2;
        centerY = height / 2;

    }

    protected void resetMousePosition(){
        Robot robot;
        try {
            robot = new Robot();
            robot.mouseMove(centerX + 6, centerY + 29);
        } catch(AWTException e){
            e.printStackTrace();
        }
    }

    protected RelativeRotation getRotation(MouseEvent e){

        int x = e.getX();
        int y = e.getY();
        /* System.out.println("centerX: " + centerX + " centerY: " + centerY);
        System.out.println("x: " + x + " y: " + y); */
        int dx = -(x - this.centerX);
        int dy = y - this.centerY;

        //System.out.println("LeftRight:" + dx + ", Updown: " + dy);

        float leftRightRot = ((float) dx / width);
        float upDownRot = ((float) dy / height);

        return new RelativeRotation(upDownRot, leftRightRot);
    }
}
