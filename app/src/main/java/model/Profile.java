package model;

import java.io.Serializable;

public class Profile implements Serializable {

    private int level;
    private int gamesPlayed;

    public Profile(int level, int gamesPlayed) {
        this.level = level;
        this.gamesPlayed = gamesPlayed;
    }

    public Profile() {
        this.level = 0;
        this.gamesPlayed = 0;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "level=" + level +
                ", gamesPlayed=" + gamesPlayed +
                '}';
    }
}
