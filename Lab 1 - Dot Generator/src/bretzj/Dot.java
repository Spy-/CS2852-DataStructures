package bretzj;

public class Dot {

    private double x;
    private double y;

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
