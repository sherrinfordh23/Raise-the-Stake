package com.example.raisethestake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
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

public class Deposit extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    RadioButton rb10, rb25, rb50, rbCreditDebit;
    EditText edAmount;
    Button btnDeposit;
    ImageButton btnHome, btnPlayerSearch, btnDashboard;

    FirebaseDatabase root = FirebaseDatabase.getInstance();
    DatabaseReference players = root.getReference("Players");

    Player currentPlayer;

    Float amount = 0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);

        initialize();
    }

    private void initialize() {

        rb10 = findViewById(R.id.rb10);
        rb25 = findViewById(R.id.rb25);
        rb50 = findViewById(R.id.rb50);
        rbCreditDebit = findViewById(R.id.rbCreditDebit);
        edAmount = findViewById(R.id.edAmount);
        btnDeposit = findViewById(R.id.btnDeposit);
        btnHome = findViewById(R.id.btnHome);
        btnPlayerSearch = findViewById(R.id.btnPlayerSearch);
        btnDashboard = findViewById(R.id.btnDashboard);


        btnDeposit.setOnClickListener(this);
        rb10.setOnClickListener(this);
        rb25.setOnClickListener(this);
        rb50.setOnClickListener(this);
        btnHome.setOnClickListener(this);
        btnPlayerSearch.setOnClickListener(this);
        btnDashboard.setOnClickListener(this);

        edAmount.addTextChangedListener(this);

        currentPlayer = (Player) getIntent().getExtras().getSerializable("currentPlayer");
    }

    @Override
    public void onClick(View view) {

        Intent intent;
        switch (view.getId())
        {
            case R.id.btnDeposit:
                deposit();
                break;
            case R.id.rb10:
                rb25.setChecked(false);
                rb50.setChecked(false);
                edAmount.setText(null);
                break;
            case R.id.rb25:
                rb10.setChecked(false);
                rb50.setChecked(false);
                edAmount.setText(null);
                break;
            case R.id.rb50:
                rb25.setChecked(false);
                rb10.setChecked(false);
                edAmount.setText(null);
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

    private void deposit() {
        if (!rb10.isChecked() && !rb25.isChecked() && !rb50.isChecked())
        {
            try{
                amount = Float.valueOf(edAmount.getText().toString());
            }
            catch (Exception e)
            {
                Toast.makeText(this, "Please select or enter an amount", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if (rb10.isChecked())
            amount = 10f;
        if (rb25.isChecked())
            amount = 25f;
        if (rb50.isChecked())
            amount = 50f;

        if (!rbCreditDebit.isChecked())
        {
            Toast.makeText(this, "Please select a payment method", Toast.LENGTH_SHORT).show();
            return;
        }

        currentPlayer.setBalance(currentPlayer.getBalance() + amount);

        players.child(currentPlayer.getUsername()).setValue(currentPlayer);

        rb10.setChecked(false);
        rb25.setChecked(false);
        rb50.setChecked(false);
        rbCreditDebit.setChecked(false);
        edAmount.setText(null);


    }



    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        rb10.setChecked(false);
        rb25.setChecked(false);
        rb50.setChecked(false);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}