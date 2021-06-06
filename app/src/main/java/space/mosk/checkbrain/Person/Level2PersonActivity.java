package space.mosk.checkbrain.Person;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Random;

import space.mosk.checkbrain.Array;
import space.mosk.checkbrain.AuthActivity;
import space.mosk.checkbrain.Histrory.HisoryActivity;
import space.mosk.checkbrain.R;

public class Level2PersonActivity extends AppCompatActivity {

    Button btn_back;
    Dialog dialog;
    public int counter = 0, lives = 3;
    public TextView ans_1;
    public TextView ans_2;
    public TextView ans_3;
    public TextView ans_4;
    Array array = new Array();
    public String ans = "";
    ArrayList<Integer> arrayList = new ArrayList<>();

    ImageView heart1;
    ImageView heart2;
    ImageView heart3;

    private int money = 0;

    private SharedPreferences preferences;

    public int rand = 0;
    public String answers[] = {"", "", "", ""};
    public final int[] progress = {R.id.point1,R.id.point2,R.id.point3,R.id.point4,R.id.point5,R.id.point6,R.id.point7,R.id.point8,R.id.point9,R.id.point10,R.id.point11,R.id.point12,R.id.point13,R.id.point14,R.id.point15,R.id.point16,R.id.point17,R.id.point18,R.id.point19,R.id.point20};
    TextView tv;
    Random random = new Random();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_lives);
        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            startActivity(new Intent(Level2PersonActivity.this, AuthActivity.class));
            finish();
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Level2PersonActivity.this, HisoryActivity.class));
                overridePendingTransition(0,0);
            }
        });

        preferences = getSharedPreferences(getApplicationContext().getPackageName() + "_preferences", Context.MODE_PRIVATE);
        loadHistoryValue();

        // Вызов dialog
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.previewdialog);
        TextView tx = dialog.findViewById(R.id.textTask);
        tx.setText("Предприниматели");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button btn_continue = dialog.findViewById(R.id.btn_continue);
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        heart1 = findViewById(R.id.heart1);
        heart2 = findViewById(R.id.heart2);
        heart3 = findViewById(R.id.heart3);

        heart1.setScaleX((float) 0.5);
        heart1.setScaleY((float) 0.5);
        heart2.setScaleX((float) 0.5);
        heart2.setScaleY((float) 0.5);
        heart3.setScaleX((float) 0.5);
        heart3.setScaleY((float) 0.5);



        dialog.show();

        ans_1 = findViewById(R.id.ans_1);
        ans_2 = findViewById(R.id.ans_2);
        ans_3 = findViewById(R.id.ans_3);
        ans_4 = findViewById(R.id.ans_4);

        ans_1.setTextSize(25);
        ans_2.setTextSize(25);
        ans_3.setTextSize(25);
        ans_4.setTextSize(25);


        handler();

        ans_1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    clearBtn(false);
                    ans_1.setEnabled(true);
                    if (ans == answers[0]){
                        ans_1.setText("Да");
                        ans_1.setBackgroundColor(Color.rgb(0, 255, 0));
                        ans_1.setTextColor(Color.rgb(255, 255, 255));
                    }else {
                        ans_1.setText("Нет");
                        ans_1.setBackgroundColor(Color.rgb(255, 0, 0));
                        ans_1.setTextColor(Color.rgb(255, 255, 255));
                    }
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                    counter++;
                    if (ans == answers[0]){
                        handlerProgress(true);
                    } else {
                        handlerProgress(false);
                    }
                    if (counter>=20){
                        gameover();
                    } else {
                        handler();
                        clearBtn(true);
                        ans_1.setBackgroundResource(R.drawable.style_choose_btn);
                        ans_1.setTextColor(Color.rgb(0,0,0));
                    }
                }
                return true;
            }
        });

        ans_2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    clearBtn(false);
                    ans_2.setEnabled(true);
                    if (ans == answers[1]){
                        ans_2.setText("Да");
                        ans_2.setBackgroundColor(Color.rgb(0, 255, 0));
                        ans_2.setTextColor(Color.rgb(255, 255, 255));
                    }else {
                        ans_2.setText("Нет");
                        ans_2.setBackgroundColor(Color.rgb(255, 0, 0));
                        ans_2.setTextColor(Color.rgb(255, 255, 255));
                    }
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                    counter++;
                    if (ans == answers[1]){
                        handlerProgress(true);
                    } else {
                        handlerProgress(false);
                    }
                    if (counter>=20){
                        gameover();
                    } else {
                        handler();
                        clearBtn(true);
                        ans_2.setBackgroundResource(R.drawable.style_choose_btn);
                        ans_2.setTextColor(Color.rgb(0,0,0));
                    }
                }
                return true;
            }
        });

        ans_3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    clearBtn(false);
                    ans_3.setEnabled(true);
                    if (ans == answers[2]){
                        ans_3.setText("Да");
                        ans_3.setBackgroundColor(Color.rgb(0, 255, 0));
                        ans_3.setTextColor(Color.rgb(255, 255, 255));
                    }else {
                        ans_3.setText("Нет");
                        ans_3.setBackgroundColor(Color.rgb(255, 0, 0));
                        ans_3.setTextColor(Color.rgb(255, 255, 255));
                    }
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                    counter++;
                    if (ans == answers[2]){
                        handlerProgress(true);
                    } else {
                        handlerProgress(false);
                    }
                    if (counter>=20){
                        gameover();
                    } else {
                        handler();
                        clearBtn(true);
                        ans_3.setBackgroundResource(R.drawable.style_choose_btn);
                        ans_3.setTextColor(Color.rgb(0,0,0));
                    }
                }
                return true;
            }
        });

        ans_4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    clearBtn(false);
                    ans_4.setEnabled(true);
                    if (ans == answers[3]){
                        ans_4.setText("Да");
                        ans_4.setBackgroundColor(Color.rgb(0, 255, 0));
                        ans_4.setTextColor(Color.rgb(255, 255, 255));
                    }else {
                        ans_4.setText("Нет");
                        ans_4.setBackgroundColor(Color.rgb(255, 0, 0));
                        ans_4.setTextColor(Color.rgb(255, 255, 255));
                    }
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                    counter++;
                    if (ans == answers[3]){
                        handlerProgress(true);
                    } else {
                        handlerProgress(false);
                    }
                    if (counter>=20){
                        gameover();
                    } else {
                        handler();
                        clearBtn(true);
                        ans_4.setBackgroundResource(R.drawable.style_choose_btn);
                        ans_4.setTextColor(Color.rgb(0,0,0));
                    }
                }
                return true;
            }
        });



    }

    public void handlerProgress(boolean bool) {
        TextView tvPr = findViewById(progress[counter-1]);
        if (bool){
            tvPr.setBackgroundResource(R.drawable.style_points_true);
        } else {
            tvPr.setBackgroundResource(R.drawable.style_points_false);
            lives--;
            if (lives == 2){
                heart3.setImageResource(R.drawable.heart_open);
            }else if(lives == 1){
                heart2.setImageResource(R.drawable.heart_open);
            } else{
                heart1.setImageResource(R.drawable.heart_open);
                livesover();
            }
        }
    }

    public void handler() {
        rand = 0;
        int cnt = -1;
        while (cnt != 0){
            cnt = 0;
            rand = random.nextInt(array.person2.length);
            for (int i = 0; i < arrayList.size(); i++){
                if (rand == arrayList.get(i)){
                    cnt+=1;
                    break;
                }
            }
        }

        arrayList.add(rand);

        tv = findViewById(R.id.task);
        tv.setTextSize(20);
        ans = array.person2Ans[rand];
        tv.setText(array.person2[rand]);
        for (int i = 0; i < answers.length; i++) {
            answers[i] = "";
        }
        rand = random.nextInt(4);
        answers[rand] = ans;
        int count = 0;
        for (int i = 0; i < answers.length; i++) {
            if (rand == i){
                continue;
            }
            answers[i] = array.person2Ans[rand];
            for (int j = 0; j < answers.length; j++) {
                if (answers[i] == answers[j]){
                    count++;
                }
            }
            while (count > 1){
                count = 0;
                int rnd  = random.nextInt(4);
                answers[i] = array.person2Ans[rnd];
                for (int j = 0; j < answers.length; j++) {
                    if (answers[i] == answers[j]){
                        count++;
                    }
                }
            }
        }
        ans_1.setText(answers[0]);
        ans_2.setText(answers[1]);
        ans_3.setText(answers[2]);
        ans_4.setText(answers[3]);
    }

    public void clearBtn(boolean bool){
        if (bool){
            ans_1.setEnabled(true);
            ans_2.setEnabled(true);
            ans_3.setEnabled(true);
            ans_4.setEnabled(true);
        } else {
            ans_1.setEnabled(false);
            ans_2.setEnabled(false);
            ans_3.setEnabled(false);
            ans_4.setEnabled(false);
        }
    }
    public void gameover(){
        if (lives == 0){
            livesover();
            return;
        }
        // Вызов dialog
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.previewdialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        TextView tx = dialog.findViewById(R.id.textTask);
        tx.setText("Уровень успешно пройден");
        Button btn_continue = dialog.findViewById(R.id.btn_continue);
        btn_continue.setText("Выйти в меню");
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                startActivity(new Intent(Level2PersonActivity.this, PersonActivity.class));
                finish();
            }
        });

        ImageView iv = dialog.findViewById(R.id.previewimg);
        iv.setImageResource(R.drawable.coin_big);
        updateNum(money);
        saveHistory();

        dialog.show();
    }
    public void livesover(){
        // Вызов dialog
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.previewdialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        TextView tx = dialog.findViewById(R.id.textTask);
        tx.setText("У вас закончились жизни");
        Button btn_continue = dialog.findViewById(R.id.btn_continue);
        btn_continue.setText("Выйти в меню");
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Level2PersonActivity.this, PersonActivity.class));
                finish();
            }
        });

        dialog.show();
    }
    private void loadHistoryValue(){
        int value = preferences.getInt("money", 0);
        updateNum(value);
    }

    private void saveHistory(){
        preferences.edit().putInt("money",  getIntValue()).apply();
    }
    private int getIntValue(){
        return money;
    }
    private void updateNum(int value){
        money = value+4;
    }
}
