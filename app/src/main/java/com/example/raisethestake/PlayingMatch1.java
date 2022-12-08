package com.example.raisethestake;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import model.Match;
import model.Player;

public class PlayingMatch1 extends AppCompatActivity implements View.OnClickListener {

    Button btnReady, btnCancel;

    Match currentMatch;
    Player currentPlayer;

    TextView tvName;

    FirebaseDatabase root = FirebaseDatabase.getInstance();
    DatabaseReference matches = root.getReference("Matches");
    DatabaseReference players = root.getReference("Players");
    DatabaseReference playersSearching = root.getReference("PlayersSearching");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing_match1);

        initialize();
    }

    private void initialize()
    {
        currentPlayer = (Player) getIntent().getExtras().getSerializable("currentPlayer");

        btnReady = findViewById(R.id.buttonReady);
        btnReady.setOnClickListener(this);
        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(this);

        tvName = findViewById(R.id.tvName);
        tvName.setText(currentPlayer.getUsername());


        matches.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren())
                {
                    if (ds.getValue(Match.class).getUuid().equals(currentPlayer.getMatchOrTournamentId()))
                    {
                        currentMatch = ds.getValue(Match.class);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onClick(View view)
    {
        int id = view.getId();
        switch (id) {
            case R.id.buttonReady:
                btnReady.setEnabled(false);
                btnReady.setBackgroundColor(Color.rgb(112, 138, 119));
                readyUp(view);
                break;
            case R.id.btnCancel:
                cancel(view);
                btnReady.setEnabled(true);
                btnReady.setBackgroundColor(Color.rgb(193, 230, 198));
                break;
        }
    }

    private void cancel(View view)
    {
        if (!btnReady.isEnabled())
        {
            // Players not ready
            if(currentMatch.getPlayer1().equals(currentPlayer.getUsername()))
            {
                currentMatch.setPlayer1Ready(false);
            }
            else
                currentMatch.setPlayer2Ready(false);

            matches.child(currentMatch.getUuid()).setValue(currentMatch);
        }
        else {
            // Cancel the match
            // Delete match from matches
            currentPlayer.setMatchOrTournamentId(null);
            players.child(currentPlayer.getUsername()).setValue(currentPlayer);

            Match newMatch = new Match();

            if (currentMatch.getPlayer1().equals(currentPlayer.getUsername()))
            {
                currentMatch.setPlayer1(null);
                newMatch = new Match(currentMatch.getUuid(), currentMatch.getGame(), currentMatch.getGameMode(),
                        currentMatch.getDevice(), currentMatch.getMoneyDeposited(), currentMatch.getPlayer2());

            }
            else if (currentMatch.getPlayer2().equals(currentPlayer.getUsername()))
            {
                currentMatch.setPlayer2(null);
                newMatch = new Match(currentMatch.getUuid(), currentMatch.getGame(), currentMatch.getGameMode(),
                        currentMatch.getDevice(), currentMatch.getMoneyDeposited(), currentMatch.getPlayer1());

            }

            currentPlayer.setMatchOrTournamentId(null);

            playersSearching.child(newMatch.getUuid()).setValue(newMatch);
            players.child(currentPlayer.getUsername()).setValue(currentPlayer);
            matches.child(currentMatch.getUuid()).removeValue();

            Intent intent = new Intent(this, Home.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("currentPlayer", currentPlayer);
            startActivity(intent);
            PlayingMatch1.this.finish();

        }
    }

    private void readyUp(View view)
    {
        if(currentMatch.getPlayer1().equals(currentPlayer.getUsername()))
        {
            currentMatch.setPlayer1Ready(true);
        }
        else if(currentMatch.getPlayer2().equals(currentPlayer.getUsername()))
            currentMatch.setPlayer2Ready(true);



        matches.child(currentMatch.getUuid()).setValue(currentMatch);


    }
}