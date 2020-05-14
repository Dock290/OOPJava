package po83.kirgizov.oop.model;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.regex.Pattern;

public class Individual implements Client {
    private static int DEF_SIZE = 16;
    private static int DEF_CREDIT_SCORE = 0;

    private String name;
    private int size;
    protected Account[] accounts;
    private int creditScore;

    public Individual(String name) {
        Objects.requireNonNull(name, "name is null");
        accounts = new Account[DEF_SIZE];
        size = DEF_SIZE;
        this.name = name;
        creditScore = DEF_CREDIT_SCORE;
    }

    public Individual(String name, int size) {
        Objects.requireNonNull(name, "name is null");

        accounts = new Account[size];
        this.size = size;
        this.name = name;
        creditScore = DEF_SIZE;
    }

    public Individual(String name, Account[] accounts) {
        Objects.requireNonNull(name, "name is null");
        Objects.requireNonNull(accounts, "accounts array is null");

        size = accounts.length;
        this.accounts = new Account[size];

        for (int i = 0; i < size; ++i) {
            this.accounts[i] = new DebitAccount(
                    accounts[i].getNumber(), accounts[i].getBalance(),
                    accounts[i].getCreationDate(), accounts[i].getExpirationDate()
            );
        }

        this.name = name;
        creditScore = 0;
    }


    public boolean add(Account account) throws DuplicateAccountNumberException {
        Objects.requireNonNull(account, "account is null");

        if (isNumberMatchFound(account.getNumber())) {
            throw new DuplicateAccountNumberException("account number " + account.getNumber() + " already exists");
        }

        for (int i = 0; i < size; ++i) {
            if (accounts[i] == null) {
                accounts[i] = account;
                return true;
            }
        }

        doubleAccountsArraySize();

        return add(account);
    }

    public boolean add(int index, Account account) throws DuplicateAccountNumberException {
        if (index >= size) {
            throw new IndexOutOfBoundsException("index is greater than size");
        } else if (index < 0) {
            throw new IndexOutOfBoundsException("index is less than zero");
        }

        Objects.requireNonNull(account, "account is null");

        if (isNumberMatchFound(account.getNumber())) {
            throw new DuplicateAccountNumberException("account number " + account.getNumber() + " already exists");
        }

        if (accounts[index] == null) {
            accounts[index] = account;
            return true;
        } else {
            return false;
        }
    }

    public void addCreditScores(int creditScores) {
        this.creditScore += creditScores;
    }

    public Account get(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("index is greater than size");
        } else if (index < 0) {
            throw new IndexOutOfBoundsException("index is less than zero");
        }

