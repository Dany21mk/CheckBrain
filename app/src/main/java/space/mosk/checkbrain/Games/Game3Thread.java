package space.mosk.checkbrain.Games;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;

import space.mosk.checkbrain.Games.model.Circle;
import space.mosk.checkbrain.Games.model.Enemy2;

public class Game3Thread extends Thread {

    private final SurfaceHolder holder;
    private boolean running;
    private int downB;

    private static int counter = 1;

    protected static  int lives = 3, score = 0;

    protected boolean game = true;

    private float radius;
    private long start;
    private Paint paint;

    private boolean baseBool = false;

    private long circleSpawnTime;

    private float dx, dy, cx, cy;

    private ConcurrentLinkedDeque<Circle> circles;

    private ConcurrentLinkedDeque<Enemy2> enemies;

    private static int counterCheckBase = 0;



    public Game3Thread(SurfaceHolder holder) {
        this.holder = holder;
        running = true;
        radius = 180;
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.rgb(76, 175, 80));
        Rect rect = holder.getSurfaceFrame();
        downB = rect.bottom;
        dx = dy = 5;
        start =  System.currentTimeMillis();
        circles = new ConcurrentLinkedDeque<>();
        enemies = new ConcurrentLinkedDeque<Enemy2>();
        cx = (float) ((Math.random() * (((rect.right - radius) - (rect.left + radius)) + 1)) + (rect.left + radius));
        cy = radius + 5;
        circleSpawnTime = 500;
        circles.add(new Circle(cx, radius));
    }

    public void checkPos(Float x, Float y){
        for (Circle circle : circles) {
            if (!(Math.abs(x - circle.getX()) > radius || Math.abs(y - circle.getY()) > radius)){
                circles.remove(circle);
                score++;
            }
        }
        Rect rect = holder.getSurfaceFrame();
        for (Enemy2 enemy : enemies){
            if (!(Math.abs(x - enemy.getX()) > enemy.getR() || Math.abs(y - enemy.getY()) > enemy.getR())){
                lives--;
                if (lives <= 0){
                    //counter = rect.bottom;
                    game = false;
                }
                enemies.remove(enemy);
            }
        }
    }

    public SurfaceHolder getHolder() {
        return holder;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        long circleStartTime = startTime;
        int epochCount = 0;
        int enemyCount = 0;
        int baseCount = 0;
        while (running) {
            synchronized (holder) {
                Canvas canvas = null;
                long current = System.currentTimeMillis();
                Rect rect = holder.getSurfaceFrame();
                if (current - circleStartTime > circleSpawnTime) {
                    circleStartTime = current;
                    epochCount++;
                    enemyCount++;
                    baseCount++;
                    cx = (float) ((Math.random() * (((rect.right - radius) - (rect.left + radius)) + 1)) + (rect.left + radius));
                    cy = radius + 5;
                    if (!baseBool){
                        circles.add(new Circle(cx, radius));
                    }
                }
                if (epochCount > 5) {
                    epochCount = 0;
                    circleSpawnTime -= 50;
                    if (circleSpawnTime < 200) {
                        circleSpawnTime = 200;
                    }
                }
                if (enemyCount > 50){
                    enemyCount = 0;
                    if (!baseBool) {
                        enemies.add(new Enemy2(cx, radius));
                    }
                }
                /*if (baseCount > 75){
                    baseCount = 0;
                    cx = (float) ((Math.random() * (((rect.right - 280) - (rect.left + 280)) + 1)) + (rect.left + 280));
                    cy = 280;
                    if (!baseBool){
                        bases.add(new Base(cx, cy));
                        baseBool = true;
                    }
                }*/
                //  if (current - start > 1) {
                canvas = holder.lockCanvas();
                try{draw(canvas);}catch (Exception e){}
                updateParams();
                start = current;
                try{holder.unlockCanvasAndPost(canvas);}catch (Exception e){}
                //  }
                try {
                    TimeUnit.MICROSECONDS.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void draw(Canvas canvas){
        if (game){
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.rgb(76, 175, 80));
            try {
                canvas.drawColor(Color.rgb(183,239,237));
            } catch (Exception e){}
            for (Circle circle : circles){
               try {
                   canvas.drawCircle(circle.getX(), circle.getY(), circle.getR(), paint);
               }catch (Exception e){}
            }
            paint.setColor(Color.RED);
            for (Enemy2 enemy : enemies){
                canvas.drawCircle(enemy.getX(), enemy.getY(), enemy.getR(), paint);
            }
            paint.setColor(Color.rgb(255,141,20));
            paint.setColor(Color.rgb(255,141,20));
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(10 * counter*2);
            if (counter*20 > canvas.getHeight() - (canvas.getHeight() * 40) / 100){
                game = false;
            }
            canvas.drawLine(0, canvas.getHeight(), canvas.getWidth(), canvas.getHeight(), paint);
        } else {
            paint.setColor(Color.RED);
            canvas.drawColor(Color.WHITE);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setAntiAlias(true);
            paint.setTextSize(65.0f);
            paint.setStrokeWidth(2.0f);
            paint.setStyle(Paint.Style.STROKE);
            // paint.setShadowLayer(5.0f, 10.0f, 10.0f, Color.BLACK);

            canvas.drawText("Game Over, Ваш счёт: " + score, canvas.getWidth() / 2, canvas.getHeight()/2, paint);
        }
        /*paint.setTextSize(30);
        canvas.drawText("Hello World", 10, 25,paint);*/
    }


    private void updateParams(){
        Rect rect = holder.getSurfaceFrame();
        for (Circle circle : circles) {
            circle.setWindowRect(rect);
            circle.update();
            if (circle.getY() + circle.getR() >= rect.bottom){
                circles.remove(circle);
                counter++;
            }
        }
        //int procent = (18 * rect.bottom) / 100;
        if (circles.peekLast() == null){
            cx = (float) ((Math.random() * (((rect.right - radius) - (rect.left + radius)) + 1)) + (rect.left + radius));
            cy = radius + 5;
            circles.add(new Circle(cx, radius));
            return;
        }

        for (Enemy2 enemy : enemies){
            enemy.setWindowRect(rect);
            enemy.update();
            if (enemy.getY() + enemy.getR() >= rect.bottom) {
                enemies.remove(enemy);
            }
        }


        for (Circle circle : circles){
            for (Enemy2 enemy : enemies){
                if (!(Math.abs(circle.getX() - enemy.getX()) > radius || Math.abs(circle.getY() - enemy.getY()) > radius)){
                    circles.remove(circle);
                    //circle.setDy(circle.getDy() * -1);
                }
            }
        }
    }
}
