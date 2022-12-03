package model;

import java.util.ArrayList;
import java.util.Arrays;

public class Tournament {

    private String game;
    private String gameMode;
    private String device;
    private ArrayList<Player> listOfPlayers;
    private float prize;
    private ArrayList<Match> listOfMatches;


    public Tournament(String game, String gameMode, String device, ArrayList<Player> listOfPlayers, float prize, ArrayList<Match> listOfMatches) {
        this.game = game;
        this.gameMode = gameMode;
        this.device = device;
        this.listOfPlayers = listOfPlayers;
        this.prize = prize;
        this.listOfMatches = listOfMatches;
    }

    public Tournament(){

    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public float getPrize() {
        return prize;
    }

    public void setPrize(float prize) {
        this.prize = prize;
    }

    public ArrayList<Player> getListOfPlayers() {
        return listOfPlayers;
    }

    public void setListOfPlayers(ArrayList<Player> listOfPlayers) {
        this.listOfPlayers = listOfPlayers;
    }

    public ArrayList<Match> getListOfMatches() {
        return listOfMatches;
    }

    public void setListOfMatches(ArrayList<Match> listOfMatches) {
        this.listOfMatches = listOfMatches;
    }


    @Override
    public String toString() {
        return "Tournament{" +
                "game='" + game + '\'' +
                ", gameMode='" + gameMode + '\'' +
                ", device='" + device + '\'' +
                ", listOfPlayers=" + listOfPlayers +
                ", prize=" + prize +
                ", listOfMatches=" + listOfMatches +
                '}';
    }
}
