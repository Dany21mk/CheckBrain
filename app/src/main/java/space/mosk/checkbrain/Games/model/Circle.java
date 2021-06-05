package space.mosk.checkbrain.Games.model;

import android.graphics.Rect;

public class Circle {
    private float x, y, r;
    private float dy;
    private Rect window;

    public Circle(float x, float y) {
        this.x = x;
        this.y = y;
        r = 120;
        dy = (float) (Math.random() * ((25 - 13) + 1)) + 13;
    }

    public void setWindowRect(Rect rect){
        window = rect;
    }

    public void update(){
        if (window == null){
            return;
        }
        y += dy;
        if (y + r >= window.bottom || y - r <= window.top){
            dy *= -1;
        }
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getR() {
        return r;
    }
    public float getDy() {
        return dy;
    }
    public void setDy(float dy) {
        this.dy = dy;
    }
    public void setR(float r) {
        this.r = r;
    }



}
