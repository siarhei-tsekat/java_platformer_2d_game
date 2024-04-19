package com.me.entities;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Entity {

    protected float x;
    protected float y;
    protected int width;
    protected int height;
    protected Rectangle2D.Float hitBox;

    public Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rectangle2D.Float getHitBox() {
        return hitBox;
    }

    protected void drawHitBox(Graphics graphics) {
        graphics.setColor(Color.PINK);
        graphics.drawRect((int) hitBox.x, (int) hitBox.y, (int) hitBox.width, (int) hitBox.height);
    }

//    protected void updateHitBox() {
//        hitBox.x = (int) x;
//        hitBox.y = (int) y;
//    }

    protected void initHitBox(float x, float y, float width, float height) {
        hitBox = new Rectangle2D.Float(x, y, width, height);
    }
}
