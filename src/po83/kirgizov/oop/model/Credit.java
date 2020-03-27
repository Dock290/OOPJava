package po83.kirgizov.oop.model;

import java.time.LocalDate;

public interface Credit {
    double getAnnualPercentageRate();
    void setAnnualPercentageRate(double AnnualPercentageRate);

    double getNextPaymentValue();
    LocalDate getNextPaymentDate();
}
