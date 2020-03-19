package po83.kirgizov.oop.model;

public abstract class AbstractAccount implements Account{
    private String number;
    private double balance;

    protected AbstractAccount() {
        number = "";
        balance = 0;
    }

    protected AbstractAccount(String number, double balance) {
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
