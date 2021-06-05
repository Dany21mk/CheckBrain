package space.mosk.checkbrain.Games;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.concurrent.TimeUnit;

public class Game2View extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {

    private SurfaceHolder holder;

    private Game2Thread gameThread;

    public Game2View(Context context){
        super(context);
        init();
    }

    private void init(){
        holder = getHolder();
        holder.addCallback(this);
        setOnTouchListener(this);
    }



    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Canvas canvas = holder.lockCanvas();
        canvas.drawColor(Color.WHITE);
        holder.unlockCanvasAndPost(canvas);
        gameThread = new Game2Thread(holder, this);
        gameThread.start();
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        gameThread.setRunning(false);
        while (gameThread.isAlive()){
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        gameThread.addNewBullet(event.getX(), event.getY());
        return false;
    }
}