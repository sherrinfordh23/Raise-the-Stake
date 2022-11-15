package model;

import java.util.ArrayList;

public class Player {

    private String playerTag;
    private String username;
    private String email;
    private String password;
    private Player listOfFollowers;
    private Player listOfFollowing;
    private float balance;
    private Profile profile;
    private ArrayList<PaymentMethod> paymentMethods;


    public Player(String playerTag, String username, String email, String password, Player listOfFollowers, Player listOfFollowing, float balance, Profile profile, ArrayList<PaymentMethod> paymentMethods) {
        this.playerTag = playerTag;
        this.username = username;
        this.email = email;
        this.password = password;
        this.listOfFollowers = listOfFollowers;
        this.listOfFollowing = listOfFollowing;
        this.balance = balance;
        this.profile = profile;
        this.paymentMethods = paymentMethods;
    }


    public Player() {
    }

    public String getPlayerTag() {
        return playerTag;
    }

    public void setPlayerTag(String playerTag) {
        this.playerTag = playerTag;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Player getListOfFollowers() {
        return listOfFollowers;
    }

    public void setListOfFollowers(Player listOfFollowers) {
        this.listOfFollowers = listOfFollowers;
    }

    public Player getListOfFollowing() {
        return listOfFollowing;
    }

    public void setListOfFollowing(Player listOfFollowing) {
        this.listOfFollowing = listOfFollowing;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public Profile getProfile(){
        return profile;
    }

    public void setProfile(Profile profile){
        this.profile = profile;
    }

    public ArrayList<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(ArrayList<PaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerTag='" + playerTag + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", listOfFollowers=" + listOfFollowers +
                ", listOfFollowing=" + listOfFollowing +
                ", balance=" + balance +
                ", profile=" + profile +
                ", paymentMethods=" + paymentMethods +
                '}';
    }
}
