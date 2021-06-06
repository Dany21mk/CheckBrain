package space.mosk.checkbrain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ShopActivity extends AppCompatActivity {
    ImageView img;

    ImageButton livesBtn;

    TextView tvCoin;
    TextView tvLives;

    Button btn_back;


    private int[] rocketImgBtn = {R.id.rocket1, R.id.rocket2, R.id.rocket3, R.id.rocket4};
    private int[] patronBtn = {R.id.patron1,
                               R.id.patron2,
                               R.id.patron3,
                               R.id.patron4,
                               R.id.patron5,
                               R.id.patron6,
                               R.id.patron7,
                               R.id.patron8,
                               R.id.patron9};
    private int[] rocketImg = {R.drawable.rocket1, R.drawable.rocket2, R.drawable.rocket3, R.drawable.rocket4};

    public int rocket = 1, patron = 1000, lives = 10, coin = 0;

    private SharedPreferences preferencesRocket;
    private SharedPreferences preferencesPatron;
    private SharedPreferences preferencesLives;
    private SharedPreferences preferencesCoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // back_btn
        btn_back = findViewById(R.id.btn_back_chat);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShopActivity.this, DashBoard.class));
                overridePendingTransition(0,0);
            }
        });

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
            lives = 0;
        }

        preferencesCoin = getSharedPreferences(getApplicationContext().getPackageName() + "_preferences", Context.MODE_PRIVATE);
        loadHistoryValueCoin();

        tvCoin = findViewById(R.id.coin);
        tvCoin.setText(String.valueOf("coin: " + coin));

        tvLives = findViewById(R.id.heart_lives);
        tvLives.setText(String.valueOf("lives: " + lives));

        livesBtn = findViewById(R.id.lives);
        livesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (coin < 10){
                    Toast.makeText(ShopActivity.this, "У вас недостаточно средств", Toast.LENGTH_SHORT).show();
                    updateNumCoin(coin);
                    saveHistoryCoin();
                    return;
                }
                coin-=10;
                tvCoin.setText(String.valueOf(coin));
                lives+=10;
                tvLives.setText(String.valueOf("lives: " + lives));
                updateNum(lives);
                saveHistoryLives();
            }
        });



        ImageButton actImg = findViewById(rocketImgBtn[rocket-1]);
        actImg.setImageResource(R.drawable.img_choose);
        img = findViewById(R.id.rocket_choose);
        img.setImageResource(rocketImg[rocket-1]);

        for (int i = 0; i < rocketImgBtn.length; i++) {
            ImageButton iv = findViewById(rocketImgBtn[i]);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int cnt = 0;
                    int money = 0;
                    switch (v.getId()){
                        case R.id.rocket1:
                            cnt=0;
                            money = 100;
                            break;
                        case R.id.rocket2:
                            cnt=1;
                            money = 500;
                            break;
                        case R.id.rocket3:
                            cnt=2;
                            money = 1000;
                            break;
                        case R.id.rocket4:
                            cnt=3;
                            money = 2000;
                            break;
                    }
                    if (money > coin){
                        if (rocket==cnt+1){
                            return;
                        }
                        Toast.makeText(ShopActivity.this, "У вас недостаточно средств", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    coin -= money;
                    tvCoin.setText(String.valueOf(coin));
                    updateNumCoin(coin);
                    saveHistoryCoin();
                    rocket = cnt+1;
                    updateNum(rocket);
                    saveHistory();
                    imgFill();
                    ImageButton actImg = findViewById(rocketImgBtn[rocket-1]);
                    actImg.setImageResource(R.drawable.img_choose);
                    img = findViewById(R.id.rocket_choose);
                    img.setImageResource(rocketImg[rocket-1]);
                }
            });
        }

        for (int i = 0; i < patronBtn.length; i++) {
            Button btn = findViewById(patronBtn[i]);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int k = 0;
                    int money = 0;
                    switch (v.getId()){
                        case R.id.patron1:
                            k=100;
                            money=50;
                            break;
                        case R.id.patron2:
                            k=200;
                            money=70;
                            break;
                        case R.id.patron3:
                            k=300;
                            money=90;
                            break;
                        case R.id.patron4:
                            k=600;
                            money=160;
                            break;
                        case R.id.patron5:
                            k=700;
                            money=180;
                            break;
                        case R.id.patron6:
                            k=1000;
                            money=200;
                            break;
                        case R.id.patron7:
                            k=2000;
                            money=350;
                            break;
                        case R.id.patron8:
                            k=3000;
                            money=450;
                            break;
                        case R.id.patron9:
                            k=10000;
                            money=500;
                            break;
                    }
                    if (money > coin){
                        Toast.makeText(ShopActivity.this, "У вас недостаточно средств", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    coin -= money;
                    tvCoin.setText(String.valueOf(coin));
                    updateNumCoin(coin);
                    saveHistoryCoin();
                    patron = patron+k;
                    updateNumPatron(patron);
                    saveHistoryPatron();
                }
            });
        }


    }

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



    private void loadHistoryValuePatron(){
        int value = preferencesPatron.getInt("patron", 1000);
        updateNumPatron(value);
    }
    private void saveHistoryPatron(){
        preferencesPatron.edit().putInt("patron", (int) getIntValuePatron()).apply();
    }
    private float getIntValuePatron(){
        return patron;
    }
    private void updateNumPatron(int value){
        patron = value;
    }


    private void loadHistoryValueLives(){
        int value = preferencesLives.getInt("lives", 10);
        updateNumLives(value);
    }
    private void saveHistoryLives(){
        preferencesLives.edit().putInt("lives", (int) getIntValueLives()).apply();
    }
    private float getIntValueLives(){
        return lives;
    }
    private void updateNumLives(int value){
        lives = value;
    }


    private void loadHistoryValueCoin(){
        int value = preferencesCoin.getInt("money", 0);
        updateNumCoin(value);
    }
    private void saveHistoryCoin(){
        preferencesCoin.edit().putInt("money", (int) getIntValueCoin()).apply();
    }
    private float getIntValueCoin(){
        return coin;
    }
    private void updateNumCoin(int value){
        coin = value;
    }

    public void imgFill(){
        for (int i = 0; i < rocketImgBtn.length; i++) {
            ImageButton iv = findViewById(rocketImgBtn[i]);
            iv.setImageResource(rocketImg[i]);
        }
    }
}