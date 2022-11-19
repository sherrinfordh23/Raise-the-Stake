package com.example.raisethestake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

import model.Player;

public class Signup extends AppCompatActivity implements View.OnClickListener, ValueEventListener {

    EditText edFirstName, edLastName, edUsername, edPassword, edConfirmPassword;
    Button btnSignUp;
    TextView txtBack;
    FirebaseDatabase root = FirebaseDatabase.getInstance();
    DatabaseReference players = root.getReference("Players");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initialize();
    }

    private void initialize() {

        edFirstName = findViewById(R.id.editTextFirstName);
        edLastName = findViewById(R.id.editTextLastName);
        edUsername = findViewById(R.id.editTextUsername);
        edPassword = findViewById(R.id.editTextPassword);
        edConfirmPassword = findViewById(R.id.editTextTextPasswordConfirm);
        btnSignUp = findViewById(R.id.buttonSignUp);
        txtBack = findViewById(R.id.textViewBack);
        btnSignUp.setOnClickListener(this);
        txtBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.buttonSignUp:
                createUser();
                break;
            case R.id.textViewBack:
                finish();
        }
    }

    private void createUser() {


        if(edUsername.getText().toString().equals("") ||
                edFirstName.getText().toString().equals("") ||
                edLastName.getText().toString().equals("") ||
                edPassword.getText().toString().equals("") ||
                edConfirmPassword.getText().toString().equals("")
        )
            Toast.makeText(this, "One or more fields are empty", Toast.LENGTH_SHORT).show();
        else{
            String emailOrUsername = edUsername.getText().toString();
            players.child(emailOrUsername).addListenerForSingleValueEvent(this);
        }






    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        if(!snapshot.exists())
        {
            try{

                Player newPlayer = new Player();
                newPlayer.setFirstName(edFirstName.getText().toString());
                newPlayer.setLastName(edLastName.getText().toString());
                newPlayer.setUsername(edUsername.getText().toString());

                if (edPassword.getText().toString().equals(edConfirmPassword.getText().toString()))
                    newPlayer.setPassword(edPassword.getText().toString());
                else
                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();


                players.child(edUsername.getText().toString()).setValue(newPlayer);
                Toast.makeText(this, "New player has been added", Toast.LENGTH_SHORT).show();

                clearWidgets();
            }catch (Exception e)
            {
                Toast.makeText(this, "One or more fields are empty", Toast.LENGTH_SHORT).show();
            }

        }
        else {
            Toast.makeText(this, "The email or username "
                    + edUsername.getText().toString()
                    + " has already existed", Toast.LENGTH_SHORT).show();

            edUsername.requestFocus();

        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }

    public void clearWidgets(){
        edFirstName.setText(null);
        edLastName.setText(null);
        edUsername.setText(null);
        edPassword.setText(null);
        edConfirmPassword.setText(null);
        edFirstName.requestFocus();
    }
}