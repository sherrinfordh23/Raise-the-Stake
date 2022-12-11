package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {

    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private ArrayList<String> listOfFollowers;
    private ArrayList<String> listOfFollowing;
    private float balance;
    private Profile profile;
    private ArrayList<String> paymentMethods;
    private String matchOrTournamentId;
    private String profilePicture;


    public Player(String username, String email, String firstName, String lastName, String password, ArrayList<String> listOfFollowers, ArrayList<String> listOfFollowing, float balance, Profile profile, ArrayList<String> paymentMethods) {
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.listOfFollowers = listOfFollowers;
        this.listOfFollowing = listOfFollowing;
        this.balance = balance;
        this.profile = profile;
        this.paymentMethods = paymentMethods;
    }

    public Player() {
        balance = 0;
        paymentMethods = new ArrayList<String>();
        listOfFollowers = new ArrayList<String>();
        listOfFollowing = new ArrayList<String>();
        profile = new Profile();
        matchOrTournamentId = null;
        email = "";
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public ArrayList<String> getListOfFollowers() {
        return listOfFollowers;
    }

    public void setListOfFollowers(ArrayList<String> listOfFollowers) {
        this.listOfFollowers = listOfFollowers;
    }

    public ArrayList<String> getListOfFollowing() {
        return listOfFollowing;
    }

    public void setListOfFollowing(ArrayList<String> listOfFollowing) {
        this.listOfFollowing = listOfFollowing;
    }

    public ArrayList<String> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(ArrayList<String> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public String getMatchOrTournamentId() {
        return matchOrTournamentId;
    }

    public void setMatchOrTournamentId(String matchOrTournamentId) {
        this.matchOrTournamentId = matchOrTournamentId;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    @Override
    public String toString() {
        return "Player{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", listOfFollowers=" + listOfFollowers +
                ", listOfFollowing=" + listOfFollowing +
                ", balance=" + balance +
                ", profile=" + profile +
                ", paymentMethods=" + paymentMethods +
                '}';
    }
}
