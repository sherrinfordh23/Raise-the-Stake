package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class Tournament {

    private String uuid;
    private String game;
    private String gameMode;
    private String device;
    private ArrayList<String> listOfPlayers;
    private float prize = 50;
    private ArrayList<String> listOfMatches;
    private int numOfPlayers = 16;


    public Tournament(String game, String gameMode, String device, ArrayList<String> listOfPlayers, float prize, ArrayList<String> listOfMatches) {
        uuid = UUID.randomUUID().toString();
        this.game = game;
        this.gameMode = gameMode;
        this.device = device;
        this.listOfPlayers = listOfPlayers;
        this.prize = prize;
        this.listOfMatches = listOfMatches;
    }

    public Tournament(){
        uuid = UUID.randomUUID().toString();
        listOfPlayers = new ArrayList<String>();
        listOfMatches = new ArrayList<String>();
    }

    public String getUuid() { return uuid ;}

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

    public ArrayList<String> getListOfPlayers() {
        return listOfPlayers;
    }

    public void setListOfPlayers(ArrayList<String> listOfPlayers) {
        this.listOfPlayers = listOfPlayers;
    }

    public ArrayList<String> getListOfMatches() {
        return listOfMatches;
    }

    public void setListOfMatches(ArrayList<String> listOfMatches) {
        this.listOfMatches = listOfMatches;
    }

    public void setNumOfPlayers(int numOfPlayers)
    {
        this.numOfPlayers = numOfPlayers;
    }

    public int getNumOfPlayers()
    {
        return this.numOfPlayers;
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
