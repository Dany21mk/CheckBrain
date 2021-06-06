package space.mosk.checkbrain.MainGame;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;

import space.mosk.checkbrain.R;
import space.mosk.checkbrain.ShopActivity;

public class GameThread extends Thread {
    private final SurfaceHolder holder;
    private boolean running;
    private boolean gameBool = true;
    private int baseRadius, xBase, yBase;
    protected int borderL, borderR;
    private Paint paint;
    protected static int lives = 3, score = 0, wave = 1;
    private ConcurrentLinkedDeque<EnemyGame> enemies;
    private ConcurrentLinkedDeque<BulletGame> bullets;
    private long enemySpawnTime;
    protected float cx, cy, radius;
    private long start;
    protected boolean game = true;
    ArrayList<Integer> arrayList = new ArrayList<>();

    public GameThread(SurfaceHolder holder) {
        this.holder = holder;
        running = true;
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.rgb(3, 218, 197));
        Rect rect = holder.getSurfaceFrame();
        baseRadius = 100;
        radius = 65;
        xBase = rect.right / 2;
        yBase = rect.bottom - (baseRadius * 2)-100;
        borderL = rect.left;
        borderR = rect.right;
        Random random = new Random() ;
        for (int i = 0; i < 30; i++) {
            arrayList.add((random.nextInt(rect.bottom + 1 - 5) + 5));
            arrayList.add((random.nextInt(rect.right + 1 - 5) + 5));
        }
        enemies = new ConcurrentLinkedDeque<EnemyGame>();
        bullets = new ConcurrentLinkedDeque<BulletGame>();
        start = System.currentTimeMillis();
        enemySpawnTime = 800;
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
        while (running) {
            synchronized (holder) {
                Canvas canvas = null;
                long current = System.currentTimeMillis();
                if (current - circleStartTime > enemySpawnTime) {
                    circleStartTime = current;
                    epochCount++;
                    cx = (float) ((Math.random() * (((borderR - radius) - (borderL+ radius)) + 1)) + (borderL + radius));
                    cy = radius + 5;
                    enemies.add(new EnemyGame(cx, radius));
                }
                if (epochCount > 10) {
                    epochCount = 0;
                    enemySpawnTime -= 50;
                    if (enemySpawnTime < 350) {
                        enemySpawnTime = 350;
                    }
                    GameMainActivity.coin += 2;
                    GameMainActivity.saveHistoryCoin();
                }
                    canvas = holder.lockCanvas();
                    draw(canvas);
                    updateParams();
                    start = current;
                    try{
                        holder.unlockCanvasAndPost(canvas);
                    } catch (Exception e){ }
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
        if (!gameBool || GameMainActivity.lives <= 0){
            paint.setColor(Color.RED);
            canvas.drawColor(Color.WHITE);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setAntiAlias(true);
            paint.setTextSize(65.0f);
            paint.setStrokeWidth(2.0f);
            paint.setStyle(Paint.Style.STROKE);
            if (GameMainActivity.lives <= 0){
                canvas.drawText("Game Over. Your Lives = 0", canvas.getWidth() / 2, canvas.getHeight()/2, paint);
            } else{
                canvas.drawText("Game Over", canvas.getWidth() / 2, canvas.getHeight()/2, paint);
            }
            return;
        }
        canvas.drawColor(Color.BLACK);
        paint.setColor(Color.WHITE);
        for (int i = 0; i < arrayList.size()-1; i++) {
            canvas.drawCircle(arrayList.get(i), arrayList.get(i+1), 5, paint);
        }

        if (GameMainActivity.rocket == 1){
            paint.setColor(Color.BLUE);
            paint.setShadowLayer(5.0f, 10.0f, 10.0f, Color.BLACK);
            canvas.drawCircle(xBase, yBase, baseRadius, paint);
            paint.setColor(Color.YELLOW);
            paint.setStrokeWidth(56);
            paint.setShadowLayer(0 , 0, 0 , Color.WHITE);
            canvas.drawLine(xBase,yBase-baseRadius*2,xBase, yBase-baseRadius, paint);
            canvas.drawLine(xBase+(baseRadius/2),yBase+baseRadius,xBase+baseRadius, canvas.getHeight(), paint);
            canvas.drawLine(xBase-(baseRadius/2),yBase+baseRadius,xBase-baseRadius, canvas.getHeight(), paint);
        } else if(GameMainActivity.rocket == 2){
            paint.setColor(Color.rgb(0,184,23));
            canvas.drawCircle(xBase, yBase, baseRadius, paint);
            paint.setShadowLayer(5.0f, 10.0f, 10.0f, Color.BLACK);
            paint.setColor(Color.YELLOW);
            paint.setStrokeWidth(56);
            paint.setShadowLayer(0 , 0, 0 , Color.WHITE);
            canvas.drawLine(xBase+(baseRadius/2),yBase-baseRadius*2,xBase+(baseRadius/2), yBase-baseRadius, paint);
            canvas.drawLine(xBase-(baseRadius/2),yBase-baseRadius*2,xBase-(baseRadius/2), yBase-baseRadius, paint);
            canvas.drawLine(xBase+(baseRadius/2),yBase+baseRadius,xBase+(baseRadius/2), canvas.getHeight()-100, paint);
            canvas.drawLine(xBase-(baseRadius/2),yBase+baseRadius,xBase-(baseRadius/2), canvas.getHeight()-100, paint);
        } else if(GameMainActivity.rocket == 3){
            paint.setColor(Color.rgb(210,15,119));
            canvas.drawCircle(xBase, yBase, baseRadius, paint);
            paint.setShadowLayer(5.0f, 10.0f, 10.0f, Color.BLACK);
            paint.setColor(Color.YELLOW);
            paint.setStrokeWidth(56);
            paint.setShadowLayer(0 , 0, 0 , Color.WHITE);
            canvas.drawLine(xBase+(baseRadius/2),yBase-baseRadius*2,xBase+(baseRadius/2), yBase-baseRadius, paint);
            canvas.drawLine(xBase-(baseRadius/2),yBase-baseRadius*2,xBase-(baseRadius/2), yBase-baseRadius, paint);
            canvas.drawLine(xBase,yBase-baseRadius*2,xBase, yBase-baseRadius, paint);
            canvas.drawLine(xBase,yBase+baseRadius,xBase, canvas.getHeight()-100, paint);
            canvas.drawLine(xBase+(baseRadius/2),yBase+baseRadius,xBase+(baseRadius/2), canvas.getHeight()-100, paint);
            canvas.drawLine(xBase-(baseRadius/2),yBase+baseRadius,xBase-(baseRadius/2), canvas.getHeight()-100, paint);
        } else{
            paint.setColor(Color.rgb(227,227,227));
            canvas.drawCircle(xBase, yBase, baseRadius, paint);
            paint.setShadowLayer(5.0f, 10.0f, 10.0f, Color.BLACK);
            paint.setColor(Color.CYAN);
            paint.setStrokeWidth(56);
            paint.setShadowLayer(0 , 0, 0 , Color.WHITE);
            canvas.drawCircle(xBase+(baseRadius/2), yBase-baseRadius*2, 50, paint);
            canvas.drawCircle(xBase-(baseRadius/2), yBase-baseRadius*2, 50, paint);
            canvas.drawCircle(xBase+(baseRadius/2), yBase+baseRadius, 50, paint);
            canvas.drawCircle(xBase-(baseRadius/2), yBase+baseRadius, 50, paint);
        }
        paint.setColor(Color.RED);
        for (EnemyGame enemy : enemies){
            canvas.drawCircle(enemy.getX(), enemy.getY(), enemy.getR(), paint);
        }
        paint.setColor(Color.WHITE);
        for (BulletGame bullet : bullets){
            canvas.drawCircle(bullet.getX(), bullet.getY(), bullet.getR(), paint);
        }
        paint.setColor(Color.WHITE);

        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);
        paint.setTextSize(65.0f);
        paint.setStrokeWidth(2.0f);
        paint.setStyle(Paint.Style.FILL);

        canvas.drawText("Lives: " +  GameMainActivity.lives, canvas.getWidth() - 150, canvas.getHeight() -50 , paint);
        canvas.drawText("Bullets: " + GameMainActivity.patron, 200, canvas.getHeight() -50, paint);
    }

