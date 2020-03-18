package po83.kirgizov.oop.model;

public class DebitAccount implements Account {
    private String number;
    private double balance;

    public DebitAccount() {
        number = "";
        balance = 0;
    }

    public DebitAccount(String number, double balance) {
        this.number = number;
        this.balance = balance;
    }


    public void setNumber(String number) {
        this.number = number;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getNumber() {
        return number;
    }

    public double getBalance() {
        return balance;
    }

}
