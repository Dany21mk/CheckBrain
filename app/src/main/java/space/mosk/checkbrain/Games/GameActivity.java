package space.mosk.checkbrain.Games;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import space.mosk.checkbrain.AccountActivity;
import space.mosk.checkbrain.AuthActivity;
import space.mosk.checkbrain.DashBoard;
import space.mosk.checkbrain.MainActivity;
import space.mosk.checkbrain.R;
import space.mosk.checkbrain.GameAdapter;
import space.mosk.checkbrain.TestsActivity;

public class GameActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    String[] strTheme = {"Кликер cookie", "Захват башни" , "Врагопад"};
    int[] imagesTheme = {R.drawable.game1, R.drawable.game2, R.drawable.game3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Игры");


        // проверка авторизации пользователя
        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            startActivity(new Intent(GameActivity.this, AuthActivity.class));
            finish();
        }

        // Делаем скролл
        recyclerView = findViewById(R.id.recycler_theme);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new GameAdapter(this, imagesTheme, strTheme);
        recyclerView.setAdapter(adapter);


        // Настройка меню

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.findViewById(R.id.dashboard).setSelected(false);
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
    }
}