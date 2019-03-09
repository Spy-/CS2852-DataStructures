/*
 * Course: CS2852
 * Spring 2019
 * Lab 1 - Dot 2 Dot Generator
 * Name: John Bretz
 * Created: 3/4/2019
 */
package bretzj;

import static bretzj.Util.dist;

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

    /**
     * Calculates the critical value of the dot
     *
     * @param prev the previous dot in the list
     * @param next the next dot in the list
     * @return the critical value
     */
    public double calculateCriticalValue(Dot prev, Dot next) {
        return dist(prev.getX(), prev.getY(), x, y) +  // previous to this
                dist(x, y, next.getX(), next.getY()) -  // this to next
                dist(prev.getX(), prev.getY(), next.getX(), next.getY()); // prev to next
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
