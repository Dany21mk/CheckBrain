package space.mosk.checkbrain.Games;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;

import space.mosk.checkbrain.Games.model.Bullet;
import space.mosk.checkbrain.Games.model.Enemy;

public class Game2Thread extends Thread{
    private final SurfaceHolder holder;
    private int leftB, rightB, upB, downB;
    private int baseRadius, xBase, yBase;
    private Game2View gameView;
    private boolean running;
    private Paint paint;
    protected static int lives = 3, score = 0, wave = 1;
    private ConcurrentLinkedDeque<Enemy> enemies;
    private ConcurrentLinkedDeque<Bullet> bullets;
    private long enemySpawnTime;
    protected float cx, cy, radius;
    private long start;
    protected boolean game = true;


    public Game2Thread(SurfaceHolder holder, Game2View gameView) {
        this.holder = holder;
        this.gameView = gameView;
        running = true;
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.rgb(3, 218, 197));
        Rect rect = holder.getSurfaceFrame();
        baseRadius = 100;
        radius = 40;
        xBase = rect.right / 2;
        yBase = rect.bottom / 2;
        enemies = new ConcurrentLinkedDeque<>();
        bullets = new ConcurrentLinkedDeque<>();
        start =  System.currentTimeMillis();
        enemySpawnTime = 800;
    }


    public void addNewBullet(Float x, Float y){
        bullets.add(new Bullet(gameView, x, y));
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
        long enemyStartTime = startTime;
        int epochCount = 0;
        while (running) {
            synchronized (holder) {
                Canvas canvas = null;
                long current = System.currentTimeMillis();
                if (current - enemyStartTime > enemySpawnTime) {
                    enemyStartTime = current;
                    Rect rect = holder.getSurfaceFrame();


                    cx = (float) (Math.random() * (((rect.right + 750) - (-750)) + 1)) + (-750);
                    int rand = (int) (Math.random() * 2);
                    if (rand == 0) {
                        cy = (float) (-100 - Math.random() * 500);
                    } else {
                        cy = (float) (rect.bottom + 100 - Math.random() * 500);
                    }

                    epochCount++;
                    enemies.add(new Enemy(gameView ,cx, cy));
                }
                if (epochCount > 12) {
                    wave++;
                    epochCount = 0;
                    enemySpawnTime -= 100;
                    if (enemySpawnTime < 200) {
                        int mas[] = {400, 400, 400, 400,  450, 450, 400};
                        int rand = (int) (Math.random() * 7);
                        enemySpawnTime = mas[rand];
                    }
                }
                //  if (current - start > 1) {
                canvas = holder.lockCanvas();
                draw(canvas);
                updateParams();
                start = current;
                try {
                    holder.unlockCanvasAndPost(canvas);
                } catch (Exception e){}
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
        if (canvas == null){
            return;
        }
        if (game && lives > 0){
            canvas.drawColor(Color.WHITE);
            paint.setColor(Color.BLUE);
            paint.setShadowLayer(5.0f, 10.0f, 10.0f, Color.BLACK);
            canvas.drawCircle(xBase, yBase, baseRadius, paint);
            paint.setShadowLayer(0 , 0, 0 , Color.WHITE);
            paint.setColor(Color.RED);
            for (Enemy enemy : enemies) {
                canvas.drawCircle(enemy.getX(), enemy.getY(), enemy.getR(), paint);
            }
            paint.setColor(Color.BLACK);
            for (Bullet bullet : bullets) {
                canvas.drawCircle(bullet.getX(), bullet.getY(), bullet.getR(), paint);
            }

            paint.setColor(Color.RED);

            paint.setTextAlign(Paint.Align.CENTER);
            paint.setAntiAlias(true);
            paint.setTextSize(65.0f);
            paint.setStrokeWidth(2.0f);
            paint.setStyle(Paint.Style.FILL);

            canvas.drawText("Lives: " +  lives, canvas.getWidth() - 150, canvas.getHeight() -50 , paint);
            canvas.drawText("Score: " + score, 165, canvas.getHeight() -50, paint);
            canvas.drawText("Waves: " +  wave, canvas.getWidth() - 150, 165 , paint);
        } else {
            paint.setColor(Color.RED);
            canvas.drawColor(Color.WHITE);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setAntiAlias(true);
            paint.setTextSize(65.0f);
            paint.setStrokeWidth(2.0f);
            paint.setStyle(Paint.Style.STROKE);
            //paint.setShadowLayer(5.0f, 10.0f, 10.0f, Color.BLACK);

            canvas.drawText("Game Over, score: " + score, canvas.getWidth() / 2, canvas.getHeight()/2, paint);
        }

    }

    private void updateParams(){
      /*  cx += dx;
        cy += dy;*/
        Rect rect = holder.getSurfaceFrame();
        for (Enemy enemy : enemies) {
            enemy.setWindowRect(rect);
            enemy.update();
        }
        for (Bullet bullet : bullets) {
            bullet.setWindowRect(rect);
            bullet.update();
        }

        for (Enemy enemy : enemies) {
            for (Bullet bullet : bullets) {
                if (!(Math.abs(enemy.getX() - bullet.getX()) > (radius + (radius * 80 / 100)) || Math.abs(enemy.getY() - bullet.getY()) > (radius + (radius * 80 / 100)) )){
                    enemies.remove(enemy);
                    bullets.remove(bullet);
                    score++;
                }
            }
        }
        for (Enemy enemy : enemies) {
            if (!(Math.abs(enemy.getX() - xBase) > baseRadius || Math.abs(enemy.getY() - yBase) > baseRadius)){
                enemies.remove(enemy);
                lives--;
                if (lives == 0){
                    game = false;
                }
            }
        }
        for (Bullet bullet : bullets) {
            if (bullet.getX() + bullet.getR() >= rect.right || bullet.getX() - bullet.getR() <= rect.left || bullet.getY() + bullet.getR() >= rect.bottom || bullet.getY() - bullet.getR() <= rect.top){
                bullets.remove(bullet);
            }
        }
        if (wave % 10 == 0){
            for (Enemy enemy : enemies) {
                enemy.addSpeed();
            }
        }
    }
}
