package po83.kirgizov.oop.model;

import java.util.*;

public interface Client extends Iterable<Account>, Comparable<Client>, Collection<Account> {
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

        for (Account account : toArray()) {
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

    default boolean hasAccount(String accountNumber) {
        Objects.requireNonNull(accountNumber, "accountNumber is null");

        if (isNumberNotFormatted(accountNumber)) {
            throw new InvalidAccountNumberException("accountNumber has wrong format");
        }

        for (Account account : toArray()) {
            if (account.getNumber().equals(accountNumber)) {
                return true;
            }
        }

        return false;
    }

    Account remove(int index);

    Account remove(String accountNumber);

    @Override
    default Account[] toArray() {
        Account[] result = new Account[size()];

        int index = 0;
        for (Account account : this) {
            result[index++] = account;
        }

        return result;
    }

    @Override
    default <T> T[] toArray(T[] a) {
        Account[] result = new Account[size()];

        int index = 0;
        for (Account account : this) {
            result[index++] = account;
        }

        return (T[]) result;
    }

    default List<Account> sortedAccountsByBalance() {
        List<Account> result = new ArrayList<>(this);
        Collections.sort(result);
        return result;
    }

    default Collection<Credit> getCreditAccounts() {
        LinkedList<Credit> result = new LinkedList<>();

        for (Account account : this) {
            if (!Objects.isNull(account)) {
                if (account.getClass().equals(CreditAccount.class)) {
                    result.add((Credit) account);
                }
            }
        }

        return result;
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
