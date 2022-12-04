package com.example.raisethestake;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FindMatch extends AppCompatActivity implements View.OnClickListener {

    EditText edAmount;
    Button btnFindMatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_match);

        initialize();
    }

    private void initialize()
    {
        edAmount = findViewById(R.id.editTextAmount);
        btnFindMatch = findViewById(R.id.buttonFindMatch);
        btnFindMatch.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }
}