package com.example.customprogressbar;

import androidx.appcompat.app.AppCompatActivity;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.graphics.Path;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView percent;
    ImageView ball;
    long mCurrentPlayTimeAnimation;
    long mCurrentPlayTimeRotating;
    long duration = 5000;
    Button start;
    Button stop;
    ObjectAnimator animation;
    ObjectAnimator rotating;




    @SuppressLint("ObjectAnimatorBinding")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        percent = (TextView) findViewById(R.id.percent);
        ball = (ImageView)findViewById(R.id.ball);
        start = (Button)findViewById(R.id.start);

        ball.setVisibility(View.VISIBLE);
        Path path = new Path();
        path.arcTo(50f, 800f, 1000f, 1000f, -180f, 145f, true);
        animation = ObjectAnimator.ofFloat(ball, View.X, View.Y, path);
        rotating = ObjectAnimator.ofFloat(ball,"rotation",1080);
        animation.setDuration(duration);
        rotating.setDuration(duration);
        mCurrentPlayTimeAnimation = 0;

        final Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                handler.postDelayed(this, 0);
                if (animation.getCurrentPlayTime() > 0) {
                    percent.setText(animation.getCurrentPlayTime() / (duration / 100) + "%");
                    if (percent.getText().equals("100%")) {
                        mCurrentPlayTimeAnimation = 0;
                        mCurrentPlayTimeRotating = 0;
                    }
                } else if (!percent.getText().equals("100%")) {
                    percent.setText(mCurrentPlayTimeAnimation / (duration / 100) + "%");
                }

            }
        };
        handler.postDelayed(r, 0000);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentPlayTimeAnimation > 0) {
                    animation.start();
                    rotating.start();
                    animation.setCurrentPlayTime(mCurrentPlayTimeAnimation);
                    rotating.setCurrentPlayTime(mCurrentPlayTimeRotating);
                } else {
                    animation.start();
                    rotating.start();
                }
            }
        });

        stop = (Button)findViewById(R.id.stop);

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentPlayTimeAnimation = animation.getCurrentPlayTime();
                animation.cancel();
                mCurrentPlayTimeRotating = rotating.getCurrentPlayTime();
                rotating.cancel();
            }
        });
    }
}

