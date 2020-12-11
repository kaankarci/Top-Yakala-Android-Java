package com.kk.catchkenny;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    TextView scoreText;
    TextView bestScoreText;
    TextView timeText;
    TextView playAgain;
    ImageView imageView;
    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    ImageView imageView6;
    ImageView imageView7;
    ImageView imageView8;
    ImageView[] imageArray;
    Handler handler;
    Runnable runnable;
    int score, bestScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timeText = findViewById(R.id.timeText);
        bestScoreText = findViewById(R.id.bestScoreText);
        playAgain = findViewById(R.id.playAgain);
        scoreText = findViewById(R.id.scoreText);
        imageView = findViewById(R.id.imageView);
        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);
        imageView5 = findViewById(R.id.imageView5);
        imageView6 = findViewById(R.id.imageView6);
        imageView7 = findViewById(R.id.imageView7);
        imageView8 = findViewById(R.id.imageView8);
        score = 0;
        imageArray = new ImageView[]{imageView, imageView1, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8};

        bestScoreText.setText("");
        sharedPreferences = this.getSharedPreferences("com.kk.catchkenny", Context.MODE_PRIVATE);

        new CountDownTimer(10000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                timeText.setText("Time: " + millisUntilFinished / 1000);

            }

            @Override
            public void onFinish() {
                timeText.setText("Game Over");
                handler.removeCallbacks(runnable);
                for (ImageView image : imageArray) {

                    image.setVisibility(View.INVISIBLE);

                }
                playAgain.setText("Play Again");

                bestScore=sharedPreferences.getInt("bestScore",0);
                bestScoreText.setText("Best Score: " + bestScore);
            }
        }.start();
        hideImages();
    }

    public void score(View view) {
        score++;
        scoreText.setText("Score: " + score);
        bestScore=sharedPreferences.getInt("bestScore",0);
        if (bestScore < score) {
            bestScore = score;
            sharedPreferences.edit().putInt("bestScore", bestScore).apply();
        }
    }

    public void playAgain(View view) {
        playAgain.setText("");


        score = 0;
        Intent intent = getIntent();
        finish();
        startActivity(intent);

    }

    public void hideImages() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                for (ImageView image : imageArray) {

                    image.setVisibility(View.INVISIBLE);

                }
                Random random = new Random();
                int i = random.nextInt(9);
                imageArray[i].setVisibility(View.VISIBLE);
                handler.postDelayed(this, 500);

            }
        };
        handler.post(runnable);

    }
}