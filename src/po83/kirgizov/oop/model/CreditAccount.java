package po83.kirgizov.oop.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;
import java.util.regex.Pattern;

public class CreditAccount extends AbstractAccount implements Credit, Cloneable {
    private double AnnualPercentageRate;

    public CreditAccount(String number, LocalDate expirationDate) {
        super(number, expirationDate);

        if (isNumberNotFormatted(number)) {
            throw new InvalidAccountNumberException("number has wrong format");
        }

        AnnualPercentageRate = 30;
    }

    public CreditAccount(String number, double balance, double AnnualPercentageRate,
                         LocalDate creationDate, LocalDate expirationDate)
            throws IllegalArgumentException {
        super(number, balance, creationDate, expirationDate);

        if (balance > 0) {
            throw new IllegalArgumentException("balance is greater than zero");
        }

        setAnnualPercentageRate(AnnualPercentageRate);
    }

    public double getAnnualPercentageRate() {
        return AnnualPercentageRate;
    }

    public void setAnnualPercentageRate(double AnnualPercentageRate) {
        this.AnnualPercentageRate = AnnualPercentageRate;
    }

    @Override
    public double getNextPaymentValue() {
        double result = getBalance() * (1 + getAnnualPercentageRate() *
                Period.between(LocalDate.now(), getExpirationDate()).getYears())
                / monthsQuantityBeforeExpiration();
        return result < 0 ? -result : result;
    }

    @Override
    public LocalDate getNextPaymentDate() {
        LocalDate result = LocalDate.now();

        if (LocalDate.now().getDayOfMonth() >= 25) {
            result = result.plusMonths(1);
        }
        result.withDayOfMonth(25);

        return result;
    }

    @Override
    public String toString() {
        return "Credit account - " +
                "number: " +
                getNumber() +
                " balance: " +
                String.format("%f", getBalance()) +
                " Annual Percentage Rate: " +
                String.format("%f", getAnnualPercentageRate()) +
                " creation date: " +
                getCreationDate() +
                " expiration date: " +
                getExpirationDate();
    }

    @Override
    public int hashCode() {
        return super.hashCode() & 71 & String.format("%f", AnnualPercentageRate).hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o) && (getAnnualPercentageRate() == ((CreditAccount) o).getAnnualPercentageRate());
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean isNumberNotFormatted(String accountNumber) {
        Objects.requireNonNull(accountNumber, "number is null");
        return !Pattern.matches("^4[45]\\d{3}810\\d(?!0{4})\\d{4}(?!0{7})\\d{7}$", accountNumber);
    }
}
