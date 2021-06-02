package space.mosk.checkbrain.ChooseTrue;

import android.app.Dialog;
import android.content.Intent;
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
import space.mosk.checkbrain.Math.MathActivity;
import space.mosk.checkbrain.R;

public class Level3ChooseTrueActivity extends AppCompatActivity {

    Button btn_back;
    Dialog dialog;
    ImageView heart1;
    ImageView heart2;
    ImageView heart3;

    public int numLeft, numRight;
    Array array = new Array();
    Random random = new Random();
    public int counter = 0;
    public int lives = 3;
    TextView text_task;
    public boolean boolAns = false;


    ArrayList<Integer> arrayList = new ArrayList<>();
    final int[] progress = {R.id.point1,R.id.point2,R.id.point3,R.id.point4,R.id.point5,R.id.point6,R.id.point7,R.id.point8,R.id.point9,R.id.point10,R.id.point11,R.id.point12,R.id.point13,R.id.point14,R.id.point15,R.id.point16,R.id.point17,R.id.point18,R.id.point19,R.id.point20};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose3);
        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            startActivity(new Intent(Level3ChooseTrueActivity.this, AuthActivity.class));
            finish();
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        text_task = findViewById(R.id.task);

        heart1 = findViewById(R.id.heart1);
        heart2 = findViewById(R.id.heart2);
        heart3 = findViewById(R.id.heart3);

        final TextView ans_1 = findViewById(R.id.ans_1);
        final TextView ans_2 = findViewById(R.id.ans_2);

        // back_btn
        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Level3ChooseTrueActivity.this, ChooseTrueActivity.class));
                overridePendingTransition(0,0);
            }
        });

        // Вызов dialog
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.previewdialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView tx = dialog.findViewById(R.id.textTask);
        tx.setText("Правда или ложь. Уровень 1");
        Button btn_continue = dialog.findViewById(R.id.btn_continue);
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });



        dialog.show();


        handler();


        ans_1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    ans_2.setEnabled(false);
                    if (!boolAns){
                        ans_1.setText("Да");
                        ans_1.setBackgroundColor(Color.rgb(0, 255, 0));
                        ans_1.setTextColor(Color.rgb(255, 255, 255));
                    }else {
                        ans_1.setText("Нет");
                        ans_1.setBackgroundColor(Color.rgb(255, 0, 0));
                        ans_1.setTextColor(Color.rgb(255, 255, 255));
                    }
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (!boolAns) {
                        if (counter < 10) {
                            counter++;
                        }
                        handlerProgress(true);
                    } else {
                        if (counter < 10) {
                            counter++;
                        }
                        handlerProgress(false);
                    }
                    if (counter >= 10) {
                        gameover();
                    } else {
                        handler();
                        ans_2.setEnabled(true);
                        ans_1.setText("Ложь");
                        ans_1.setBackgroundResource(R.drawable.style_choose_btn_fl);
                        ans_1.setTextColor(Color.rgb(0, 0, 0));
                    }
                }
                return true;
            }
        });


        ans_2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    ans_1.setEnabled(false);
                    if (boolAns){
                        ans_2.setText("Да");
                        ans_2.setBackgroundColor(Color.rgb(0, 255, 0));
                        ans_2.setTextColor(Color.rgb(255, 255, 255));
                    }else {
                        ans_2.setText("Нет");
                        ans_2.setBackgroundColor(Color.rgb(255, 0, 0));
                        ans_2.setTextColor(Color.rgb(255, 255, 255));
                    }
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (boolAns) {
                        if (counter < 10) {
                            counter++;
                        }
                        handlerProgress(true);
                    } else {
                        if (counter < 10) {
                            counter++;
                        }
                        handlerProgress(false);
                    }
                    if (counter >= 10) {
                        gameover();
                    } else {
                        handler();
                        ans_1.setEnabled(true);
                        ans_2.setText("Правда");
                        ans_2.setBackgroundResource(R.drawable.style_choose_btn_tr);
                        ans_2.setTextColor(Color.rgb(0, 0, 0));
                    }
                }
                return true;
            }
        });
    }
    public void handler(){
        int num = 0;
        int cnt = -1;
        while (cnt != 0){
            cnt = 0;
            num = random.nextInt(array.choose_true3.length);
            for (int i = 0; i < arrayList.size(); i++){
                if (num == arrayList.get(i)){
                    cnt+=1;
                    break;
                }
            }
        }

        arrayList.add(num);
        text_task.setText(array.choose_true3[num]);
        boolAns = array.chooseTrueAns3[num];
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
                        startActivity(new Intent(Level3ChooseTrueActivity.this, ChooseTrueActivity.class));
                        finish();
                    }
                });

                dialog.show();
            }
        }
    }
    public void gameover(){
        if (lives == 0){
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
                startActivity(new Intent(Level3ChooseTrueActivity.this, ChooseTrueActivity.class));
                finish();
            }
        });

        dialog.show();
    }
}