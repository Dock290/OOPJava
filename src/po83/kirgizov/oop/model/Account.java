package po83.kirgizov.oop.model;

import java.time.LocalDate;

public interface Account {
    void setNumber(String number);
    void setBalance(double balance);

    String getNumber();
    double getBalance();

    void setExpirationDate(LocalDate expirationDate);

    LocalDate getCreationDate();
    LocalDate getExpirationDate();

    int monthsQuantityBeforeExpiration();

    boolean isNumberNotFormatted(String number);
}
