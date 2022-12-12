package com.example.raisethestake;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import model.Match;
import model.Player;

public class SubmitResults extends AppCompatActivity implements View.OnClickListener {

    Button btnSubmitResult;
    ImageButton btnHome, btnPlayerSearch, btnDashboard;
    TextView tvUsername, tvBalance;
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

        btnHome = findViewById(R.id.btnHome);
        btnPlayerSearch = findViewById(R.id.btnPlayerSearch);
        btnDashboard = findViewById(R.id.btnDashboard);
        tvUsername = findViewById(R.id.tvUsername);
        tvBalance = findViewById(R.id.tvBalance);
        tvUsername.setText(currentPlayer.getUsername());
        tvBalance.setText(String.valueOf(currentPlayer.getBalance()));
        imgProfilePicture = findViewById(R.id.imgProfilePicture);
        if (currentPlayer.getProfilePicture() != null)
        {
            Picasso.with(this).load(currentPlayer.getProfilePicture()).into(imgProfilePicture);
        }

        btnSubmitResult = findViewById(R.id.buttonSubmit);
        btnSubmitResult.setOnClickListener(this);

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
        edPlayer1Score = findViewById(R.id.edPlayer1Score);
        edPlayer2Score = findViewById(R.id.edPLayer2Score);
        Button btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                int player1Score = Integer.valueOf(edPlayer1Score.getText().toString());
                int player2Score = Integer.valueOf(edPlayer1Score.getText().toString());
                String playerWon = "";

                if (player1Score > player2Score)
                    playerWon = currentMatch.getPlayer1();
                else if (player2Score > player1Score)
                    playerWon = currentMatch.getPlayer2();
                else
                    Toast.makeText(SubmitResults.this, "Invalid Results", Toast.LENGTH_LONG).show();

                if(currentMatch.getPlayer1().equals(currentPlayer.getUsername()))
                {
                    matchResults.child(currentMatch.getUuid()).setValue(playerWon);
                }

            }
        });

        dialog.show();
    }

}