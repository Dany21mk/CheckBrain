package space.mosk.checkbrain;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import space.mosk.checkbrain.Models.User;

public class AuthActivity extends AppCompatActivity {

    Button btn_auth, btn_reg;
    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;
    RelativeLayout root;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);


        // проверка регистрации пользователя
        if (!(FirebaseAuth.getInstance().getCurrentUser() == null)){
                    startActivity(new Intent(AuthActivity.this, MainActivity.class));
                    finish();
        }


        // Инициализация
        btn_auth = findViewById(R.id.btn_auth);
        btn_reg = findViewById(R.id.btn_reg);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance("https://checkbrain-aaf80-default-rtdb.firebaseio.com/");
        users = db.getReference("Users");

        root = findViewById(R.id.root_element);

        // Форма регистрации
        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRegWindow();
            }
        });

        // Форма авторизации
        btn_auth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAuthWindow();
            }
        });
    }

    private void showAuthWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Авторизация");
        dialog.setMessage("Введите ваши регистрационные данные для входа в ваш личный кабинет.");

        LayoutInflater inflater = LayoutInflater.from(this);
        View auth_window = inflater.inflate(R.layout.auth_window, null);
        dialog.setView(auth_window);

        MaterialEditText email = auth_window.findViewById(R.id.emailField);
        MaterialEditText pass = auth_window.findViewById(R.id.passField);

        dialog.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.setPositiveButton("Продолжить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Проверка данных
                if (TextUtils.isEmpty(email.getText().toString())){
                    Toast.makeText(AuthActivity.this, "Неверный email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pass.getText().toString().length() < 5){
                    Toast.makeText(AuthActivity.this, "Пароль должен быть больше 5 символов", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Авторизация
                auth.signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                startActivity(new Intent(AuthActivity.this, MainActivity.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AuthActivity.this, "Ошибка авторизации", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        dialog.show();
    }

    private void showRegWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Регистрация");
        dialog.setMessage("Введите в поля ваши данные, для создания аккаунта.");

        LayoutInflater inflater = LayoutInflater.from(this);
        View reg_window = inflater.inflate(R.layout.register_window, null);
        dialog.setView(reg_window);

        MaterialEditText email = reg_window.findViewById(R.id.emailField);
        MaterialEditText pass = reg_window.findViewById(R.id.passField);
        MaterialEditText username = reg_window.findViewById(R.id.usernameField);

        dialog.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.setPositiveButton("Продолжить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Проверка данных
                if (TextUtils.isEmpty(email.getText().toString())){
                    Toast.makeText(AuthActivity.this, "Неверный email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(username.getText().toString())){
                    Toast.makeText(AuthActivity.this, "Неверный username", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pass.getText().toString().length() < 5){
                    Toast.makeText(AuthActivity.this, "Пароль должен быть больше 5 символов", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Регистрация
                auth.createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                User user = new User(email.getText().toString(), username.getText().toString(), pass.getText().toString());

                                users.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(AuthActivity.this, "Вы успешно зарегистрировались", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AuthActivity.this, "Пользователь с этой почтой уже зарегестрирован", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        dialog.show();
    }
}