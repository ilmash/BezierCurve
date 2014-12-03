package com.ilmash.bezier;

/**
 * Created by ilmash on 2014-11-19.
 * It's PID at its simplest form, don't count on too much features
 */
@SuppressWarnings("UnusedDeclaration")
public class Point2D {
    private double x;
    private double y;

    Point2D() {
        x = 0;
        y = 0;
    }

    Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    Point2D(Point2D p) {
        this.x = p.getX();
        this.y = p.getY();
    }

    public static double distance(Point2D p1, Point2D p2) {
        double x_difference = Math.abs(p1.getX() - p2.getX());
        double y_difference = Math.abs(p1.getY() - p2.getY());
        return Math.sqrt(Math.pow(x_difference, 2) + Math.pow(y_difference, 2));
    }

    public static double[] pointBetweenAsDouble(Point2D p1, Point2D p2, double percent) {
        double new_x, new_y;
        if (p1.getX() > p2.getX()) {
            new_x = p1.getX()-((p1.getX() - p2.getX()) * percent);
        } else {
            new_x = p1.getX()+((p2.getX() - p1.getX()) * percent);
        }
        if (p1.getY() > p2.getY()) {
            new_y = p1.getY()-((p1.getY() - p2.getY()) * percent);
        } else {
            new_y = p1.getY()+((p2.getY() - p1.getY()) * percent);
        }
        return new double[]{new_x, new_y};
    }

    public static Point2D pointBetween(Point2D p1, Point2D p2, double percent) {
        double new_x, new_y;
        if (p1.getX() > p2.getX()) {
            new_x = p1.getX()-((p1.getX() - p2.getX()) * percent);
        } else {
            new_x = p1.getX()+((p2.getX() - p1.getX()) * percent);
        }
        if (p1.getY() > p2.getY()) {
            new_y = p1.getY()-((p1.getY() - p2.getY()) * percent);
        } else {
            new_y = p1.getY()+((p2.getY() - p1.getY()) * percent);
        }
        return new Point2D(new_x, new_y);
    }


    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
