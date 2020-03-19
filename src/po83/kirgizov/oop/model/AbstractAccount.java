package po83.kirgizov.oop.model;

public abstract class AbstractAccount implements Account, Cloneable{
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

    public String toString() {
        return "number: " + number + " balance: " + balance;
    }

    @Override
    public int hashCode() {
        return number.hashCode() * String.format("%f", balance).hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return o.getClass() == this.getClass() && number.equals(((AbstractAccount)o).number) && balance == (((AbstractAccount)o).balance);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
