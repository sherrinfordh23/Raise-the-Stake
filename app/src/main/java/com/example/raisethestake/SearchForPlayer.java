package com.example.raisethestake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.telecom.TelecomManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

import model.Player;

public class SearchForPlayer extends AppCompatActivity implements View.OnClickListener, TextWatcher, ValueEventListener {

    TextView tvPlayer;
    EditText edSearch;
    Button btnFollow;
    Button btnChallenge;

    DatabaseReference root = FirebaseDatabase.getInstance().getReference();
    DatabaseReference players = root.child("Players");
    Player currentPlayer, playerFound = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_for_player);

        initialize();
    }

    private void initialize() {

        tvPlayer = findViewById(R.id.tvPlayer);
        edSearch = findViewById(R.id.edSearch);
        currentPlayer = (Player) getIntent().getExtras().getSerializable("currentPlayer");
        btnFollow = findViewById(R.id.btnFollow);
        btnChallenge = findViewById(R.id.btnChallenge);
        btnChallenge.setOnClickListener(this);
        btnFollow.setOnClickListener(this);
        edSearch.addTextChangedListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btnFollow:
                follow();
                break;
            case R.id.btnChallenge:

                break;
        }
    }

    private void follow() {

        if(playerFound == null)
        {
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();

        }
        else if(playerFound.getUsername().equals(currentPlayer.getUsername()))
        {
            Toast.makeText(this, "Cannot follow oneself!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            ArrayList<String> playerFoundListOfFollowers = playerFound.getListOfFollowers();
            ArrayList<String> currentPlayerListOfFollowing = currentPlayer.getListOfFollowing();

            if (!playerFoundListOfFollowers.contains(currentPlayer))
            {
                playerFoundListOfFollowers.add(currentPlayer.getUsername());
                currentPlayerListOfFollowing.add(playerFound.getUsername());

                players.child(currentPlayer.getUsername()).setValue(currentPlayer);
                players.child(playerFound.getUsername()).setValue(playerFound);


            }
            else
                Toast.makeText(this, "Already following user " + playerFound.getUsername(), Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        players.child(edSearch.getText().toString()).addListenerForSingleValueEvent(this);
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {

        if(snapshot.exists())
        {
            playerFound = snapshot.getValue(Player.class);
            tvPlayer.setText(playerFound.getUsername());
        }
        else{
            playerFound = null;
            tvPlayer.setText("Player's Name");
        }


    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
}