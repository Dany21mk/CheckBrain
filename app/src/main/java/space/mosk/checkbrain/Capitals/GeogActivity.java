package space.mosk.checkbrain.Capitals;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import space.mosk.checkbrain.AccountActivity;
import space.mosk.checkbrain.AuthActivity;
import space.mosk.checkbrain.DashBoard;
import space.mosk.checkbrain.MainActivity;
import space.mosk.checkbrain.Math.Level10MathActivity;
import space.mosk.checkbrain.Math.Level11MathActivity;
import space.mosk.checkbrain.Math.Level1MathActivity;
import space.mosk.checkbrain.Math.Level2MathActivity;
import space.mosk.checkbrain.Math.Level3MathActivity;
import space.mosk.checkbrain.Math.Level4MathActivity;
import space.mosk.checkbrain.Math.Level5MathActivity;
import space.mosk.checkbrain.Math.Level6MathActivity;
import space.mosk.checkbrain.Math.Level7MathActivity;
import space.mosk.checkbrain.Math.Level8MathActivity;
import space.mosk.checkbrain.Math.Level9MathActivity;
import space.mosk.checkbrain.R;
import space.mosk.checkbrain.TestsActivity;

public class GeogActivity extends AppCompatActivity {

    Button btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geog);

        // проверка авторизации пользователя
        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            startActivity(new Intent(GeogActivity.this, AuthActivity.class));
            finish();
        }

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        // bottom navigation

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.findViewById(R.id.home).setSelected(false);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.dashboard:
                        startActivity(new Intent(getApplicationContext(), DashBoard.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.account:
                        startActivity(new Intent(getApplicationContext(), AccountActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.tests:
                        startActivity(new Intent(getApplicationContext(), TestsActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });


        // back_btn
        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GeogActivity.this, MainActivity.class));
                overridePendingTransition(0,0);
            }
        });

        // Переход по уровням
        TextView textView1 = findViewById(R.id.textMathView1);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GeogActivity.this, Level1GeogActivity.class));
            }
        });

        TextView textView2 = findViewById(R.id.textMathView2);
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GeogActivity.this, Level2GeogActivity.class));
            }
        });

        TextView textView3 = findViewById(R.id.textMathView3);
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GeogActivity.this, Level3GeogActivity.class));
            }
        });

        TextView textView4 = findViewById(R.id.textMathView4);
        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GeogActivity.this, Level4GeogActivity.class));
            }
        });
        TextView textView5 = findViewById(R.id.textMathView5);
        textView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GeogActivity.this, Level5GeogActivity.class));
            }
        });
        TextView textView6 = findViewById(R.id.textMathView6);
        textView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GeogActivity.this, Level6GeogActivity.class));
            }
        });
        TextView textView7 = findViewById(R.id.textMathView7);
        textView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GeogActivity.this, Level7GeogActivity.class));
            }
        });
    }
}