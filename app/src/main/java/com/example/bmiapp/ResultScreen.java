package com.example.bmiapp;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result_screen);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Viewの取得
        TextView Result = findViewById(R.id.resultBmi);
        TextView Comment = findViewById(R.id.resultComment);
        View circleView = findViewById(R.id.circleView);
        View mainView = findViewById(R.id.main);
        AppCompatButton backBtn = findViewById(R.id.backBtn);

        // Intentからデータを取得
        String bmiResult = getIntent().getStringExtra("result");
        String bmiComment = getIntent().getStringExtra("comment");

        // 取得データのセット
        Result.setText(bmiResult);
        Comment.setText(bmiComment);

        
        ValueAnimator colorAnimator = ValueAnimator.ofObject(
                new ArgbEvaluator(),
                Color.parseColor("#8DA4CE"),
                Color.parseColor("#A0B4DB"),
                Color.parseColor("#6B82A5"),
                Color.parseColor("#8DA4CE")
        );
        colorAnimator.setDuration(5000);
        colorAnimator.setRepeatCount(ValueAnimator.INFINITE);
        colorAnimator.setRepeatMode(ValueAnimator.REVERSE);

        
        colorAnimator.addUpdateListener(animation -> {
            int animatedValue = (int) animation.getAnimatedValue();
            mainView.setBackgroundColor(animatedValue);
        });

        
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(circleView, "scaleX", 1f, 1.5f, 0.8f, 1.2f, 1f);
        scaleX.setDuration(2000);
        scaleX.setInterpolator(new OvershootInterpolator());

        ObjectAnimator scaleY = ObjectAnimator.ofFloat(circleView, "scaleY", 1f, 0.8f, 1.5f, 1.2f, 1f);
        scaleY.setDuration(2000);
        scaleY.setInterpolator(new OvershootInterpolator());

        ObjectAnimator rotation = ObjectAnimator.ofFloat(circleView, "rotation", 0f, 15f, -15f, 10f, -10f, 0f);
        rotation.setDuration(2000);
        rotation.setInterpolator(new AccelerateDecelerateInterpolator());

        
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleX, scaleY, rotation);

        
        animatorSet.addListener(new android.animation.AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(android.animation.Animator animation) {
                animatorSet.start();
            }
        });

        
        animatorSet.start();
        colorAnimator.start();

        // 戻るボタン
        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(ResultScreen.this, InputScreen.class);
            startActivity(intent);
        });
    }
}
