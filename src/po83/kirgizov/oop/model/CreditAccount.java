package po83.kirgizov.oop.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;
import java.util.Scanner;

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
                //todo почему умножение на 0.1? в формуле этого не было
                Period.between(LocalDate.now(), getExpirationDate()).getYears() * 0.1)
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

    //todo уже можно пользоваться StringBuilder и String.format
    @Override
    public String toString() {
        return "Credit account - " + "number: " + getNumber() + " balance: " + getBalance() +
                " Annual Percentage Rate: " + AnnualPercentageRate +
                "\ncreation date: " + getCreationDate() + " expiration date: " + getExpirationDate();
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
    public boolean isNumberNotFormatted(String number) {
        Objects.requireNonNull(number, "number is null");

        //todo почему не паттерн для проверки номера?
        return !(number.length() == 20 &&
                number.charAt(0) == '4' && (number.charAt(1) == '4' || number.charAt(1) == '5') &&
                number.startsWith("810", 5) &&
                !number.startsWith("0000", 9) &&
                !number.startsWith("0000000", 13));

    }
}
