package no.uib.inf101.sem2.gameEngine.controller;

import java.awt.event.KeyEvent;

import no.uib.inf101.sem2.gameEngine.view.pipeline.LinearMath.Vector;

public class MoveHandler {
    float moveSpeed;
    boolean noclip;
    float jumpBurst;

    boolean wKeyPressed;
    boolean aKeyPressed;
    boolean sKeyPressed;
    boolean dKeyPressed;
    boolean spaceKeyPressed;
    boolean ctrlKeyPressed;

    public MoveHandler(float moveSpeed, boolean noclip, float jumpBurst){
        this.moveSpeed = moveSpeed;
        this.noclip = noclip;
        this.jumpBurst = jumpBurst;

        wKeyPressed = false;
        aKeyPressed = false;
        sKeyPressed = false;
        dKeyPressed = false;
        spaceKeyPressed = false;
        ctrlKeyPressed = false;
    }

    public boolean keyPressed(KeyEvent press){
        boolean change = false;
        if(press.getKeyCode() == KeyEvent.VK_W && !wKeyPressed) {
            wKeyPressed = true;
            change = true;
        }
        if(press.getKeyCode() == KeyEvent.VK_S && !sKeyPressed){
            sKeyPressed = true;
            change = true;
        }
        if(press.getKeyCode() == KeyEvent.VK_A && !aKeyPressed) {
            aKeyPressed = true;
            change = true;
        }
        if(press.getKeyCode() == KeyEvent.VK_D && !dKeyPressed) {
            dKeyPressed = true;
            change = true;
        }
        if(press.getKeyCode() == KeyEvent.VK_SPACE && (!spaceKeyPressed || !noclip)) {
            spaceKeyPressed = true;
            change = true;
        }
        if(press.getKeyCode() == KeyEvent.VK_CONTROL && !ctrlKeyPressed) {
            ctrlKeyPressed = true;
            change = true;
        }

        return change;
    }

    public boolean keyReleased(KeyEvent release){
        boolean change = false;

        if(release.getKeyCode() == KeyEvent.VK_W && wKeyPressed) {
            wKeyPressed = false;
            change = true;
        }
        if(release.getKeyCode() == KeyEvent.VK_S && sKeyPressed){
            sKeyPressed = false;
            change = true;
        }
        if(release.getKeyCode() == KeyEvent.VK_A && aKeyPressed) {
            aKeyPressed = false;
            change = true;
        }
        if(release.getKeyCode() == KeyEvent.VK_D && dKeyPressed) {
            dKeyPressed = false;
            change = true;
        }
        if(release.getKeyCode() == KeyEvent.VK_SPACE && spaceKeyPressed) {
            spaceKeyPressed = false;
            change = true;
        }
        if(release.getKeyCode() == KeyEvent.VK_CONTROL && ctrlKeyPressed) {
            ctrlKeyPressed = false;
            change = true;
        }

        return change;
    }

    public Vector getMovementDelta(){
        float forwards = 0;
        float left = 0;
        float up = 0;
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
        
        if(this.spaceKeyPressed){
            up += 1;
        }
        if(this.ctrlKeyPressed){
            up += -1;
        }

        Vector deltaXZ = new Vector(new float[]{left, forwards});
        Vector normalizedXZ;
        if(left == 0 && forwards == 0){
            normalizedXZ = new Vector(new float[]{0, 0});
        } else {
            normalizedXZ = deltaXZ.normalized().scaledBy(this.moveSpeed);
        }

        if(this.noclip){
            return new Vector(new float[]{normalizedXZ.get(0), up*this.moveSpeed, normalizedXZ.get(1)});
        
        } else {
            if(this.spaceKeyPressed) {
                return new Vector(new float[]{normalizedXZ.get(0), this.jumpBurst, normalizedXZ.get(1)});
            } else {
                return new Vector(new float[]{normalizedXZ.get(0), 0, normalizedXZ.get(1)});
            }
        }
    }
}
