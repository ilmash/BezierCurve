package com.ilmash.bezier;

/**
 * Created by ilmash on 2014-12-04.
 */
public class AnimOptions {
    private int duration;
    private int repeat;
    private Point2D leftHandle, rightHandle;

    public AnimOptions() {
        duration = 5;
        repeat = 1;
        leftHandle = new Point2D(100.0, 50.0);
        rightHandle = new Point2D(350.0, 50.0);
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getRepeat() {
        return repeat;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    public Point2D getLeftHandle() {
        return leftHandle;
    }

    public void setLeftHandle(Point2D leftHandle) {
        this.leftHandle = leftHandle;
    }

    public Point2D getRightHandle() {
        return rightHandle;
    }

    public void setRightHandle(Point2D rightHandle) {
        this.rightHandle = rightHandle;
    }
}
