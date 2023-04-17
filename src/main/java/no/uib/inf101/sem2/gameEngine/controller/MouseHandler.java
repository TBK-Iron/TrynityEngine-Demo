package no.uib.inf101.sem2.gameEngine.controller;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.MouseEvent;

import no.uib.inf101.sem2.gameEngine.config.Config;
import no.uib.inf101.sem2.gameEngine.view.pipeline.RelativeRotation;

/**
 * MouseHandler class is responsible for handling mouse input
 * and converting it into rotation values.
 */
public class MouseHandler {

    private Config config;

    /**
     * Constructs a new MouseHandler with the specified screen dimensions.
     *
     * @param width  The width of the screen.
     * @param height The height of the screen.
     */
    protected MouseHandler(Config config){

        this.config = config;
    }
    
    /**
     * Resets the mouse position to the center of the screen.
     */
    protected void resetMousePosition(){
        Robot robot;
        try {
            robot = new Robot();
            robot.mouseMove(config.screenWidth()/2, config.screenHeight()/2);
        } catch(AWTException e){
            e.printStackTrace();
        }
    }

    /**
     * Calculates the relative rotation based on the mouse event.
     *
     * @param e The MouseEvent to process.
     * @return A RelativeRotation object representing the rotation values.
     */
    protected RelativeRotation getRotation(MouseEvent e){
        int x = e.getX();
        int absX = e.getXOnScreen();
        int y = e.getY();
        int absY = e.getYOnScreen();
        
        int dx = -(x + (absX - x) - config.screenWidth()/2);
        int dy = y + (absY - y) - config.screenHeight()/2;

        float leftRightRot = ((float) dx / config.screenWidth());
        float upDownRot = ((float) dy / config.screenHeight());

        return new RelativeRotation(upDownRot, leftRightRot);
    }
}
