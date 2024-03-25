package org.acme;

import java.awt.Rectangle;

public abstract class GameObject {

    protected int x;
    protected int y;
    protected int width;
    protected int height;

    protected GameObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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

    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), width, height);
    }

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
}