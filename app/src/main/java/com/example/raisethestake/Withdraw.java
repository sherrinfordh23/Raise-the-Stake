package com.example.raisethestake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import model.Player;

public class Withdraw extends AppCompatActivity implements View.OnClickListener {

    EditText edAmount;
    RadioButton rbCreditDebit, rbPaypal;
    Button btnWithdraw;
    ImageButton btnHome, btnPlayerSearch, btnDashboard;

    FirebaseDatabase root = FirebaseDatabase.getInstance();
    DatabaseReference players = root.getReference("Players");

    Player currentPlayer;
    Float amount = 0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);

        initialize();
    }

    private void initialize() {

        edAmount = findViewById(R.id.edAmount);
        rbCreditDebit = findViewById((R.id.rbCreditDebit));
        rbPaypal = findViewById(R.id.rbPaypal);
        btnWithdraw = findViewById(R.id.btnWithdraw);
        btnHome = findViewById(R.id.btnHome);
        btnPlayerSearch = findViewById(R.id.btnPlayerSearch);
        btnDashboard = findViewById(R.id.btnDashboard);
        btnHome.setOnClickListener(this);
        btnPlayerSearch.setOnClickListener(this);
        btnDashboard.setOnClickListener(this);

        rbCreditDebit.setOnClickListener(this);
        rbPaypal.setOnClickListener(this);
        btnWithdraw.setOnClickListener(this);

        currentPlayer = (Player) getIntent().getExtras().getSerializable("currentPlayer");

    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId())
        {
            case R.id.rbCreditDebit:
                rbPaypal.setChecked(false);
                break;
            case R.id.rbPaypal:
                rbCreditDebit.setChecked(false);
                break;
            case R.id.btnWithdraw:
                withdraw();
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

    private void withdraw() {

        try{
            amount = Float.valueOf(edAmount.getText().toString());
        }
        catch (Exception e)
        {
            Toast.makeText(this, "Please specify an amount", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!rbCreditDebit.isChecked() && !rbPaypal.isChecked())
        {
            Toast.makeText(this, "Please select a payment method", Toast.LENGTH_SHORT).show();
            return;
        }


        currentPlayer.setBalance(currentPlayer.getBalance() - amount);
        players.child(currentPlayer.getUsername()).setValue(currentPlayer);

        rbPaypal.setChecked(false);
        rbCreditDebit.setChecked(false);
        edAmount.setText(null);


    }

}