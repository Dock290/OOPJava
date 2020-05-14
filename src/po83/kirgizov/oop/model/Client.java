package po83.kirgizov.oop.model;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;

public interface Client extends Iterable<Account>, Comparable<Client> {
    boolean add(Account account) throws DuplicateAccountNumberException;

    boolean add(int index, Account account) throws DuplicateAccountNumberException;

    void addCreditScores(int creditScores);

    Account set(int index, Account account) throws DuplicateAccountNumberException;

    Account get(int index);

    default Account get(String accountNumber) {
        Objects.requireNonNull(accountNumber, "accountNumber is null");

        if (isNumberNotFormatted(accountNumber)) {
            throw new InvalidAccountNumberException("accountNumber has wrong format");
        }

        Account result = null;

        for (Account account : getAccounts()) {
            if (account.getNumber().equals(accountNumber)) {
                result = account;
                break;
            }
        }

        if (Objects.isNull(result)) {
            throw new NoSuchElementException("account with number " + accountNumber + " not found");
        }

        return result;
    }

    int getCreditScore();

    default ClientStatus getStatus() {
        int creditScores = getCreditScore();

        if (creditScores >= 5) {
            return ClientStatus.PLATINUM;
        } else if (creditScores >= 3) {
            return ClientStatus.GOLD;
        } else if (creditScores >= 0) {
            return ClientStatus.GOOD;
        } else if (creditScores > -4) {
            return ClientStatus.RISKY;
        } else {
            return ClientStatus.BAD;
        }
    }

    int indexOf(Account account);

    int getSize();

    default boolean hasAccount(String accountNumber) {
        Objects.requireNonNull(accountNumber, "accountNumber is null");

        if (isNumberNotFormatted(accountNumber)) {
            throw new InvalidAccountNumberException("accountNumber has wrong format");
        }

        for (Account account : getAccounts()) {
            if (account.getNumber().equals(accountNumber)) {
                return true;
            }
        }

        return false;
    }

    Account remove(int index);

    Account remove(String accountNumber);

    boolean remove(Account account);

    default Account[] getAccounts() {
        Account[] result = new Account[getSize()];

        int index = 0;
        for (Account account : this) {
            result[index++] = account;
        }

        return result;
    }

    default Account[] sortedAccountsByBalance() {
        Account[] result = getAccounts();
        Arrays.sort(result);
        return result;
    }

    default Account[] getCreditAccounts() {
        int newSize = 0;

        for (Account account : this) {
            if (!Objects.isNull(account)) {
                if (account.getClass() == CreditAccount.class) {
                    newSize++;
                }
            }
        }

        Account[] creditAccounts = new Account[newSize];

        int index = 0;
        for (Account account : this) {
            if (!Objects.isNull(account)) {
                if (account.getClass() == CreditAccount.class) {
                    creditAccounts[index++] = account;
                }
            }
        }

        return creditAccounts;
    }

    default double debtTotal() {
        double result = 0;

        for (Account account : this) {
            if (!Objects.isNull(account)) {
                if (account.getClass() == CreditAccount.class) {
                    result += account.getBalance();
                }
            }
        }

        return result;
    }

    void setName(String name);

    String getName();

    default double totalBalance() {
        double result = 0;

        for (Account account : this) {
            if (!Objects.isNull(account))
            {
                result += account.getBalance();
            }
        }

        return result;
    }

    boolean isNumberNotFormatted(String accountNumber);
}
