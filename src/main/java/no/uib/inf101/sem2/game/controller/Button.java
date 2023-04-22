package no.uib.inf101.sem2.game.controller;

import java.awt.geom.Rectangle2D;

/**
 * Button is a class representing a button in the game's user interface.
 * It stores information about the button's position, dimensions, and text.
 */
public final class Button{
    private final int x0;
    private final int y0;
    private final int width;
    private final int height;
    private String text;

    /**
     * Constructs a Button object centered at the given xy position, dimensions, and text.
     *
     * @param centerX The x coordinate of the button's center.
     * @param centerY The y coordinate of the button's center.
     * @param width The width of the button.
     * @param height The height of the button.
     * @param text The text displayed on the button.
     */
    public Button(int centerX, int centerY, int width, int height, String text){
        this.x0 = centerX - width/2;
        this.y0 = centerY - height/2;
        this.width = width;
        this.height = height;
        this.text = text;
    }

    /**
     * Checks if the button was clicked based on the given x and y coordinates.
     *
     * @param x The x coordinate of the click.
     * @param y The y coordinate of the click.
     * @return true if the button was clicked, false otherwise.
     */
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

    /**
     * Returns a Rectangle2D object representing the button's bounding rectangle.
     *
     * @return A Rectangle2D object representing the button's bounding rectangle.
     */
    public Rectangle2D getRect(){
        return new Rectangle2D.Double(this.x0, this.y0, this.width, this.height);
    }

    /**
     * Returns the x coordinate of the button's center.
     *
     * @return The x coordinate of the button's center.
     */
    public int getCenterX(){
        return this.x0 + this.width/2;
    }

    /**
     * Returns the y coordinate of the button's center.
     *
     * @return The y coordinate of the button's center.
     */
    public int getCenterY(){
        return this.y0 + this.height/2;
    }

    /**
     * Returns the text displayed on the button.
     *
     * @return The text displayed on the button.
     */
    public String getText(){
        return this.text;
    }

    /**
     * Sets the text displayed on the button.
     *
     * @param text The new text to be displayed on the button.
     */
    public void setText(String text){
        this.text = text;
    }

}
