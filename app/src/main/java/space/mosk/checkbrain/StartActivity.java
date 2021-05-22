package space.mosk.checkbrain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class StartActivity extends AppCompatActivity {

    Button btn_begin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        if (!(FirebaseAuth.getInstance().getCurrentUser() == null)){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(StartActivity.this, MainActivity.class));
                    finish();
                }
            }, 3*1000);

        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(StartActivity.this, AuthActivity.class));
                    finish();
                }
            }, 3*1000);
        }

        btn_begin = findViewById(R.id.btn_begin);
        btn_begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(FirebaseAuth.getInstance().getCurrentUser() == null)){
                    startActivity(new Intent(StartActivity.this, MainActivity.class));
                    overridePendingTransition(0,0);
                    finish();
                } else {
                    startActivity(new Intent(StartActivity.this, AuthActivity.class));
                    overridePendingTransition(0,0);
                    finish();
                }
            }
        });
    }
}