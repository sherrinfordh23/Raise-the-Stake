package model;

import android.content.Context;
import android.provider.ContactsContract;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.raisethestake.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MatchDisplayAdapter extends BaseAdapter {

    private ArrayList<String> listOfMatches;
    private Context context;

    DatabaseReference matches = FirebaseDatabase.getInstance().getReference("Matches");


    public MatchDisplayAdapter(ArrayList<String> listOfMatches, Context context){
        this.listOfMatches = listOfMatches;
        this.context = context;
    }


    @Override
    public int getCount() {
        return listOfMatches.size();
    }

    @Override
    public Object getItem(int i) {
        return listOfMatches.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        String matchUUID = (String) getItem(i);

        TextView tvMatchNumber, tvPlayer1, tvPlayer2;
        LayoutInflater inflater = LayoutInflater.from(context);
        View one_match;
        one_match = inflater.inflate(R.layout.one_match, viewGroup, false);

        tvMatchNumber = one_match.findViewById(R.id.tvMatchNumber);
        tvPlayer1 = one_match.findViewById(R.id.tvPlayer1);
        tvPlayer2 = one_match.findViewById(R.id.tvPlayer2);

        tvMatchNumber.setText(tvMatchNumber.getText() + " " + String.valueOf(listOfMatches.indexOf(matchUUID) + 1));

        matches.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Toast.makeText(context, snapshot.getKey().toString(), Toast.LENGTH_LONG).show();
                if (snapshot.getKey().equals(matchUUID))
                {
                    tvPlayer1.setText(snapshot.child("player1").getValue(String.class));
                    tvPlayer2.setText(snapshot.child("player2").getValue(String.class));
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

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

        /*
        tvPlayer1.setText(match.getPlayer1().getUsername());
        tvPlayer2.setText(match.getPlayer2().getUsername());
*/

        return one_match;
    }
}
