package com.example.bmiapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.splashscreen.SplashScreen;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        splashScreen.setKeepOnScreenCondition(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return false;
        });

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // view の取得
        EditText editUserName = findViewById(R.id.editUser);
        EditText editUserPass = findViewById(R.id.editPass);
        AppCompatButton loginBtn = findViewById(R.id.loginBtn);

        // ボタン押下時の処理
        loginBtn.setOnClickListener(v -> {

            String userName = editUserName.getText().toString();
            String userPass = editUserPass.getText().toString();


            // 画面遷移
            if (!userName.isEmpty() && !userPass.isEmpty()) {

                Intent intent = new Intent(MainActivity.this, InputScreen.class);
                startActivity(intent);
            } else {
                editUserName.setError("未入力です");
                editUserPass.setError("未入力です");
            }
        });

    }
}