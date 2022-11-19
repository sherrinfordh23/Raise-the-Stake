package model;

import java.util.ArrayList;

public class Player {

    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private ArrayList<Player> listOfFollowers;
    private ArrayList<Player> listOfFollowing;
    private float balance;
    private Profile profile;
    private ArrayList<PaymentMethod> paymentMethods;

    public Player(String username, String email, String firstName, String lastName, String password, ArrayList<Player> listOfFollowers, ArrayList<Player> listOfFollowing, float balance, Profile profile, ArrayList<PaymentMethod> paymentMethods) {
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
        paymentMethods = new ArrayList<PaymentMethod>();
        listOfFollowers = new ArrayList<Player>();
        listOfFollowing = new ArrayList<Player>();
        profile = new Profile();
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

    public ArrayList<Player> getListOfFollowers() {
        return listOfFollowers;
    }

    public void setListOfFollowers(ArrayList<Player> listOfFollowers) {
        this.listOfFollowers = listOfFollowers;
    }

    public ArrayList<Player> getListOfFollowing() {
        return listOfFollowing;
    }

    public void setListOfFollowing(ArrayList<Player> listOfFollowing) {
        this.listOfFollowing = listOfFollowing;
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

    public ArrayList<PaymentMethod> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(ArrayList<PaymentMethod> paymentMethods) {
        this.paymentMethods = paymentMethods;
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
