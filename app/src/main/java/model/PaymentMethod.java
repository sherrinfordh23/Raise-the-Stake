package model;

import java.io.Serializable;

public class PaymentMethod implements Serializable {

    private long creditCardNumber;
    private String cardHolderName;
    private int cvc;

    public PaymentMethod(long creditCardNumber, String cardHolderName, int cvc) {
        this.creditCardNumber = creditCardNumber;
        this.cardHolderName = cardHolderName;
        this.cvc = cvc;
    }

    public PaymentMethod() {
    }

    public long getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(long creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public int getCvc() {
        return cvc;
    }

    public void setCvc(int cvc) {
        this.cvc = cvc;
    }

    @Override
    public String toString() {
        return "PaymentMethod{" +
                "creditCardNumber=" + creditCardNumber +
                ", cardHolderName='" + cardHolderName + '\'' +
                ", cvc=" + cvc +
                '}';
    }
}
