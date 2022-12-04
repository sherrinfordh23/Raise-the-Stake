package com.example.raisethestake;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import model.Match;
import model.Player;

public class FindMatch extends AppCompatActivity implements View.OnClickListener {

    TextView tvGame, tvBalance, tvName;
    EditText edAmount;
    Button btnFindMatch;
    RadioGroup rgDevice, rgGameMode;
    RadioButton rbPS5, rbXbox, rbMyTeam, rbQuickMatch;

    Player currentPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_match);

        initialize();
    }

    private void initialize()
    {
        currentPlayer = (Player) getIntent().getExtras().getSerializable("currentPlayer");

        tvBalance = findViewById(R.id.tvBalance);
        tvBalance.setText(String.valueOf(currentPlayer.getBalance()));
        tvName = findViewById(R.id.tvUsername);
        tvName.setText(currentPlayer.getUsername());
        tvGame = findViewById(R.id.textNBA);

        rgGameMode = findViewById(R.id.rgGameMode);
        rbMyTeam = findViewById(R.id.rbMyTeam);
        rbQuickMatch = findViewById(R.id.rbQuickMatch);

        rgDevice = findViewById(R.id.rgDevice);
        rbPS5 = findViewById(R.id.rbPS5);
        rbXbox = findViewById(R.id.rbXbox);

        edAmount = findViewById(R.id.editTextAmount);

        btnFindMatch = findViewById(R.id.btnFindMatch);
        btnFindMatch.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        int id = view.getId();
        switch (id)
        {
            case R.id.btnFindMatch:
                checkBalance();
                break;
        }
    }

    private void checkBalance()
    {
        if (currentPlayer.getBalance() < Float.valueOf(edAmount.getText().toString()))
        {
            Toast.makeText(this, "Insufficient Funds!", Toast.LENGTH_LONG).show();
        }
        else {
            Match match = new Match();
        }
    }
}