package com.example.tugasanimasi;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class MainActivity extends AppCompatActivity {
    private ImageView ballImg;
    private Button ballBtn;
    private float ballX, ballY; // Ball position
    private float ballSpeedX, ballSpeedY; // Ball speed
    private int screenWidth, screenHeight; // Screen dimensions
    private int ballWidth, ballHeight; // Ball dimensions
    private int frameRate = 30; // Animation frame rate in milliseconds
    private boolean animationRunning = false; // Animation state

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ballBtn = findViewById(R.id.bounceBallButton);
        ballImg = findViewById(R.id.bounceBallImage);
        ballX = ballImg.getX();
        ballY = ballImg.getY();
        ballSpeedX = 20; // Initial speed in the X direction
        ballSpeedY = 20; // Initial speed in the Y direction

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        ballBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (animationRunning) {
                    stopAnimation();
                } else {
                    startAnimation();
                }
            }
        });
    }
    private void updateBallPosition() {
        // Update ball position
        ballX += ballSpeedX;
        ballY += ballSpeedY;

        // Check for collision with screen edges
        if (ballX <= 0 || ballX + ballWidth >= screenWidth) {
            ballSpeedX = -ballSpeedX; // Reverse ball direction in X
        }
        if (ballY <= 0 || ballY + ballHeight >= screenHeight) {
            ballSpeedY = -ballSpeedY; // Reverse ball direction in Y
        }

        // Update ImageView position
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ballImg.setX(ballX);
                ballImg.setY(ballY);
            }
        });
    }
    private void startAnimation() {
        if (!animationRunning) {
            animationRunning = true;
            ballBtn.setText("Stop Animation");
            final Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    updateBallPosition();
                    if (animationRunning) {
                        handler.postDelayed(this, frameRate);
                    }
                }
            };
            handler.postDelayed(runnable, frameRate);
        }

    }
    private void stopAnimation() {
        animationRunning = false;
        ballBtn.setText("Start Animation");
    }
}