package po83.kirgizov.oop.model;

public class DebitAccount extends AbstractAccount {
    private String number;
    private double balance;

    public DebitAccount() {
        super();
    }

    public DebitAccount(String number, double balance) {
        super(number, balance);
    }

}
