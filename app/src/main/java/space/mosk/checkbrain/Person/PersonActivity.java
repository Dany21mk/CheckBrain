package space.mosk.checkbrain.Person;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import space.mosk.checkbrain.AccountActivity;
import space.mosk.checkbrain.AuthActivity;
import space.mosk.checkbrain.DashBoard;
import space.mosk.checkbrain.MainActivity;
import space.mosk.checkbrain.R;
import space.mosk.checkbrain.TestsActivity;

public class PersonActivity extends AppCompatActivity {

    Button btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        // проверка авторизации пользователя
        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            startActivity(new Intent(PersonActivity.this, AuthActivity.class));
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
                startActivity(new Intent(PersonActivity.this, MainActivity.class));
                overridePendingTransition(0,0);
            }
        });

        // Переход по уровням
        TextView textView1 = findViewById(R.id.textMathView1);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PersonActivity.this, Level1PersonActivity.class));
            }
        });

        TextView textView2 = findViewById(R.id.textMathView2);
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PersonActivity.this, Level2PersonActivity.class));
            }
        });
    }

}