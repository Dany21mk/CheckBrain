package space.mosk.checkbrain.MainGame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.lang.reflect.Array;

import space.mosk.checkbrain.R;

public class GameMainActivity extends AppCompatActivity implements View.OnTouchListener {
    public static boolean isLeftPressed = false;
    public static boolean isRightPressed = false;
    public static boolean isCenterPressed = false;

    private static SharedPreferences preferencesRocket;
    private static SharedPreferences preferencesPatron;
    private static SharedPreferences preferencesLives;
    private static SharedPreferences preferencesCoin;

    public static int coin = 0, lives = 10, rocket=1, patron=1000;

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getRocket() {
        return rocket;
    }

    public void setRocket(int rocket) {
        this.rocket = rocket;
    }

    public int getPatron() {
        return patron;
    }

    public void setPatron(int patron) {
        this.patron = patron;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_main);
        GameView gameView = new GameView(this);
        LinearLayout gameLayout = findViewById(R.id.gameLayout);
        gameLayout.addView(gameView);
        Button leftButton = findViewById(R.id.leftButton);
        Button rightButton = findViewById(R.id.rightButton);
        Button centerButton = findViewById(R.id.centerButton);
        leftButton.setOnTouchListener(this);
        rightButton.setOnTouchListener(this);
        centerButton.setOnTouchListener(this);


        preferencesRocket = getSharedPreferences(getApplicationContext().getPackageName() + "_preferences", Context.MODE_PRIVATE);
        loadHistoryValue();
        if (rocket > 4){
            rocket=1;
        }

        preferencesPatron = getSharedPreferences(getApplicationContext().getPackageName() + "_preferences", Context.MODE_PRIVATE);
        loadHistoryValuePatron();

        preferencesLives = getSharedPreferences(getApplicationContext().getPackageName() + "_preferences", Context.MODE_PRIVATE);
        loadHistoryValueLives();

        if (lives < 0){
            lives =0;
        }

        preferencesCoin = getSharedPreferences(getApplicationContext().getPackageName() + "_preferences", Context.MODE_PRIVATE);
        loadHistoryValueCoin();

    }
    public boolean onTouch(View button, MotionEvent motion) {
        switch(button.getId()) {
            case R.id.leftButton:
                switch (motion.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isLeftPressed = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        isLeftPressed = false;
                        break;
                }
                break;
            case R.id.rightButton:
                switch (motion.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isRightPressed = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        isRightPressed = false;
                        break;
                }
                break;
            case R.id.centerButton:
                switch (motion.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isCenterPressed = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        isCenterPressed = false;
                        break;
                }
                break;
        }
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) { }

    private void loadHistoryValue(){
        int value = preferencesRocket.getInt("rocket", 1);
        updateNum(value);
    }

    private void saveHistory(){
        preferencesRocket.edit().putInt("rocket", (int) getIntValue()).apply();
    }
    private float getIntValue(){
        return rocket;
    }
    private void updateNum(int value){
        rocket = value;
    }



    private static void loadHistoryValuePatron(){
        int value = preferencesPatron.getInt("patron", 1000);
        updateNumPatron(value);
    }
    static void saveHistoryPatron(){
        preferencesPatron.edit().putInt("patron", (int) getIntValuePatron()).apply();
    }
    private static float getIntValuePatron(){
        return patron;
    }
    private static void updateNumPatron(int value){
        patron = value;
    }


    private static void loadHistoryValueLives(){
        int value = preferencesLives.getInt("lives", 10);
        updateNumLives(value);
    }
    static void saveHistoryLives(){
        preferencesLives.edit().putInt("lives", (int) getIntValueLives()).apply();
    }
    private static float getIntValueLives(){
        return lives;
    }
    private static void updateNumLives(int value){
        lives = value;
    }


    private static void loadHistoryValueCoin(){
        int value = preferencesCoin.getInt("money", 0);
        updateNumCoin(value);
    }
    static void saveHistoryCoin(){
        preferencesCoin.edit().putInt("money", (int) getIntValueCoin()).apply();
    }
    private static float getIntValueCoin(){
        return coin;
    }
    private static void updateNumCoin(int value){
        coin = value;
    }
}