package po83.kirgizov.oop.model;

public class DebitAccount extends AbstractAccount implements Cloneable{
    public DebitAccount() {
        super();
    }

    public DebitAccount(String number, double balance) {
        super(number, balance);
    }

    @Override
    public String toString() {
        return "DebitAccount - " + "number: " + getNumber() + " balance = " + getBalance();
    }

    @Override
    public int hashCode() {
        return super.hashCode() * 53;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
