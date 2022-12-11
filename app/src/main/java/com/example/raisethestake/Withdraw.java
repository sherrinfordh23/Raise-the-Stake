package com.example.raisethestake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

import org.w3c.dom.Text;

import model.Player;

public class Withdraw extends AppCompatActivity implements View.OnClickListener {

    EditText edAmount;
    RadioButton rbCreditDebit, rbPaypal;
    RadioGroup radioPaymentMethod;
    Button btnWithdraw;
    ImageButton btnHome, btnPlayerSearch, btnDashboard;
    TextView tvUsername, tvBalance;
    ImageView imgProfilePicture;

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

        currentPlayer = (Player) getIntent().getExtras().getSerializable("currentPlayer");
        radioPaymentMethod = findViewById(R.id.radioPaymentMethod);
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
        tvUsername = findViewById(R.id.tvUsername);
        tvBalance = findViewById(R.id.tvBalance);
        tvUsername.setText(currentPlayer.getUsername());
        tvBalance.setText(String.valueOf(currentPlayer.getBalance()));
        imgProfilePicture = findViewById(R.id.imgProfilePicture);
        if (currentPlayer.getProfilePicture() != null)
        {
            Picasso.with(this).load(currentPlayer.getProfilePicture()).into(imgProfilePicture);
        }

        rbCreditDebit.setOnClickListener(this);
        rbPaypal.setOnClickListener(this);
        btnWithdraw.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId())
        {
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
            Toast.makeText(this, "Please specify an amount", Toast.LENGTH_LONG).show();
            radioPaymentMethod.clearCheck();
            edAmount.setText(null);
            return;
        }

        if (!rbCreditDebit.isChecked() && !rbPaypal.isChecked())
        {
            Toast.makeText(this, "Please select a payment method", Toast.LENGTH_LONG).show();
            radioPaymentMethod.clearCheck();
            edAmount.setText(null);
            return;
        }

        if (amount > currentPlayer.getBalance())
        {
            Toast.makeText(this, "Are you trying to rob us?", Toast.LENGTH_LONG).show();
            radioPaymentMethod.clearCheck();
            edAmount.setText(null);
            return;
        }


        currentPlayer.setBalance(currentPlayer.getBalance() - amount);
        players.child(currentPlayer.getUsername()).setValue(currentPlayer);
        Toast.makeText(this, "Successfully withdrew $" + amount, Toast.LENGTH_SHORT).show();
        tvBalance.setText(String.valueOf(currentPlayer.getBalance()));

        radioPaymentMethod.clearCheck();
        edAmount.setText(null);


    }

}