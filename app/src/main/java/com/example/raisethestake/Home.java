package com.example.raisethestake;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;

import model.Player;
import model.Tournament;
import model.TournamentDisplayAdapter;

public class Home extends AppCompatActivity implements ChildEventListener, View.OnClickListener {

    ListView lvTournaments;
    Button btnFindMatch;
    TextView tvUsername, tvBalance;

    FirebaseDatabase root = FirebaseDatabase.getInstance();
    DatabaseReference tournaments = root.getReference("Tournaments");
    ArrayList<Tournament> listOfTournaments;
    TournamentDisplayAdapter adapter;

    Player currentPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initialize();
    }

    private void initialize()
    {
        currentPlayer = (Player) getIntent().getExtras().getSerializable("currentPlayer");

        tvBalance = findViewById(R.id.tvBalance);
        tvUsername = findViewById(R.id.tvUsername);
        tvBalance.setText(String.valueOf(currentPlayer.getBalance()));
        tvUsername.setText(currentPlayer.getUsername());

        lvTournaments = findViewById(R.id.lvTournaments);
        //Refactoring needed
        listOfTournaments = new ArrayList<Tournament>();
        tournaments.child("NBA 2K23").addChildEventListener(this);
        adapter = new TournamentDisplayAdapter(this, listOfTournaments);
        lvTournaments.setAdapter(adapter);

        btnFindMatch = findViewById(R.id.btnFindMatch);
        btnFindMatch.setOnClickListener(this);


    }

    @Override
    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        Tournament tournament = new Tournament();
        tournament.setDevice(snapshot.child("device").getValue().toString());
        tournament.setGame(snapshot.child("game").getValue().toString());
        tournament.setGameMode(snapshot.child("gameMode").getValue().toString());
        tournament.setPrize(Float.valueOf(snapshot.child("prize").getValue().toString()));
        tournament.setListOfPlayers((ArrayList<String>) snapshot.child("listOfPlayers").getValue());
        tournament.setListOfMatches((ArrayList<String>) snapshot.child("listOfMatches").getValue());

        listOfTournaments.add(tournament);
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

    @Override
    public void onClick(View view)
    {
        int id = view.getId();
        switch (id)
        {
            case R.id.btnFindMatch:
                Intent i = new Intent(this, FindMatch.class);
                i.putExtra("currentPlayer", currentPlayer);
                startActivity(i);
                break;
        }
    }
}