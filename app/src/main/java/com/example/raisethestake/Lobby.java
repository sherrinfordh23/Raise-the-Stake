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
    Match match;

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


        // Fetching match object in PlayersSearching to display values in text fields
        playerSearching.child(currentPlayer.getMatchOrTournamentId())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists())
                        {
                            match = snapshot.getValue(Match.class);
                            if (match.getUuid().equals(currentPlayer.getMatchOrTournamentId()))
                            {
                                tvGame.setText(match.getGame());
                                tvGameMode.setText(match.getGameMode());
                                tvDevice.setText(match.getDevice());
                                edAmount.setText(String.valueOf(match.getMoneyDeposited()));
                            }


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        //The side of the person who is waiting at the lobby
        players.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                Player snapshotPlayer = snapshot.getValue(Player.class);
                if (snapshotPlayer.getUsername().equals(currentPlayer.getUsername()))
                {
                    currentPlayer = snapshotPlayer;
                    Intent i = new Intent(context, PlayingMatch1.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("currentPlayer", currentPlayer);
                    finish();
                    startActivity(i);

                }


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

        playerSearching.addListenerForSingleValueEvent(this);



    }


    // Looping through the SearchPlayers table to find the matching criteria
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {

        for (DataSnapshot ds : snapshot.getChildren())
        {
            Match oneMatch = ds.getValue(Match.class);
            if(oneMatch.equals(match))
            {
                // The side of the person who finds the criteria after the criteria was already set
                Match newMatch = new Match();
                newMatch.setGame(oneMatch.getGame());
                newMatch.setGameMode(oneMatch.getGameMode());
                newMatch.setDevice(oneMatch.getDevice());
                newMatch.setMoneyDeposited(oneMatch.getMoneyDeposited());
                newMatch.setPlayer1(oneMatch.getPlayer1());
                newMatch.setPlayer2(match.getPlayer1());

                matches.child(newMatch.getUuid()).setValue(newMatch);
                playerSearching.child(oneMatch.getUuid()).removeValue();
                playerSearching.child(match.getUuid()).removeValue();


                players.child(newMatch.getPlayer1()).
                        addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists())
                                {
                                    Player player = snapshot.getValue(Player.class);
                                    player.setMatchOrTournamentId(newMatch.getUuid());
                                    players.child(player.getUsername()).setValue(player);

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                players.child(newMatch.getPlayer2()).
                        addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists())
                                {
                                    Player player = snapshot.getValue(Player.class);
                                    player.setMatchOrTournamentId(newMatch.getUuid());
                                    players.child(player.getUsername()).setValue(player);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                Intent i = new Intent(context, PlayingMatch1.class);
                i.putExtra("currentPlayer", currentPlayer);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                finish();
                startActivity(i);

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

                break;
        }
    }
}