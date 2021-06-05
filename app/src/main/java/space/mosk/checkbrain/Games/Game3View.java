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

public class Game3View extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {

    private Game3Thread gameThread;

    private SurfaceHolder holder;

    public Game3View(Context context) {
        super(context);
        init();
    }


    private void init() {
        holder = getHolder();
        holder.addCallback(this);
        setOnTouchListener(this);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Canvas canvas = holder.lockCanvas();
        canvas.drawColor(Color.WHITE);
        try {
            holder.unlockCanvasAndPost(canvas);
        } catch (Exception e){}
        gameThread = new Game3Thread(holder);
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        gameThread.setRunning(false);
        while (gameThread.isAlive()) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        gameThread.checkPos(event.getX(), event.getY());
        return false;
    }
}