    private void updateParams(){
        Rect rect = holder.getSurfaceFrame();
        if (GameMainActivity.isLeftPressed && xBase > baseRadius){
            xBase -= 20;
        }
        if (GameMainActivity.isRightPressed && xBase < borderR-baseRadius){
            xBase += 20;
        }
        if (GameMainActivity.isCenterPressed){
            Random random = new Random();
            if (GameMainActivity.patron <= 0){
                GameMainActivity.patron = 0;
            } else{
                if (GameMainActivity.rocket == 1){
                    bullets.add(new BulletGame(xBase,yBase-baseRadius*2 ));
                    GameMainActivity.patron -= 1;
                } else if(GameMainActivity.rocket == 2){
                    bullets.add(new BulletGame(xBase+(baseRadius/2),yBase-baseRadius*2 ));
                    bullets.add(new BulletGame(xBase-(baseRadius/2),yBase-baseRadius*2 ));
                    GameMainActivity.patron -= 2;
                } else if(GameMainActivity.rocket == 3){
                    bullets.add(new BulletGame(xBase,yBase-baseRadius*2 ));
                    bullets.add(new BulletGame(xBase+(baseRadius/2),yBase-baseRadius*2 ));
                    bullets.add(new BulletGame(xBase-(baseRadius/2),yBase-baseRadius*2 ));
                    GameMainActivity.patron -= 3;
                } else {
                    bullets.add(new BulletGame(random.nextInt(rect.right),yBase-baseRadius*2 ));
                    GameMainActivity.patron -= 1;
                }
                GameMainActivity.saveHistoryPatron();
            }
        }
        for (EnemyGame enemy : enemies) {
            for (BulletGame bullet : bullets) {
                if (!(Math.abs(enemy.getX() - bullet.getX()) > (radius + (radius * 80 / 100)) || Math.abs(enemy.getY() - bullet.getY()) > (radius + (radius * 80 / 100)) )){
                    enemies.remove(enemy);
                    bullets.remove(bullet);
                }
            }
        }
        for (EnemyGame enemy : enemies) {
            enemy.setWindowRect(rect);
            enemy.update();
            if (enemy.getY() + enemy.getR() >= rect.bottom){
                enemies.remove(enemy);
            }
        }
        for (BulletGame bullet : bullets) {
            bullet.setWindowRect(rect);
            bullet.update();
            if (bullet.getY()<radius){
                bullets.remove(bullet);
            }
        }
        if (enemies.peekLast() == null){
            cx = (float) ((Math.random() * (((rect.right - radius) - (rect.left + radius)) + 1)) + (rect.left + radius));
            cy = radius + 5;
            enemies.add(new EnemyGame(cx, radius));
            return;
        }
        for (EnemyGame enemy : enemies) {
            if (!(Math.abs(enemy.getX() - xBase) > baseRadius || Math.abs(enemy.getY() - yBase) > baseRadius)){
                GameMainActivity.lives-=1;
                GameMainActivity.saveHistoryLives();
                if (GameMainActivity.lives<=0){
                    gameBool = false;
                }
                enemies.remove(enemy);
            }
        }
    }
}
