package space.mosk.checkbrain.MainGame;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import space.mosk.checkbrain.MainGame.GameThread;
import space.mosk.checkbrain.R;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {

    private GameThread gameThread;

    private SurfaceHolder holder;

    public GameView(Context context) {
        super(context);
        init();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        holder = getHolder();
        holder.addCallback(this);
        setOnTouchListener(this);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        Canvas canvas = holder.lockCanvas();
        canvas.drawColor(R.drawable.space);
        holder.unlockCanvasAndPost(canvas);
        gameThread = new GameThread(holder);
        gameThread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
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
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }
}
