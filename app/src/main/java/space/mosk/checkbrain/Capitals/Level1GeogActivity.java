package space.mosk.checkbrain.Math;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Random;

import space.mosk.checkbrain.Array;
import space.mosk.checkbrain.AuthActivity;
import space.mosk.checkbrain.MainActivity;
import space.mosk.checkbrain.R;

public class Level1MathActivity extends AppCompatActivity {

    Button btn_back;
    Dialog dialog;

    public int numLeft, numRight;
    Array array = new Array();
    Random random = new Random();
    public int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            startActivity(new Intent(Level1MathActivity.this, AuthActivity.class));
            finish();
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        TextView text_task = findViewById(R.id.task);
        text_task.setText(R.string.levelonemath);

        final ImageView img_left = findViewById(R.id.img_left);
        final ImageView img_right = findViewById(R.id.img_right);

        final TextView text_left = findViewById(R.id.text_left);
        final TextView text_right = findViewById(R.id.text_right);

        // back_btn
        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Level1MathActivity.this, MathActivity.class));
                overridePendingTransition(0,0);
            }
        });

        // Вызов dialog
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.previewdialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button btn_continue = dialog.findViewById(R.id.btn_continue);
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });



        dialog.show();

        final int[] progress = {R.id.point1,R.id.point2,R.id.point3,R.id.point4,R.id.point5,R.id.point6,R.id.point7,R.id.point8,R.id.point9,R.id.point10,R.id.point11,R.id.point12,R.id.point13,R.id.point14,R.id.point15,R.id.point16,R.id.point17,R.id.point18,R.id.point19,R.id.point20};

        final Animation a =  AnimationUtils.loadAnimation(Level1MathActivity.this, R.anim.alpha);



        numLeft = random.nextInt(10);
        img_left.setImageResource(array.images_math1[numLeft]);
        text_left.setText(array.texts1[numLeft]);
        numRight = numLeft;
        while (numRight == numLeft){
            numRight = random.nextInt(10);
        }
        img_right.setImageResource(array.images_math1[numRight]);
        text_right.setText(array.texts1[numRight]);

        img_left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    img_right.setEnabled(false);
                    if (numLeft > numRight){
                        img_left.setImageResource(R.drawable.img_true);
                    }else {
                        img_left.setImageResource(R.drawable.img_false);
                    }
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                    if (numLeft > numRight){
                        if (counter<20){
                            counter++;
                        }
                        for (int i = 0; i < 20; i++) {
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points);
                        }
                        for (int i = 0; i < counter; i++) {
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points);
                            tv.setBackgroundResource(R.drawable.style_points_true);
                        }
                    } else {
                        if (counter>0){
                            if (counter==1){
                                counter=0;
                            } else {
                                counter-=2;
                            }
                        }
                        for (int i = 0; i < 19; i++) {
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points);
                        }
                        for (int i = 0; i < counter; i++) {
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points);
                            tv.setBackgroundResource(R.drawable.style_points_true);
                        }
                    }
                    if (counter>=20){
                        Toast.makeText(Level1MathActivity.this, "Конец игры", Toast.LENGTH_SHORT).show();
                    } else {
                        numLeft = random.nextInt(10);
                        img_left.setImageResource(array.images_math1[numLeft]);
                        img_left.startAnimation(a);
                        text_left.setText(array.texts1[numLeft]);
                        numRight = numLeft;
                        while (numRight == numLeft){
                            numRight = random.nextInt(10);
                        }
                        img_right.setImageResource(array.images_math1[numRight]);
                        img_right.startAnimation(a);
                        text_right.setText(array.texts1[numRight]);
                        img_right.setEnabled(true);
                    }
                }
                return true;
            }
        });


        img_right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    img_left.setEnabled(false);
                    if (numLeft < numRight){
                        img_right.setImageResource(R.drawable.img_true);
                    }else {
                        img_right.setImageResource(R.drawable.img_false);
                    }
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                    if (numLeft < numRight){
                        if (counter<20){
                            counter++;
                        }
                        for (int i = 0; i < 20; i++) {
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points);
                        }
                        for (int i = 0; i < counter; i++) {
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points);
                            tv.setBackgroundResource(R.drawable.style_points_true);
                        }
                    } else {
                        if (counter>0){
                            if (counter==1){
                                counter=0;
                            } else {
                                counter-=2;
                            }
                        }
                        for (int i = 0; i < 19; i++) {
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points);
                        }
                        for (int i = 0; i < counter; i++) {
                            TextView tv = findViewById(progress[i]);
                            tv.setBackgroundResource(R.drawable.style_points);
                            tv.setBackgroundResource(R.drawable.style_points_true);
                        }
                    }
                    if (counter>=20){
                        Toast.makeText(Level1MathActivity.this, "Конец игры", Toast.LENGTH_SHORT).show();
                    } else {
                        numLeft = random.nextInt(10);
                        img_left.setImageResource(array.images_math1[numLeft]);
                        img_left.startAnimation(a);
                        text_left.setText(array.texts1[numLeft]);
                        numRight = numLeft;
                        while (numRight == numLeft){
                            numRight = random.nextInt(10);
                        }
                        img_right.setImageResource(array.images_math1[numRight]);
                        img_right.startAnimation(a);
                        text_right.setText(array.texts1[numRight]);
                        img_left.setEnabled(true);
                    }
                }
                return true;
            }
        });
    }
}