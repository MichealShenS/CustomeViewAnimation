package com.ssq.androidanimation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f,0.2f,1.0f,0.2f,0.5f,0.5f);
                scaleAnimation.setDuration(4000);
                Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.view_anim);
                textView.clearAnimation();
                textView.startAnimation(animation);
            }
        });
    }
}
