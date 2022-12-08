package com.example.raisethestake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import model.Match;
import model.Player;

public class PlayingMatch2 extends AppCompatActivity implements View.OnClickListener {

    Button btnMatchStarted;

    Match currentMatch;
    Player currentPlayer;

    FirebaseDatabase root = FirebaseDatabase.getInstance();
    DatabaseReference matches = root.getReference("Matches");
    DatabaseReference players = root.getReference("Players");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing_match2);

        initialize();
    }

    private void initialize()
    {
        currentPlayer = (Player) getIntent().getExtras().getSerializable("currentPlayer");

        btnMatchStarted = findViewById(R.id.buttonMatchStart);
        btnMatchStarted.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch(id)
        {
            case R.id.buttonMatchStart:
                matchStarted(view);
                break;
        }
    }

    private void matchStarted(View view)
    {
        Intent intent = new Intent(this, SubmitResults.class);
        intent.putExtra("currentPlayer", currentPlayer);
        startActivity(intent);
        PlayingMatch2.this.finish();
    }
}