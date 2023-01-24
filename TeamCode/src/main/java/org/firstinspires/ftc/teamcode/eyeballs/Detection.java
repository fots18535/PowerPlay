package org.firstinspires.ftc.teamcode.eyeballs;

public class Detection {
    double area;
    int x;
    int y;

    public Detection(double area, int x, int y) {
        this.area = area;
        this.x = x;
        this.y = y;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
