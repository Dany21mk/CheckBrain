package space.mosk.checkbrain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import space.mosk.checkbrain.Math.MathActivity;

public class DashBoard extends AppCompatActivity {

    private long backPressedTime;
    private Toast backToast;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    String[] strTheme = {"Чат", "Перемена" , "О приложении", "Мои задачи","Магазин"};
    int[] imagesTheme = {R.drawable.chat, R.drawable.game, R.drawable.about, R.drawable.tasks, R.drawable.shop};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Сервисы");


        // проверка авторизации пользователя
        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            startActivity(new Intent(DashBoard.this, AuthActivity.class));
            finish();
        }

        // Делаем скролл
        recyclerView = findViewById(R.id.recycler_theme);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ServiceAdapter(this, imagesTheme, strTheme);
        recyclerView.setAdapter(adapter);


        // Настройка меню

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.dashboard);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.dashboard:
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
    }
}