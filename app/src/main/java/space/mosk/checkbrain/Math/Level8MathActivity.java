package space.mosk.checkbrain.Math;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Random;

import space.mosk.checkbrain.AuthActivity;
import space.mosk.checkbrain.R;

public class Level8MathActivity extends AppCompatActivity {

    Button btn_back;
    Dialog dialog;
    public int counter = 0;
    public int num1, num2, ans;
    Random random = new Random();
    public TextView ans_1;
    public TextView ans_2;
    public TextView ans_3;
    public TextView ans_4;

    public int rand = 0;
    public int answers[] = {-1, -1, -1, -1};
    public final int[] progress = {R.id.point1,R.id.point2,R.id.point3,R.id.point4,R.id.point5,R.id.point6,R.id.point7,R.id.point8,R.id.point9,R.id.point10,R.id.point11,R.id.point12,R.id.point13,R.id.point14,R.id.point15,R.id.point16,R.id.point17,R.id.point18,R.id.point19,R.id.point20};
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose2);
        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            startActivity(new Intent(Level8MathActivity.this, AuthActivity.class));
            finish();
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Level8MathActivity.this, MathActivity.class));
                overridePendingTransition(0,0);
            }
        });

        // Вызов dialog
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.previewdialog);
        TextView tx = dialog.findViewById(R.id.textTask);
        tx.setText(R.string.leveltwomath);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button btn_continue = dialog.findViewById(R.id.btn_continue);
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });



        dialog.show();

        ans_1 = findViewById(R.id.ans_1);
        ans_2 = findViewById(R.id.ans_2);
        ans_3 = findViewById(R.id.ans_3);
        ans_4 = findViewById(R.id.ans_4);

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
                    if (ans == answers[0]){
                        if (counter<20){
                            counter++;
                        }
                        handlerProgress(true);
                    } else {
                        if (counter>0){
                            if (counter==1){
                                counter=0;
                            } else {
                                counter-=2;
                            }
                        }
                        handlerProgress(false);
                    }
                    if (counter>=20){
                        Toast.makeText(Level8MathActivity.this, "Конец игры", Toast.LENGTH_SHORT).show();
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
                    if (ans == answers[1]){
                        if (counter<20){
                            counter++;
                        }
                        handlerProgress(true);
                    } else {
                        if (counter>0){
                            if (counter==1){
                                counter=0;
                            } else {
                                counter-=2;
                            }
                        }
                        handlerProgress(false);
                    }
                    if (counter>=20){
                        Toast.makeText(Level8MathActivity.this, "Конец игры", Toast.LENGTH_SHORT).show();
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
                    if (ans == answers[2]){
                        if (counter<20){
                            counter++;
                        }
                        handlerProgress(true);
                    } else {
                        if (counter>0){
                            if (counter==1){
                                counter=0;
                            } else {
                                counter-=2;
                            }
                        }
                        handlerProgress(false);
                    }
                    if (counter>=20){
                        Toast.makeText(Level8MathActivity.this, "Конец игры", Toast.LENGTH_SHORT).show();
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
                    if (ans == answers[3]){
                        if (counter<20){
                            counter++;
                        }
                        handlerProgress(true);
                    } else {
                        if (counter>0){
                            if (counter==1){
                                counter=0;
                            } else {
                                counter-=2;
                            }
                        }
                        handlerProgress(false);
                    }
                    if (counter>=20){
                        Toast.makeText(Level8MathActivity.this, "Конец игры", Toast.LENGTH_SHORT).show();
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
        if (bool){
            for (int i = 0; i < 20; i++) {
                TextView tvPr = findViewById(progress[i]);
                tvPr.setBackgroundResource(R.drawable.style_points);
            }
            for (int i = 0; i < counter; i++) {
                TextView tvPr = findViewById(progress[i]);
                tvPr.setBackgroundResource(R.drawable.style_points);
                tvPr.setBackgroundResource(R.drawable.style_points_true);
            }
        } else {
            for (int i = 0; i < 19; i++) {
                TextView tvPr = findViewById(progress[i]);
                tvPr.setBackgroundResource(R.drawable.style_points);
            }
            for (int i = 0; i < counter; i++) {
                TextView tvPr = findViewById(progress[i]);
                tvPr.setBackgroundResource(R.drawable.style_points);
                tvPr.setBackgroundResource(R.drawable.style_points_true);
            }
        }
    }

    public void handler() {
        tv = findViewById(R.id.task);
        num1 = rnd(10, 99);
        ans = num1 * 11;
        tv.setText(new StringBuilder().append(num1).append(" ⋅ ").append(11).toString());
        for (int i = 0; i < answers.length; i++) {
            answers[i] = -1;
        }
        rand = random.nextInt(4);
        answers[rand] = ans;
        int count = 0;
        for (int i = 0; i < answers.length; i++) {
            if (rand == i){
                continue;
            }
            answers[i] = rnd((ans-5), (ans+5));
            for (int j = 0; j < answers.length; j++) {
                if (answers[i] == answers[j]){
                    count++;
                }
            }
            while (count > 1){
                count = 0;
                answers[i] = rnd((ans-5), (ans+5));
                for (int j = 0; j < answers.length; j++) {
                    if (answers[i] == answers[j]){
                        count++;
                    }
                }
            }
        }
        ans_1.setText(new StringBuilder().append(answers[0]).toString());
        ans_2.setText(new StringBuilder().append(answers[1]).toString());
        ans_3.setText(new StringBuilder().append(answers[2]).toString());
        ans_4.setText(new StringBuilder().append(answers[3]).toString());
    }

    public static int rnd(int min, int max)
    {
        max -= min;
        return (int) (Math.random() * ++max) + min;
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
}