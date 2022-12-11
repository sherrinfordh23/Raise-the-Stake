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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

import model.Match;
import model.Player;
import model.Tournament;
import model.TournamentDisplayAdapter;

public class Home extends AppCompatActivity implements ChildEventListener, View.OnClickListener {

    ListView lvTournaments;
    Button btnFindMatch;
    TextView tvUsername, tvBalance;
    ImageButton btnHome, btnPlayerSearch, btnDashboard;
    ImageView imgProfilePicture;

    FirebaseDatabase root = FirebaseDatabase.getInstance();
    DatabaseReference tournaments = root.getReference("Tournaments");
    DatabaseReference matches = root.getReference("Matches");
    DatabaseReference playersSearching = root.getReference("PlayersSearching");
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
        imgProfilePicture = findViewById(R.id.imgProfilePicture);
        if (currentPlayer.getProfilePicture() != null)
        {
            Picasso.with(this).load(currentPlayer.getProfilePicture()).into(imgProfilePicture);
        }

        lvTournaments = findViewById(R.id.lvTournaments);
        //Refactoring needed
        listOfTournaments = new ArrayList<Tournament>();
        tournaments.child("NBA 2K23").addChildEventListener(this);
        adapter = new TournamentDisplayAdapter(this, listOfTournaments);
        lvTournaments.setAdapter(adapter);

        btnFindMatch = findViewById(R.id.btnFindMatch);
        btnFindMatch.setOnClickListener(this);
        btnHome = findViewById(R.id.btnHome);
        btnPlayerSearch = findViewById(R.id.btnPlayerSearch);
        btnDashboard = findViewById(R.id.btnDashboard);
        btnHome.setOnClickListener(this);
        btnPlayerSearch.setOnClickListener(this);
        btnDashboard.setOnClickListener(this);

        if (currentPlayer.getMatchOrTournamentId() != null)
        {
            matches.child(currentPlayer.getMatchOrTournamentId()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists())
                    {
                        Match match = snapshot.getValue(Match.class);

                        Intent intent = new Intent(Home.this, PlayingMatch1.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("currentPlayer", currentPlayer);
                        intent.putExtra("currentMatch", match);
                        finish();
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            playersSearching.child(currentPlayer.getMatchOrTournamentId()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists())
                    {
                        Match match = snapshot.getValue(Match.class);

                        Intent intent = new Intent(Home.this, Lobby.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("currentPlayer", currentPlayer);
                        intent.putExtra("currentMatch", match);
                        finish();
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }


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
        Intent intent;
        switch (id)
        {
            case R.id.btnFindMatch:
                intent = new Intent(this, FindMatch.class);
                intent.putExtra("currentPlayer", currentPlayer);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                finish();
                startActivity(intent);
                break;
            case R.id.btnHome:
                intent = new Intent(this, Home.class);
                intent.putExtra("currentPlayer", currentPlayer);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                finish();
                startActivity(intent);
                break;
            case R.id.btnPlayerSearch:
                intent = new Intent(this, SearchForPlayer.class);
                intent.putExtra("currentPlayer", currentPlayer);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                finish();
                startActivity(intent);
                break;
            case R.id.btnDashboard:
                intent = new Intent(this, account.class);
                intent.putExtra("currentPlayer", currentPlayer);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                finish();
                startActivity(intent);
                break;
        }
    }
}