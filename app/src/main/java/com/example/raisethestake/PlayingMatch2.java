package com.example.raisethestake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import model.Match;
import model.Player;

public class PlayingMatch2 extends AppCompatActivity implements View.OnClickListener {

    Button btnMatchStarted;

    Match currentMatch;
    Player currentPlayer;
    ImageButton btnHome, btnPlayerSearch, btnDashboard;
    TextView tvUsername, tvBalance, tvPlayer1, tvPlayer2, tvMatchOpponent;
    ImageView imgProfilePicture;

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
        currentMatch = (Match) getIntent().getExtras().getSerializable("currentMatch");

        tvMatchOpponent = findViewById(R.id.tvMatchOpponent);
        tvMatchOpponent.setText("Match vs. ");
        if (currentMatch.getPlayer1().equals(currentPlayer.getUsername()))
            tvMatchOpponent.append(currentMatch.getPlayer2());
        else
            tvMatchOpponent.append(currentMatch.getPlayer1());

        btnMatchStarted = findViewById(R.id.buttonMatchStart);
        btnMatchStarted.setOnClickListener(this);
        btnHome = findViewById(R.id.btnHome);
        btnPlayerSearch = findViewById(R.id.btnPlayerSearch);
        btnDashboard = findViewById(R.id.btnDashboard);
        btnHome.setOnClickListener(this);
        btnPlayerSearch.setOnClickListener(this);
        btnDashboard.setOnClickListener(this);
        tvUsername = findViewById(R.id.tvUsername);
        tvBalance = findViewById(R.id.tvBalance);
        tvUsername.setText(currentPlayer.getUsername());
        tvBalance.setText(String.valueOf(currentPlayer.getBalance()));
        imgProfilePicture = findViewById(R.id.imgProfilePicture);
        if (currentPlayer.getProfilePicture() != null)
        {
            Picasso.with(this).load(currentPlayer.getProfilePicture()).into(imgProfilePicture);
        }
        tvPlayer1 = findViewById(R.id.tvPlayer1);
        tvPlayer2 = findViewById(R.id.tvPlayer2);
        tvPlayer1.setText(currentMatch.getPlayer1());
        tvPlayer2.setText(currentMatch.getPlayer2());
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        Intent intent;
        switch(id)
        {
            case R.id.buttonMatchStart:
                matchStarted(view);
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

    private void matchStarted(View view)
    {
        Intent intent = new Intent(this, SubmitResults.class);
        intent.putExtra("currentPlayer", currentPlayer);
        intent.putExtra("currentMatch", currentMatch);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
        startActivity(intent);
    }
}