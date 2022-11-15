package model;

public class Match {

    private String game;
    private String gameMode;
    private String device;
    private float moneyDeposited;
    private Player player1;
    private Player player2;
    private Player playerWon;

    public Match(String game, String gameMode, String device, float moneyDeposited, Player player1, Player player2, Player playerWon) {
        this.game = game;
        this.gameMode = gameMode;
        this.device = device;
        this.moneyDeposited = moneyDeposited;
        this.player1 = player1;
        this.player2 = player2;
        this.playerWon = playerWon;
    }

    public Match(){

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

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public Player getPlayerWon() {
        return playerWon;
    }

    public void setPlayerWon(Player playerWon) {
        this.playerWon = playerWon;
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
}
