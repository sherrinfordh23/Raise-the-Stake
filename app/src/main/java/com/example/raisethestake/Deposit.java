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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import model.Player;

public class Deposit extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    RadioButton rb10, rb25, rb50, rbCreditDebit;
    EditText edAmount;
    Button btnDeposit;
    ImageButton btnHome, btnPlayerSearch, btnDashboard;
    ImageView imgProfilePicture;
    TextView tvUsername, tvBalance;
    RadioGroup rgAmount, radioCreditDebit;

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
        currentPlayer = (Player) getIntent().getExtras().getSerializable("currentPlayer");

        rgAmount = findViewById(R.id.rgAmount);
        radioCreditDebit = findViewById(R.id.radioCreditDebit);
        rb10 = findViewById(R.id.rb10);
        rb25 = findViewById(R.id.rb25);
        rb50 = findViewById(R.id.rb50);
        rbCreditDebit = findViewById(R.id.rbCreditDebit);
        edAmount = findViewById(R.id.edAmount);
        btnDeposit = findViewById(R.id.btnDeposit);
        btnHome = findViewById(R.id.btnHome);
        btnPlayerSearch = findViewById(R.id.btnPlayerSearch);
        btnDashboard = findViewById(R.id.btnDashboard);
        imgProfilePicture = findViewById(R.id.imgProfilePicture);
        imgProfilePicture.setOnClickListener(this);
        tvUsername = findViewById(R.id.tvUsername);
        tvBalance = findViewById(R.id.tvBalance);
        tvUsername.setText(currentPlayer.getUsername());
        tvBalance.setText(String.valueOf(currentPlayer.getBalance()));
        if (currentPlayer.getProfilePicture() != null)
        {
            Picasso.with(this).load(currentPlayer.getProfilePicture()).into(imgProfilePicture);
        }

        btnDeposit.setOnClickListener(this);
        btnHome.setOnClickListener(this);
        btnPlayerSearch.setOnClickListener(this);
        btnDashboard.setOnClickListener(this);

        edAmount.addTextChangedListener(this);

    }

    @Override
    public void onClick(View view) {

        Intent intent;
        switch (view.getId())
        {
            case R.id.btnDeposit:
                deposit();
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
                rgAmount.clearCheck();
                radioCreditDebit.clearCheck();
                edAmount.setText(null);

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
            rgAmount.clearCheck();
            radioCreditDebit.clearCheck();
            edAmount.setText(null);

            return;
        }

        Toast.makeText(this, "Successfully deposited $" + amount, Toast.LENGTH_SHORT).show();
        currentPlayer.setBalance(currentPlayer.getBalance() + amount);
        tvBalance.setText(String.valueOf(currentPlayer.getBalance()));
        players.child(currentPlayer.getUsername()).setValue(currentPlayer);

        rgAmount.clearCheck();
        radioCreditDebit.clearCheck();
        edAmount.setText(null);


    }



    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        if (rb10.isChecked() || rb25.isChecked() || rb50.isChecked())
        {
            rgAmount.clearCheck();
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}