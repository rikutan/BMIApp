package com.example.bmiapp;


import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class InputScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_input_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 入力エリアの取得
        EditText height = findViewById(R.id.editHeight);
        EditText weight = findViewById(R.id.editWeight);

        // 計算・クリアの取得
        AppCompatButton calcBtn = findViewById(R.id.calcBtn);
        AppCompatButton clearBtn = findViewById(R.id.clearBtn);

        // 画面戻る
        TextView backTxt = findViewById(R.id.inputHeader);


        // 計算ボタン押下時のイベント
        calcBtn.setOnClickListener(v -> {

            String strHeight = height.getText().toString();
            String strWeight = weight.getText().toString();

            // 入力チェック
            if (!strHeight.isEmpty() && !strWeight.isEmpty()) {

                double dbHeight = Double.parseDouble(strHeight);
                double dbWeight = Double.parseDouble(strWeight);

                // cmからmに変換
                double dbHeightM = dbHeight / 100.0;

                // BMI計算
                double dbBmi = dbWeight / (dbHeightM * dbHeightM);

                // BMIの数値をフォーマット
                String strBmi = String.format("%.2f", dbBmi);

                // BMI の判定
                String strJudge;
                if (dbBmi < 18.5) {
                    strJudge = "低体重(瘦せ型)";
                } else if (dbBmi < 25) {
                    strJudge = "普通体重";
                } else if (dbBmi < 30) {
                    strJudge = "肥満(1度)";
                } else if (dbBmi < 35) {
                    strJudge = "肥満(2度)";
                } else if (dbBmi < 40) {
                    strJudge = "肥満(3度)";
                } else {
                    strJudge = "肥満(4度)";
                }


                Intent intent = new Intent(InputScreen.this, ResultScreen.class);
                intent.putExtra("result", strBmi);
                intent.putExtra("comment", strJudge);
                startActivity(intent);

            } else {
                height.setError("未入力です");
                weight.setError("未入力です");
            }
        });

        // 入力(戻る機能)
        backTxt.setOnClickListener(v -> {
            Intent backIntent = new Intent(InputScreen.this,MainActivity.class);
            startActivity(backIntent);
        });

        // クリアボタン押下時のイベント
        clearBtn.setOnClickListener(v -> {
            height.setText("");
            weight.setText("");
        });

    }

}