        return accounts[index];
    }

    public Account get(String accountNumber) {
        Objects.requireNonNull(accountNumber, "accountNumber is null");

        if (isNumberNotFormatted(accountNumber)) {
            throw new InvalidAccountNumberException("accountNumber has wrong format");
        }

        Account result = null;

        for (int i = 0; i < size; ++i) {
            if (accounts[i] != null) {
                if (accounts[i].getNumber().equals(accountNumber)) {
                    result = accounts[i];
                    break;
                }
            }
        }

        if (Objects.isNull(result)) {
            throw new NoSuchElementException("account with number " + accountNumber + " not found");
        }

        return result;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public int indexOf(Account account) {
        Objects.requireNonNull(account, "account is null");

        for (int i = 0; i < size; ++i) {
            if (accounts[i] != null) {
                if (accounts[i].equals(account)) {
                    return i;
                }
            }
        }

        return -1;
    }

    public boolean hasAccount(String accountNumber) {
        Objects.requireNonNull(accountNumber, "accountNumber is null");

        if (isNumberNotFormatted(accountNumber)) {
            throw new InvalidAccountNumberException("accountNumber has wrong format");
        }

        for (int i = 0; i < size; ++i) {
            if (accounts[i] != null) {
                if (accounts[i].getNumber().equals(accountNumber)) {
                    return true;
                }
            }
        }
        return false;
    }

    public Account set(int index, Account account) throws DuplicateAccountNumberException {
        if (index >= size) {
            throw new IndexOutOfBoundsException("index is greater than size");
        } else if (index < 0) {
            throw new IndexOutOfBoundsException("index is less than zero");
        }

        Objects.requireNonNull(account, "account is null");

        for (int i = 0; i < size; ++i) {
            if (accounts[i] != null) {
                if (accounts[i].getNumber().equals(account.getNumber())) {
                    throw new DuplicateAccountNumberException("account number " + account.getNumber() + " already exists");
                }
            }
        }

        Account changedAccount = accounts[index];
        accounts[index] = account;
        return changedAccount;
    }

    public Account remove(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("index is greater than size");
        } else if (index < 0) {
            throw new IndexOutOfBoundsException("index is less than zero");
        }

        Account changedAccount = accounts[index];
        accounts[index] = null;

        if (size - index + 1 >= 0) {
            System.arraycopy(accounts, index + 1, accounts, index, size - index + 1);
        }

        return changedAccount;
    }

    public Account remove(String accountNumber) {
        Objects.requireNonNull(accountNumber, "accountNumber is null");

        if (isNumberNotFormatted(accountNumber)) {
            throw new InvalidAccountNumberException("accountNumber has wrong format");
        }

        for (int i = 0; i < size; ++i) {
            if (accounts[i] != null) {
                if (accounts[i].getNumber().equals(accountNumber)) {
                    return remove(i);
                }
            }
        }

        throw new NoSuchElementException("account with number " + accountNumber + " not found");
    }

    @Override
    public boolean remove(Account account) {
        Objects.requireNonNull(account, "account is null");

        for (int i = 0; i < size; ++i) {
            if (accounts[i] != null) {
                if (accounts[i].equals(account)) {
                    remove(i);
                    return true;
                }
            }
        }

        return false;
    }

    public int getSize() {
        return size;
    }

    public Account[] getAccounts() {
        Account[] result = new Account[size];
        System.arraycopy(accounts, 0, result, 0, size);

        return result;
    }

    public Account[] sortedAccountsByBalance() {
        Account[] result = new Account[size];
        System.arraycopy(accounts, 0, result, 0, size);

        Account buffer;
        boolean isSorted = false;

        while (!isSorted) {
            isSorted = true;
            for (int i = 0; i < size - 1; i++) {
                if (result[i + 1] != null) {
                    if (result[i] == null || result[i].getBalance() > result[i + 1].getBalance()) {
                        isSorted = false;

                        buffer = result[i];
                        result[i] = result[i + 1];
                        result[i + 1] = buffer;
                    }
                }
            }
        }

        return result;
    }

    public Account[] getCreditAccounts() {
        int newSize = 0;
        for (int i = 0; i < size; ++i) {
            if (accounts[i] != null) {
                if (accounts[i].getClass() == CreditAccount.class) {
                    newSize++;
                }
            }
        }

        Account[] result = new Account[newSize];

        int j = 0;
        for (int i = 0; i < size; ++i) {
            if (accounts[i] != null) {
                if (accounts[i].getClass() == CreditAccount.class) {
                    System.arraycopy(accounts, i, result, j, 1);
                    j++;
                }
            }
        }

        return result;
    }

    @Override
    public double debtTotal() {
        double result = 0;

        for (int i = 0; i < size; ++i) {
            if (accounts[i] != null) {
                if (accounts[i].getClass() == CreditAccount.class) {
                    result += accounts[i].getBalance();
                }
            }
        }

        return result;
    }

    public void setName(String name) {
        Objects.requireNonNull(name, "name is null");

        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double totalBalance() {
        Account[] buffer = getAccounts();
        double result = 0;

        for (Account account : buffer) {
            result += account.getBalance();
        }

        return result;
    }

    private void doubleAccountsArraySize()
    {
        Account[] newAccounts = new Account[size * 2];

        System.arraycopy(accounts, 0, newAccounts, 0, size);
        size *= 2;

        accounts = newAccounts;
    }

    private boolean isNumberMatchFound(String accountNumber) {
        for (int i = 0; i < size; ++i) {
            if (accounts[i] != null) {
                if (accounts[i].getNumber().equals(accountNumber)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isNumberNotFormatted(String accountNumber) {
        Objects.requireNonNull(accountNumber, "accountNumber is null");
        return !Pattern.matches("^4[045]\\d{3}810\\d(?!0{4})\\d{4}(?!0{7})\\d{7}$", accountNumber);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Individual:\n" + "name: " + name + "\ncreditScore: " + creditScore + "\n");
        for (Account a : accounts) {
            if (a != null) {
                result.append(a.toString()).append("\n");
            }
        }
        result.append("total: ").append(totalBalance());
        return result.toString();
    }

    @Override
    public int hashCode() {
        int result = name.hashCode() ^ creditScore;

        for (Account a : accounts) {
            if (a != null) {
                result ^= a.hashCode();
            }
        }

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if ((getClass() == o.getClass()) && name.equals(((Individual) o).getName()) && (size == ((Individual) o).getSize())) {
            for (int i = 0; i < size; i++) {
                if (accounts[i] != null) {
                    if (!accounts[i].equals(((Individual) o).accounts[i])) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Object result = super.clone();

        Account[] resultAccounts = new Account[size];

        for (int i = 0; i < size; ++i) {
            if (accounts[i].getClass() == DebitAccount.class) {
                resultAccounts[i] = new DebitAccount(accounts[i].getNumber(), accounts[i].getBalance(),
                        accounts[i].getCreationDate(), accounts[i].getExpirationDate());
            } else if (accounts[i].getClass() == CreditAccount.class) {
                resultAccounts[i] = new CreditAccount(accounts[i].getNumber(), accounts[i].getBalance(),
                        ((CreditAccount) accounts[i]).getAnnualPercentageRate(),
                        accounts[i].getCreationDate(), accounts[i].getExpirationDate());
            }
        }

        ((Individual) result).accounts = resultAccounts;

        return result;
    }
}
