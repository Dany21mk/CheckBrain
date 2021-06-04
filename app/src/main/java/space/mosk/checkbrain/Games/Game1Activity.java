package space.mosk.checkbrain.Games;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import space.mosk.checkbrain.R;

public class Game1Activity extends AppCompatActivity {

    private TextView counter;
    private ImageButton click;
    private SharedPreferences preferences;
    private TextView textCookieAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game1);
        counter = findViewById(R.id.txt_counter);
        textCookieAdd = findViewById(R.id.add_cookie);
        click = findViewById(R.id.cookie);
        preferences = getPreferences(Context.MODE_PRIVATE);
        loadHistoryValue();
        click.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    click.setScaleX((float) 1.1);
                    click.setScaleY((float) 1.1);
                    if (getIntValue() % 1000 == 0){
                        textCookieAdd.setText("+1");
                        textCookieAdd.setTextColor(Color.rgb(210, 190,74));
                    }
                }  else if (event.getAction() == MotionEvent.ACTION_UP) {
                    click.setScaleX((float) 1);
                    click.setScaleY((float) 1);
                    if (getIntValue() % 100 == 0){
                        textCookieAdd.setText("");
                        textCookieAdd.setTextColor(Color.rgb(255, 255,255));
                    }
                    updateNum(getIntValue()+1);
                }
                return false;
            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        saveHistory();
    }


    private void loadHistoryValue(){
        int value = preferences.getInt("cookie", 0);
        updateNum(value);
    }

    private void saveHistory(){
        preferences.edit().putInt("cookie", getIntValue()).apply();
    }
    private int getIntValue(){
        return Integer.parseInt(counter.getText().toString());
    }
    private void updateNum(int value){
        counter.setText(String.valueOf(value));
    }
}