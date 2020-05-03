package com.example.cs125finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private int score;
    private int lastIndex;
    ArrayList<Integer> usedIndices = new ArrayList<>();
    private String[] currCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        currCategory = getIntent().getStringArrayExtra("game");
        lastIndex = -1;

        score = 0;
        final TextView yourScore = findViewById(R.id.score);
        yourScore.setText("Score: " + score);

        newWord();
        runTimer();

        ImageButton pass = findViewById(R.id.pass);
        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                newWord();
            }
        });
        ImageButton correct = findViewById(R.id.correct);
        correct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                MediaPlayer correctRing = MediaPlayer.create(GameActivity.this, R.raw.correct);
                correctRing.start();
                score++;
                newWord();
                yourScore.setText("Score: " + score);
            }
        });
    }

    public void newWord() {
        TextView toAct = findViewById(R.id.word);
        Random rand = new Random();
        Integer r = rand.nextInt(30);
        if (usedIndices.size() < 30) {
            while(usedIndices.contains(r)) {
                r = rand.nextInt(30);
            }
            usedIndices.add(r);
        } else {
            while(lastIndex == r) {
                r = rand.nextInt(30);
            }
        }
        toAct.setText(currCategory[r]);
        lastIndex = r;
    }

    public void runTimer() {
        final TextView timer = findViewById(R.id.timer);
        final CountDownTimer countDownTimer = new CountDownTimer(60000, 1000) {

            public void onTick(long millisec) {
                long totalSeconds = millisec / 1000;
                long minutes = totalSeconds / 60;
                long seconds = totalSeconds - minutes * 60;
                String timeLeft = "" + minutes + ":";
                if (seconds < 10) {
                    timeLeft += "0" + seconds;
                } else {
                    timeLeft += seconds;
                }
                timer.setText(timeLeft);
                if (totalSeconds <= 10) {
                    timer.setTextColor(Color.RED);
                }
            }

            public void onFinish() {
                endGame();
            }
        };
        countDownTimer.start();
    }

    public void endGame() {
        final MediaPlayer timerRing = MediaPlayer.create(this, R.raw.timer);
        timerRing.start();
        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
        builder.setMessage("Time's up! Your score is " + score + "!");
        builder.setCancelable(false);
        builder.setNegativeButton(R.string.new_deck, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                timerRing.stop();
                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        builder.setPositiveButton(R.string.play_again, new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id) {
                timerRing.stop();
                Intent intent = new Intent(GameActivity.this, GameActivity.class);
                intent.putExtra("game", currCategory);
                startActivity(intent);
            }
        });
        builder.create().show();
    }
}
