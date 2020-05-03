package com.example.cs125finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MainActivity extends AppCompatActivity {

    private String[] disneyWords = new String[30];
    private String[] animalWords = new String[30];
    private String[] sportsWords = new String[30];
    private final String[] uiucWords = {"Squirrels", "Cornfields", "MTD", "PAR Late Night",
            "Freshman 15", "State Farm Center", "Big 10", "Quad Day", "Illini Union", "Siebel Center",
            "Taco Bell", "Alma Mater", "Grainger Library", "Hail to the Orange", "Geoff", "Unofficial",
            "Cravings", "Sig Grill", "KAMS", "Green Street", "Morrow Plots", "Barn Dance", "Frat boys",
            "Illini Pantry", "Japan House", "Coronavirus", "Research Park", "Walgreens",
            "Ikenberry Commons", "Career Fair"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String disneyUrl = "https://api.datamuse.com/words?topics=disney+character&max=30";
        final String animalsUrl = "https://api.datamuse.com/words?topics=zoo+animal&max=30";
        final String sportsUrl = "https://api.datamuse.com/words?topics=athletics&max=30";

        createDeck(disneyWords, disneyUrl);
        createDeck(animalWords, animalsUrl);
        createDeck(sportsWords, sportsUrl);

        final Intent intent = new Intent(MainActivity.this, GameActivity.class);
        final AlertDialog.Builder startGame = new AlertDialog.Builder(this);
        startGame.setMessage("Describe the words for your team to guess!" +
                " Tap right for correct answers and tap left to pass. You have 60 seconds!");
        startGame.setPositiveButton(R.string.play_game_confirmation, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                startActivity(intent);
            }
        });

        Button disney = findViewById(R.id.disney);
        disney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                intent.putExtra("game", disneyWords);
                startGame.create().show();
            }
        });

        Button activities = findViewById(R.id.animals);
        activities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                intent.putExtra("game", animalWords);
                startGame.create().show();
            }
        });

        Button dailyLife = findViewById(R.id.sports);
        dailyLife.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                intent.putExtra("game", sportsWords);
                startGame.create().show();
            }
        });

        Button uiuc = findViewById(R.id.uiuc);
        uiuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                intent.putExtra("game", uiucWords);
                startGame.create().show();
            }
        });
    }

    public void createDeck(final String[] deck, String url) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JsonArray words = (new JsonParser()).parse(response).getAsJsonArray();
                int i = 0;
                for (JsonElement word : words) {
                    String str = ((JsonObject) word).get("word").getAsString();
                    str = str.substring(0, 1).toUpperCase() + str.substring(1);
                    deck[i] = str;
                    i++;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder errorMessage = new AlertDialog.Builder(MainActivity.this);
                errorMessage.setMessage("Please connect to internet to play game.");
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

}
