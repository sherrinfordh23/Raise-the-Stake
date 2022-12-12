package com.example.raisethestake;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import java.util.Iterator;

import model.Match;
import model.Player;

public class SubmitResults extends AppCompatActivity implements View.OnClickListener {

    Button btnSubmitResult;
    ImageButton btnHome, btnPlayerSearch, btnDashboard;
    TextView tvUsername, tvBalance, tvPlayer1, tvPlayer2, tvMatchOpponent;
    ImageView imgProfilePicture;

    Match currentMatch;
    Player currentPlayer;

    FirebaseDatabase root = FirebaseDatabase.getInstance();
    DatabaseReference matches = root.getReference("Matches");
    DatabaseReference players = root.getReference("Players");
    DatabaseReference matchResults = root.getReference("MatchResults");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_results);

        initialize();
    }

    private void initialize()
    {
        currentPlayer = (Player) getIntent().getExtras().getSerializable("currentPlayer");
        currentMatch = (Match) getIntent().getExtras().getSerializable("currentMatch");
;
        tvMatchOpponent = findViewById(R.id.tvMatchOpponent);
        tvMatchOpponent.setText("Match vs. ");
        if (currentMatch.getPlayer1().equals(currentPlayer.getUsername()))
            tvMatchOpponent.append(currentMatch.getPlayer2());
        else
            tvMatchOpponent.append(currentMatch.getPlayer1());
        btnHome = findViewById(R.id.btnHome);
        btnPlayerSearch = findViewById(R.id.btnPlayerSearch);
        btnDashboard = findViewById(R.id.btnDashboard);
        tvUsername = findViewById(R.id.tvUsername);
        tvBalance = findViewById(R.id.tvBalance);
        tvUsername.setText(currentPlayer.getUsername());
        tvBalance.setText(String.valueOf(currentPlayer.getBalance()));
        imgProfilePicture = findViewById(R.id.imgProfilePicture);
        tvPlayer1 = findViewById(R.id.tvPlayer1);
        tvPlayer2 = findViewById(R.id.tvPlayer2);
        tvPlayer1.setText(currentMatch.getPlayer1());
        tvPlayer2.setText(currentMatch.getPlayer2());
        if (currentPlayer.getProfilePicture() != null)
        {
            Picasso.with(this).load(currentPlayer.getProfilePicture()).into(imgProfilePicture);
        }

        btnSubmitResult = findViewById(R.id.buttonSubmit);
        btnSubmitResult.setOnClickListener(this);


        matchResults.child(currentMatch.getUuid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> listOfPlayersWon = new ArrayList<String>();
                for (DataSnapshot ds : snapshot.getChildren())
                {
                    listOfPlayersWon.add(ds.getValue(String.class));
                }


                if (listOfPlayersWon.size() == 2)
                {
                    if (listOfPlayersWon.get(0).equals(listOfPlayersWon.get(1)))
                    {
                        if (listOfPlayersWon.get(0).equals(currentPlayer.getUsername()))
                            currentPlayer.setBalance(currentPlayer.getBalance() + currentMatch.getMoneyDeposited());
                        else
                            currentPlayer.setBalance(currentPlayer.getBalance() - currentMatch.getMoneyDeposited());

                        try{
                            matchResults.child(currentMatch.getUuid()).removeValue();
                        }
                        catch (Exception e)
                        {

                        }

                    }
                    else {
                        Toast.makeText(SubmitResults.this, "Results do not match, awaiting for admin evaluation", Toast.LENGTH_LONG).show();

                    }
                    currentPlayer.setMatchOrTournamentId(null);
                    matches.child(currentMatch.getUuid()).removeValue();
                    players.child(currentPlayer.getUsername()).setValue(currentPlayer);

                    Intent intent = new Intent(SubmitResults.this, Home.class);
                    intent.putExtra("currentPlayer", currentPlayer);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    finish();
                    startActivity(intent);
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
        Intent intent;
        switch (id)
        {
            case R.id.buttonSubmit:
                submitResult(view);
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

    private void submitResult(View view)
    {
        final Dialog dialog = new Dialog(SubmitResults.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.layout_submitresult);

        final EditText edPlayer1Score, edPlayer2Score;
        edPlayer1Score = dialog.findViewById(R.id.edPlayer1Score);
        edPlayer2Score = dialog.findViewById(R.id.edPLayer2Score);
        Button btnSubmit = dialog.findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                int player1Score = Integer.valueOf(edPlayer1Score.getText().toString());
                int player2Score = Integer.valueOf(edPlayer2Score.getText().toString());
                String playerWon = "";

                if (player1Score > player2Score)
                    playerWon = currentMatch.getPlayer1();
                else if (player2Score > player1Score)
                    playerWon = currentMatch.getPlayer2();
                else
                    Toast.makeText(SubmitResults.this, "Invalid Results", Toast.LENGTH_LONG).show();

                matchResults.child(currentMatch.getUuid()).child(currentPlayer.getUsername()).setValue(playerWon);

                dialog.dismiss();

            }
        });

        dialog.show();
    }

}