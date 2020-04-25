package com.example.cs125finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent intent = new Intent(MainActivity.this, GameActivity.class);
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Ready to start?");
        builder.setPositiveButton(R.string.play_game_confirmation, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                startActivity(intent);
            }
        });

        Button one = findViewById(R.id.disney);
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                intent.putExtra("game", "one");
                builder.create().show();
            }
        });

        Button two = findViewById(R.id.activities);
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                intent.putExtra("game", "two");
                builder.create().show();
            }
        });

        Button three = findViewById(R.id.daily_life);
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                intent.putExtra("game", "three");
                builder.create().show();
            }
        });

        Button four = findViewById(R.id.uiuc);
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                intent.putExtra("game", "four");
                builder.create().show();
            }
        });
    }

}
