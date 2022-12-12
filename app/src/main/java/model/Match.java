package model;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.UUID;

public class Match implements Serializable {

    private String uuid;
    private String game;
    private String gameMode;
    private String device;
    private float moneyDeposited;
    private String player1;
    private String player2;
    private String playerWon;
    private boolean player1Ready;
    private boolean player2Ready;
    private String playerWon1;
    private String playerWon2;

    public Match(String game, String gameMode, String device,
                 float moneyDeposited, String player1, String player2, String playerWon) {
        this.uuid = UUID.randomUUID().toString();
        this.game = game;
        this.gameMode = gameMode;
        this.device = device;
        this.moneyDeposited = moneyDeposited;
        this.player1 = player1;
        this.player2 = player2;
        this.playerWon = playerWon;
    }

    public Match(String game, String gameMode, String device, float moneyDeposited, String player1) {
        this.uuid = UUID.randomUUID().toString();
        this.game = game;
        this.gameMode = gameMode;
        this.device = device;
        this.moneyDeposited = moneyDeposited;
        this.player1 = player1;
    }

    public Match(String id, String game, String gameMode, String device, float moneyDeposited, String player1) {
        this.uuid = id;
        this.game = game;
        this.gameMode = gameMode;
        this.device = device;
        this.moneyDeposited = moneyDeposited;
        this.player1 = player1;
    }

    public Match() {
        this.uuid = UUID.randomUUID().toString();
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

    public float getMoneyDeposited() {
        return moneyDeposited;
    }

    public void setMoneyDeposited(float moneyDeposited) {
        this.moneyDeposited = moneyDeposited;
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public String getPlayerWon() {
        return playerWon;
    }

    public void setPlayerWon(String playerWon) {
        this.playerWon = playerWon;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public boolean isPlayer1Ready() {
        return player1Ready;
    }

    public void setPlayer1Ready(boolean player1Ready) {
        this.player1Ready = player1Ready;
    }

    public boolean isPlayer2Ready() {
        return player2Ready;
    }

    public void setPlayer2Ready(boolean player2Ready) {
        this.player2Ready = player2Ready;
    }

    public String getPlayerWon1() {
        return playerWon1;
    }

    public void setPlayerWon1(String playerWon1) {
        this.playerWon1 = playerWon1;
    }

    public String getPlayerWon2() {
        return playerWon2;
    }

    public void setPlayerWon2(String playerWon2) {
        this.playerWon2 = playerWon2;
    }

    @Override
    public String toString() {
        return "Match{" +
                "game='" + game + '\'' +
                ", gameMode='" + gameMode + '\'' +
                ", device='" + device + '\'' +
                ", moneyDeposited=" + moneyDeposited +
                ", player1=" + player1 +
                ", player2=" + player2 +
                ", playerWon=" + playerWon +
                '}';
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        obj = (Match) obj;

        return this.getGame().equals(((Match) obj).getGame()) &&
                this.getGameMode().equals(((Match) obj).gameMode) &&
                this.getDevice().equals(((Match) obj).getDevice()) &&
                this.getMoneyDeposited() == ((Match) obj).getMoneyDeposited() &&
                !this.getPlayer1().equals(((Match) obj).player1);
    }
}
