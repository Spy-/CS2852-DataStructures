/*
 * Course: CS2852
 * Spring 2019
 * Lab 1 - Dot 2 Dot Generator
 * Name: John Bretz
 * Created: 3/4/2019
 */
package bretzj;

/**
 * The class that stores the x and y values for each dot
 */
public class Dot {

    private double x;
    private double y;

    /**
     * Constructor for a Dot object
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Dot(double x, double y) {
        this.x = (x * Main.WIDTH);
        this.y = Main.HEIGHT - (y * Main.HEIGHT);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
