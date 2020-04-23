package po83.kirgizov.oop.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public abstract class AbstractAccount implements Account, Cloneable {
    private String number;
    private double balance;

    private LocalDate creationDate;
    private LocalDate expirationDate;

    protected AbstractAccount(String number, LocalDate expirationDate)
            throws NullPointerException {
        Objects.requireNonNull(number, "number is null");
        Objects.requireNonNull(expirationDate, "expirationDate is null");

        if (isNumberNotFormatted(number)) {
            throw new InvalidAccountNumberException("number has wrong format");
        }

        this.number = number;
        this.creationDate = LocalDate.now();
        this.expirationDate = expirationDate;
    }

    protected AbstractAccount(String number, double balance, LocalDate creationDate, LocalDate expirationDate)
            throws NullPointerException, IllegalArgumentException {
        Objects.requireNonNull(number, "number is null");
        Objects.requireNonNull(creationDate, "creationDate is null");
        Objects.requireNonNull(expirationDate, "expirationDate is null");

        if (creationDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("CreationDate is in future");
        } else if (creationDate.isAfter(expirationDate)) {
            throw new IllegalArgumentException("ExpirationDate is before CreationDate");
        }

        if (isNumberNotFormatted(number)) {
            throw new InvalidAccountNumberException("number has wrong format");
        }

        this.number = number;
        this.balance = balance;

        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
    }

    public void setNumber(String number) throws NullPointerException {
        Objects.requireNonNull(number, "number is null");

        if (isNumberNotFormatted(number)) {
            throw new InvalidAccountNumberException("number has wrong format");
        }

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

    @Override
    public void setExpirationDate(LocalDate expirationDate) throws NullPointerException {
        Objects.requireNonNull(expirationDate, "expirationDate is null");
        this.expirationDate = expirationDate;
    }

    @Override
    public LocalDate getCreationDate() {
        return creationDate;
    }

    @Override
    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    @Override
    public int monthsQuantityBeforeExpiration() {
        Period period = Period.between(
                LocalDate.now().getDayOfMonth() < 25 ? LocalDate.now() : LocalDate.now().plusMonths(1),
                expirationDate.getDayOfMonth() < 25 ? expirationDate : expirationDate.plusMonths(1));
        int result = period.getYears() * 12 + period.getMonths();
        return result == 0 ? 1 : result;
    }

    @Override
    //todo StringBuilder или String.format?
    // Добавил String.format. Среда разработки предлагает заменить StringBuilder на String
    public String toString() {
        return "number: " +
                getNumber() +
                " balance: " +
                String.format("%f", getBalance()) +
                " creation date: " +
                getCreationDate() +
                " expiration date: " +
                getExpirationDate();
    }

    @Override
    public int hashCode() {
        return number.hashCode() & (
                String.format("%f", balance).hashCode() & creationDate.hashCode() & expirationDate.hashCode()
        );
    }

    @Override
    public boolean equals(Object o) {
        return o.getClass() == this.getClass() && number.equals(((AbstractAccount) o).number) &&
                balance == (((AbstractAccount) o).balance) && creationDate.equals(((AbstractAccount) o).creationDate) &&
                expirationDate.equals(((AbstractAccount) o).expirationDate);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Object result = super.clone();

        ((AbstractAccount) result).creationDate = LocalDate.of(
                creationDate.getYear(), creationDate.getMonth(), creationDate.getDayOfMonth()
        );

        ((AbstractAccount) result).expirationDate = LocalDate.of(
                creationDate.getYear(), creationDate.getMonth(), creationDate.getDayOfMonth()
        );

        return result;
    }
}
