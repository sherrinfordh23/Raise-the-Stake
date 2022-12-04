package model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.raisethestake.R;

import java.util.ArrayList;

public class TournamentDisplayAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Tournament> listOfTournaments;

    public TournamentDisplayAdapter(Context context, ArrayList<Tournament> listOfTournaments) {
        this.context = context;
        this.listOfTournaments = listOfTournaments;
    }

    @Override
    public int getCount() {
        return listOfTournaments.size();
    }

    @Override
    public Object getItem(int i) {
        return listOfTournaments.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Tournament tournament = (Tournament)getItem(i);
        View one_tournament;

        TextView tvGameMode, tvGame, tvDevice, tvPrize, tvPlayersJoined;
        Button btnJoin;
        LayoutInflater inflater = LayoutInflater.from(context);
        one_tournament =inflater.inflate(R.layout.one_tournament, viewGroup, false);

        tvGameMode = one_tournament.findViewById(R.id.tvGameMode);
        tvGame = one_tournament.findViewById(R.id.tvGame);
        tvDevice = one_tournament.findViewById(R.id.tvDevice);
        tvPrize = one_tournament.findViewById(R.id.tvPrize);
        tvPlayersJoined = one_tournament.findViewById(R.id.tvPlayersJoined);
        btnJoin = one_tournament.findViewById(R.id.btnJoin);


        tvGameMode.setText(tournament.getGameMode());
        tvGame.setText(tournament.getGame());
        tvDevice.setText(tournament.getDevice());
        tvPrize.setText("$" + String.valueOf(tournament.getPrize()));
        tvPlayersJoined.setText(tournament.getListOfPlayers().size() + "/" + tournament.getNumOfPlayers() + " players joined");


        return one_tournament;
    }
}
