package no.uib.inf101.sem2.game.controller;

import java.awt.geom.Rectangle2D;

public final class Button{
    private final int x0;
    private final int y0;
    private final int width;
    private final int height;
    private String text;

    public Button(int centerX, int centerY, int width, int height, String text){
        this.x0 = centerX - width/2;
        this.y0 = centerY - height/2;
        this.width = width;
        this.height = height;
        this.text = text;
    }

    public boolean isClicked(int x, int y){
        if(x < this.x0){
            return false;
        } else if (x > this.x0 + this.width){
            return false;
        } else if (y < this.y0){
            return false;
        } else if (y > this.y0 + this.height){
            return false;
        } else {
            return true;
        }
    }

    public Rectangle2D getRect(){
        return new Rectangle2D.Double(this.x0, this.y0, this.width, this.height);
    }

    public int getCenterX(){
        return this.x0 + this.width/2;
    }

    public int getCenterY(){
        return this.y0 + this.height/2;
    }

    public String getText(){
        return this.text;
    }

    public void setText(String text){
        this.text = text;
    }

}
