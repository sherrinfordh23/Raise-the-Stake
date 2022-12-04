package com.example.raisethestake;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CpuUsageInfo;
import android.provider.ContactsContract;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;

import model.Match;
import model.MatchDisplayAdapter;
import model.Tournament;

public class JoinedTournament extends AppCompatActivity {

    TextView tvGameMode, tvGame, tvDevice, tvPrize, tvPlayersJoined;
    ListView lvMatches;
    Tournament currentTournament;

    DatabaseReference root = FirebaseDatabase.getInstance().getReference();
    DatabaseReference tournaments = root.child("Tournaments");
    DatabaseReference matches = root.child("Matches");
    MatchDisplayAdapter adapter;
    ArrayList<String> listOfMatches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joined_tournament);

        initialize();
    }

    private void initialize() {

        currentTournament = (Tournament) getIntent().getExtras().getSerializable("currentTournament");
        tvGameMode = findViewById(R.id.tvGameMode);
        tvGame = findViewById(R.id.tvGame);
        tvDevice = findViewById(R.id.tvDevice);
        tvPrize = findViewById(R.id.tvPrize);
        tvPlayersJoined = findViewById(R.id.tvPlayersJoined);

        tvGameMode.setText(currentTournament.getGameMode());
        tvGame.setText(currentTournament.getGame());
        tvDevice.setText(currentTournament.getDevice());
        tvPrize.setText("$" + currentTournament.getPrize());
        tvPlayersJoined.setText(currentTournament.getListOfPlayers().size()
                                + "/" + currentTournament.getNumOfPlayers() + " players joined");
        lvMatches = findViewById(R.id.lvMatches);

        listOfMatches = new ArrayList<String>();
        for (int i = 0; i < currentTournament.getListOfMatches().size(); i++)
        {
            listOfMatches.add(currentTournament.getListOfMatches().get(i));
        }
        adapter = new MatchDisplayAdapter(listOfMatches, this);
        lvMatches.setAdapter(adapter);


    }

}