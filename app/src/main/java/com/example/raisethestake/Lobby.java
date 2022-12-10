package com.example.raisethestake;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

import model.Match;
import model.Player;

public class Lobby extends AppCompatActivity implements ValueEventListener, View.OnClickListener {

    TextView tvGame, tvGameMode, tvDevice;
    EditText edAmount;
    Button btnCancel;

    DatabaseReference root = FirebaseDatabase.getInstance().getReference();
    DatabaseReference matches = root.child("Matches");
    DatabaseReference playerSearching = root.child("PlayersSearching");
    DatabaseReference players = root.child("Players");

    Player currentPlayer;
    Match currentMatch;

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        initialize();
    }

    private void initialize() {

        tvGame = findViewById(R.id.tvGame);
        tvGameMode = findViewById(R.id.tvGameMode);
        tvDevice = findViewById(R.id.tvDevice);
        edAmount = findViewById(R.id.edAmount);
        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(this);

        currentPlayer = (Player) getIntent().getExtras().getSerializable("currentPlayer");
        currentMatch = (Match) getIntent().getExtras().getSerializable("currentMatch");

        playerSearching.addListenerForSingleValueEvent(this);
        matches.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Match match = snapshot.getValue(Match.class);
                currentPlayer.setMatchOrTournamentId(match.getUuid());
                players.child(currentPlayer.getUsername()).setValue(currentPlayer);
                Intent intent = new Intent(Lobby.this, PlayingMatch1.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("currentPlayer", currentPlayer);
                intent.putExtra("currentMatch", match);
                finish();
                startActivity(intent
                );
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    // Looping through the SearchPlayers table to find the matching criteria
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {

        for (DataSnapshot ds : snapshot.getChildren())
        {
            Match oneMatch = ds.getValue(Match.class);
            if(oneMatch.equals(currentMatch))
            {
                // The side of the person who finds the criteria after the criteria was already set
                Match newMatch = new Match();
                newMatch.setGame(oneMatch.getGame());
                newMatch.setGameMode(oneMatch.getGameMode());
                newMatch.setDevice(oneMatch.getDevice());
                newMatch.setMoneyDeposited(oneMatch.getMoneyDeposited());
                newMatch.setPlayer1(oneMatch.getPlayer1());
                newMatch.setPlayer2(currentMatch.getPlayer1());

                matches.child(newMatch.getUuid()).setValue(newMatch);
                playerSearching.child(oneMatch.getUuid()).removeValue();
                playerSearching.child(currentMatch.getUuid()).removeValue();


            }

        }

    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id)
        {
            case R.id.btnCancel:
                cancel();
                break;
        }
    }

    private void cancel() {

        finish();
    }
}