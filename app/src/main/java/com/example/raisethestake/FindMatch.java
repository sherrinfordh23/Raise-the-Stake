package com.example.raisethestake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

import model.Match;
import model.Player;

public class FindMatch extends AppCompatActivity implements View.OnClickListener, ValueEventListener {

    TextView tvGame, tvBalance, tvName;
    EditText edAmount;
    Button btnFindMatch;
    RadioGroup rgDevice, rgGameMode;
    RadioButton rbPS5, rbXbox, rbMyTeam, rbQuickMatch;

    Player currentPlayer;

    FirebaseDatabase root = FirebaseDatabase.getInstance();
    DatabaseReference playersSearching = root.getReference("PlayersSearching");
    DatabaseReference players = root.getReference("Players");

    String newMatchId;

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
                createMatch(view);
                break;
        }
    }

    private void createMatch(View view)
    {
        // FindMatch Fields Validation
        if ((!rbQuickMatch.isChecked() && !rbMyTeam.isChecked()) ||
                (!rbPS5.isChecked() && !rbXbox.isChecked()) ||
                edAmount.getText().equals(""))
        {
            Toast.makeText(this, "One or many fields are empty", Toast.LENGTH_LONG).show();
        }
        else
        {
            if (currentPlayer.getBalance() < Float.valueOf(edAmount.getText().toString())) {
                Toast.makeText(this, "Insufficient Funds!", Toast.LENGTH_LONG).show();
            } else {
                Match match = new Match();
                newMatchId = UUID.randomUUID().toString();
                playersSearching.child(newMatchId).addListenerForSingleValueEvent(this);
            }
        }
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot)
    {
        if(!snapshot.exists()) {
            try {
                String game = tvGame.getText().toString();

                // selected GameMode
                int selectedGameModeId = rgGameMode.getCheckedRadioButtonId();
                RadioButton selectedGameMode = (RadioButton)findViewById(selectedGameModeId);
                String gameMode = selectedGameMode.getText().toString();

                // selected Device
                int selectedDeviceId = rgDevice.getCheckedRadioButtonId();
                RadioButton selectedDevice = (RadioButton)findViewById(selectedDeviceId);
                String device = selectedDevice.getText().toString();

                Float amount = Float.valueOf(edAmount.getText().toString());
                String player1 = currentPlayer.getUsername();

                Match newMatch = new Match(newMatchId, game, gameMode, device, amount, player1);
                currentPlayer.setMatchOrTournamentId(newMatch.getUuid());

                playersSearching.child(newMatchId).setValue(newMatch);
                players.child(currentPlayer.getUsername()).setValue(currentPlayer);

                Intent intent = new Intent(this, Lobby.class);
                intent.putExtra("currentPlayer", currentPlayer);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                finish();
                startActivity(intent);
            }
            catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
}