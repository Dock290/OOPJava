package po83.kirgizov.oop.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.regex.Pattern;

public class DebitAccount extends AbstractAccount implements Cloneable {
    public DebitAccount(String number, LocalDate expirationDate) {
        super(number, expirationDate);

        if (isNumberNotFormatted(number)) {
            throw new InvalidAccountNumberException("number has wrong format");
        }
    }

    public DebitAccount(String number, double balance, LocalDate creationDate, LocalDate expirationDate)
            throws IllegalArgumentException {
        super(number, balance, creationDate, expirationDate);

        if (balance < 0) {
            throw new IllegalArgumentException("balance is less than zero");
        }
    }

    @Override
    public String toString()
    {
        return "Debit account: " +
                super.toString();
    }

    @Override
    public int hashCode() {
        return super.hashCode() * 53;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean isNumberNotFormatted(String accountNumber) {
        Objects.requireNonNull(accountNumber, "accountNumber is null");
        return !Pattern.matches("^40\\d{3}810\\d(?!0{4})\\d{4}(?!0{7})\\d{7}$", accountNumber);
    }
}
