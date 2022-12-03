package com.example.raisethestake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import model.Player;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ValueEventListener {

    EditText edUsername, edPassword;
    Button btnSignup, btnSignin;
    
    FirebaseDatabase root = FirebaseDatabase.getInstance();
    DatabaseReference players = root.getReference("Players");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    private void initialize() {
        edUsername = findViewById(R.id.editTextUsername);
        edPassword = findViewById(R.id.editTextPassword);
        btnSignup = findViewById(R.id.buttonSignUp);
        btnSignup.setOnClickListener(this);
        btnSignin = findViewById(R.id.buttonSignIn);
        btnSignin.setOnClickListener(this);

        edUsername.setText("blogitthefrog");
        edPassword.setText("thanh123");
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.buttonSignUp:
                signup();
                break;
            case R.id.buttonSignIn:
                signin();
                break;
        }
    }

    private void signin() {

        if(edUsername.getText().toString().equals("") ||
                edPassword.getText().toString().equals("")
        )
            Toast.makeText(this, "One or more fields are empty", Toast.LENGTH_SHORT).show();
        else
            players.child(edUsername.getText().toString()).addListenerForSingleValueEvent(this);


    }

    private void signup() {
        Intent i = new Intent(this, Signup.class);
        startActivity(i);
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        
        if(snapshot.exists())
        {
            Player playerFound = snapshot.getValue(Player.class);
            if(playerFound.getPassword().equals(edPassword.getText().toString()))
            {
                Intent i = new Intent(this, Withdraw.class);
                i.putExtra("currentPlayer", playerFound);
                startActivity(i);
            }
            else
                Toast.makeText(this, "Username not found or password is invalid", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Username not found or password is invalid", Toast.LENGTH_SHORT).show();
        }
        
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
}