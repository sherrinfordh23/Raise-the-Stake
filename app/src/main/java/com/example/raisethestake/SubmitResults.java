package com.example.raisethestake;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import model.Match;
import model.Player;

public class SubmitResults extends AppCompatActivity implements View.OnClickListener {

    Button btnSubmitResult;

    Match currentMatch;
    Player currentPlayer;

    FirebaseDatabase root = FirebaseDatabase.getInstance();
    DatabaseReference matches = root.getReference("Matches");
    DatabaseReference players = root.getReference("Players");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_results);

        initialize();
    }

    private void initialize()
    {
        currentPlayer = (Player) getIntent().getExtras().getSerializable("currentPlayer");

        btnSubmitResult = findViewById(R.id.buttonSubmit);
        btnSubmitResult.setOnClickListener(this);

    }

    @Override
    public void onClick(View view)
    {
        int id = view.getId();
        switch (id)
        {
            case R.id.buttonSubmit:
                submitResult(view);
                break;
        }
    }

    private void submitResult(View view)
    {
        // Create Dialogue with two fields to enter score and btnSubmit
    }
}