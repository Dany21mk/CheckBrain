package space.mosk.checkbrain.Games.model;

import android.graphics.Rect;

import space.mosk.checkbrain.Games.Game2Thread;
import space.mosk.checkbrain.Games.Game2View;

import static java.lang.Math.sqrt;

public class Enemy {
    private float x, y, r;
    private float dx, dy;
    private Rect window;
    public Game2Thread gameThread;
    protected float speed;

    public Enemy(Game2View gameView , float x, float y) {
        this.x = x;
        this.y = y;
        speed = 7;
        r = 80;
        float touchX = gameView.getRight() / 2;
        float touchY = gameView.getBottom() / 2;
        float enemyX = touchX - x;
        float enemyY = touchY - y;
        double sqrt = sqrt(enemyX * enemyX + enemyY * enemyY);
        dx = (float) (speed * (enemyX / sqrt));
        dy = (float) (speed * (enemyY / sqrt));
    }

    public void addSpeed(){
        dx *= 1.2;
        dy *= 1.2;
    }

    public void setWindowRect(Rect rect){
        window = rect;
    }

    public void update(){
        if (window == null){
            return;
        }
        x += dx;
        y += dy;
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

    public float getDx() {
        return dx;
    }

    public float getDy() {
        return dy;
    }

    public void setDx(float dx) {
        this.dx = dx;
    }

    public void setDy(float dy) {
        this.dy = dy;
    }

    public void setR(float r) {
        this.r = r;
    }
}