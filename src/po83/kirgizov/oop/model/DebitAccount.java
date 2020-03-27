package po83.kirgizov.oop.model;

import java.time.LocalDate;
import java.util.Objects;

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
    public String toString() {
        return super.toString();
    }

    @Override
    public int hashCode() {
        return super.hashCode() & 53;
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
    public boolean isNumberNotFormatted(String number) {
        Objects.requireNonNull(number, "number is null");

        return !(number.length() == 20 &&
                number.charAt(0) == '4' && number.charAt(1) == '0' &&
                number.startsWith("810", 5) &&
                !number.startsWith("0000", 9) &&
                !number.startsWith("0000000", 13));
    }
}
