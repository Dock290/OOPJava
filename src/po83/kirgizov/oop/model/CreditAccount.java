package po83.kirgizov.oop.model;

public class CreditAccount extends AbstractAccount implements Credit{
    private double AnnualPercentageRate;

    public CreditAccount() {
        AnnualPercentageRate = 30;
    }

    public CreditAccount(String number, double balance, double AnnualPercentageRate) {
        setNumber(number);
        setBalance(balance);
        setAnnualPercentageRate(AnnualPercentageRate);
    }

    public double getAnnualPercentageRate() {
        return AnnualPercentageRate;
    }

    public void setAnnualPercentageRate(double AnnualPercentageRate) {
        this.AnnualPercentageRate = AnnualPercentageRate;
    }
}
