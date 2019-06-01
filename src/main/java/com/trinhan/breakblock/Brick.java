package com.trinhan.breakblock;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class Brick {
    private int x, y, width, height;
    private boolean isVisible;

    public Brick(int x, int y, int width, int height) {

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        isVisible = true;
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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void drawBrick(Canvas canvas, Paint paint) {
        if (isVisible) {
            canvas.drawRect( new RectF( x, y, x + width, y + height ), paint );
        }
    }

    public void setInVisible() {
        isVisible = false;
    }

    public void setVisible() {
        isVisible = true;
    }

    public boolean getVisibility() {
        return isVisible;
    }
}