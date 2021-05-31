package space.mosk.checkbrain.Math;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Random;

import space.mosk.checkbrain.AuthActivity;
import space.mosk.checkbrain.ChooseTrue.ChooseTrueActivity;
import space.mosk.checkbrain.MainActivity;
import space.mosk.checkbrain.Math.Level9MathActivity;
import space.mosk.checkbrain.R;
import space.mosk.checkbrain.StartActivity;

public class Level10MathActivity extends AppCompatActivity {

    Button btn_back;
    TextView taskText;
    Dialog dialog;

    public int num1, num2, ans;
    public String userAns = "";
    public int counter = 0;
    public final int[] progress = {R.id.point1,R.id.point2,R.id.point3,R.id.point4,R.id.point5,R.id.point6,R.id.point7,R.id.point8,R.id.point9,R.id.point10,R.id.point11,R.id.point12,R.id.point13,R.id.point14,R.id.point15,R.id.point16,R.id.point17,R.id.point18,R.id.point19,R.id.point20};

    public int rand = 0;
    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_math);
        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            startActivity(new Intent(Level10MathActivity.this, AuthActivity.class));
            finish();
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Level10MathActivity.this, MathActivity.class));
                overridePendingTransition(0,0);
            }
        });

        // Вызов dialog
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.previewdialog);
        TextView tx = dialog.findViewById(R.id.textTask);
        tx.setText(R.string.leveltenmath);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button btn_continue = dialog.findViewById(R.id.btn_continue);
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });



        dialog.show();

        update();
    }


    public void inputDigit(View view) {
        Button btn = (Button)view;
        ImageView img = findViewById(R.id.img_status);
        String btnText = btn.getText().toString();
        if (btnText.equals(".")) {
            if (userAns.equals("")){
                return;
            }
        }
        taskText.setText(String.valueOf(taskText.getText() + btnText));
        userAns = userAns + "" + btnText;
        if (userAns.length() >= String.valueOf(ans).length()){
            if (userAns.equals(String.valueOf(ans))){
                img.setImageResource(R.drawable.yes);
                taskText.setTextColor(Color.rgb(20,153,0));
                if (counter<20){
                    counter++;
                }
                handlerProgress(true);
            } else {
                img.setImageResource(R.drawable.no);
                taskText.setTextColor(Color.rgb(255,0,0));
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
                gameover();
                return;
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    taskText.setTextColor(Color.rgb(0,0,0));
                    img.setImageResource(R.drawable.wait);
                    update();
                }
            }, 1*500);
        }
    }

    public void remove(View view) {
        String str = (String) taskText.getText();
        char lastChar = str.charAt(str.length()-1);
        if (lastChar == ' '){
            return;
        }
        taskText.setText(str.substring(0, str.length() - 1));
        userAns = userAns.substring(0, userAns.length() - 1);
    }
    public void clear(View view) {
        String str = (String) taskText.getText();
        char lastChar = str.charAt(str.length()-1);
        while (lastChar != ' '){
            taskText.setText(str.substring(0, str.length() - 1));
            str = (String) taskText.getText();
            lastChar = str.charAt(str.length()-1);
        }
        userAns = "";
    }
    public void help(View view) {
        Toast.makeText(this, ans+"", Toast.LENGTH_SHORT).show();
    }

    public static int getRandom(int min, int max){
        int x = (int) ((Math.random()*((max-min)+1))+min);
        return x;
    }
    public void update() {
        taskText = (TextView)findViewById(R.id.task);
        rand = random.nextInt(2);
        if (rand == 0){
            num1 = getRandom(-50, 50);
            num2 = getRandom(-50, 50);
            ans = num1 + num2;
            if (num1 < 0){
                taskText.setText(num1 + " + (" + num2 + ") = ");
            }else {
                taskText.setText(num1 + " + " + num2 + " = ");
            }
        } else {
            num1 = getRandom(-50, 50);
            num2 = getRandom(-50, 50);
            ans = num2 - num1;
            if (num1 < 0){
                taskText.setText(num2 + " - (" + num1 + ") = ");
            }else {
                taskText.setText(num2 + " - " + num1 + " = ");
            }
        }
        userAns = "";
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
    public void gameover(){
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
                startActivity(new Intent(Level10MathActivity.this, ChooseTrueActivity.class));
                finish();
            }
        });

        dialog.show();
    }
}