package no.uib.inf101.sem2.gameEngine.controller;

import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

import no.uib.inf101.sem2.gameEngine.view.pipeline.LinearMath.Vector;

public class MoveHandler {
    float moveSpeed;

    boolean wKeyPressed;
    boolean aKeyPressed;
    boolean sKeyPressed;
    boolean dKeyPressed;

    public MoveHandler(float moveSpeed){
        this.moveSpeed = moveSpeed;

        wKeyPressed = false;
        aKeyPressed = false;
        sKeyPressed = false;
        dKeyPressed = false;
    }

    public boolean keyPressed(KeyEvent press){
        boolean change = false;
        if(press.getKeyCode() == KeyEvent.VK_W && wKeyPressed == false) {
            wKeyPressed = true;
            change = true;
        }
        if(press.getKeyCode() == KeyEvent.VK_S && sKeyPressed == false){
            sKeyPressed = true;
            change = true;
        }
        if(press.getKeyCode() == KeyEvent.VK_A && aKeyPressed == false) {
            aKeyPressed = true;
            change = true;
        }
        if(press.getKeyCode() == KeyEvent.VK_D && dKeyPressed == false) {
            dKeyPressed = true;
            change = true;
        }

        return change;
    }

    public boolean keyReleased(KeyEvent release){
        boolean change = false;

        if(release.getKeyCode() == KeyEvent.VK_W && wKeyPressed == true) {
            wKeyPressed = false;
            change = true;
        }
        if(release.getKeyCode() == KeyEvent.VK_S && sKeyPressed == true){
            sKeyPressed = false;
            change = true;
        }
        if(release.getKeyCode() == KeyEvent.VK_A && aKeyPressed == true) {
            aKeyPressed = false;
            change = true;
        }
        if(release.getKeyCode() == KeyEvent.VK_D && dKeyPressed == true) {
            dKeyPressed = false;
            change = true;
        }

        return change;
    }

    public Vector getMovementDelta(){
        float forwards = 0;
        float left = 0;
        if(this.wKeyPressed){
            forwards += 1;
        } 
        if(this.sKeyPressed){
            forwards += -1;
        }
        if(this.aKeyPressed){
            left += 1;
        } 
        if(this.dKeyPressed){
            left += -1;
        }
        Vector delta = new Vector(new float[]{left, 0, forwards});

        if(left == 0 && forwards == 0){
            return new Vector(new float[]{0, 0, 0});
        } else {
            //System.out.println(delta.normalized());
            return delta.normalized().scaledBy(this.moveSpeed);
        }
    }
}
