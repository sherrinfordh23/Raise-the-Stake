package com.example.raisethestake;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.UUID;

import model.Player;

public class account extends AppCompatActivity implements View.OnClickListener {

    Button btnSignOut, btnDeposit, btnWithdraw;
    TextView tvFollowingCount, tvFollowerCount, tvUsername, tvEmail;
    ImageView imgProfilePicture;
    ImageButton btnHome, btnPlayerSearch, btnDashboard;


    DatabaseReference root = FirebaseDatabase.getInstance().getReference();
    DatabaseReference players = root.child("Players");
    StorageReference rootStorage = FirebaseStorage.getInstance().getReference();
    StorageReference newImageReference;


    Player currentPlayer;

    ActivityResultLauncher activityResultLauncher;
    Uri filePath;
    ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        initialize();
    }

    private void initialize() {

        currentPlayer = (Player) getIntent().getExtras().getSerializable("currentPlayer");

        tvFollowingCount = findViewById(R.id.tvFollowingCount);
        tvFollowerCount = findViewById(R.id.tvFollowerCount);
        tvEmail = findViewById(R.id.tvEmail);
        tvUsername = findViewById(R.id.tvUsername);
        btnSignOut = findViewById(R.id.btnSignOut);
        btnDeposit = findViewById(R.id.btnDeposit);
        btnWithdraw = findViewById(R.id.btnWithdraw);
        imgProfilePicture = findViewById(R.id.imgProfilePicture);

        if (currentPlayer.getProfilePicture() != null)
        {
            Picasso.with(this).load(currentPlayer.getProfilePicture()).into(imgProfilePicture);
        }

        btnHome = findViewById(R.id.btnHome);
        btnPlayerSearch = findViewById(R.id.btnPlayerSearch);
        btnDashboard = findViewById(R.id.btnDashboard);

        btnSignOut.setOnClickListener(this);
        btnDeposit.setOnClickListener(this);
        btnWithdraw.setOnClickListener(this);
        imgProfilePicture.setOnClickListener(this);
        btnHome.setOnClickListener(this);
        btnPlayerSearch.setOnClickListener(this);
        btnDashboard.setOnClickListener(this);

        tvUsername.setText(currentPlayer.getUsername());
        if (currentPlayer.getEmail() != null)
            tvEmail.setText(currentPlayer.getEmail());
        if (currentPlayer.getListOfFollowers() != null)
            tvFollowerCount.setText(String.valueOf(currentPlayer.getListOfFollowers().size()));
        else
            tvFollowerCount.setText("0");
        if (currentPlayer.getListOfFollowing() != null)
            tvFollowingCount.setText(String.valueOf(currentPlayer.getListOfFollowing().size()));
        else
            tvFollowingCount.setText("0");
        if (currentPlayer.getProfilePicture() != null)
            Picasso.with(this).load(currentPlayer.getProfilePicture()).into(imgProfilePicture);


        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK && result.getData() != null){
                    filePath = result.getData().getData();

                    try{
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                        imgProfilePicture.setImageBitmap(bitmap);
                        saveProfilePicture();
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(account.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        players.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.exists())
                {
                    Player player = snapshot.getValue(Player.class);
                    currentPlayer = player;
                    tvFollowerCount.setText(String.valueOf(currentPlayer.getListOfFollowers().size()));

                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onClick(View view) {

        Intent intent;

        switch (view.getId())
        {
            case R.id.btnSignOut:
                intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                finish();
                startActivity(intent);
                break;
            case R.id.btnDeposit:
                intent = new Intent(this, Deposit.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("currentPlayer", currentPlayer);
                finish();
                startActivity(intent);
                break;
            case R.id.btnWithdraw:
                intent = new Intent(this, Withdraw.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("currentPlayer", currentPlayer);
                finish();
                startActivity(intent);
                break;
            case R.id.imgProfilePicture:
                clickAndSaveProfilePicture();
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

    private void saveProfilePicture() {
        if (filePath != null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading the photo in progress...");

            progressDialog.show();
            newImageReference = rootStorage.child("/images/" + UUID.randomUUID());
            newImageReference.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Toast.makeText(account.this, "A new image has been uploaded successfully", Toast.LENGTH_LONG).show();
                    newImageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            String newImageUrl = task.getResult().toString();
                            currentPlayer.setProfilePicture(newImageUrl);
                            players.child(currentPlayer.getUsername()).setValue(currentPlayer);

                            Toast.makeText(account.this, "Account has been updated with a new profile picture", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

            newImageReference.putFile(filePath).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(account.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void clickAndSaveProfilePicture() {

        Intent intent;
        intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activityResultLauncher.launch(Intent.createChooser(intent, "Please select a photo"));


        }

}