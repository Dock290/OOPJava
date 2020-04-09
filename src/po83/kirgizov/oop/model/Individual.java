package po83.kirgizov.oop.model;

import java.util.NoSuchElementException;
import java.util.Objects;

public class Individual implements Client {
    private String name;
    private int size;
    protected Account[] accounts;
    private int creditScore;

    public Individual(String name) {
        Objects.requireNonNull(name, "name is null");

        accounts = new Account[16];
        size = 16;
        this.name = name;
        creditScore = 0;
    }

    public Individual(String name, int size) {
        Objects.requireNonNull(name, "name is null");

        accounts = new Account[size];
        this.size = size;
        this.name = name;
        creditScore = 0;
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

        for (int i = 0; i < size; ++i) {
            if (accounts[i] != null) {
                if (accounts[i].getNumber().equals(account.getNumber())) {
                    throw new DuplicateAccountNumberException("account number " + account.getNumber() + " already exists");
                }
            }
        }

        for (int i = 0; i < size; ++i) {
            if (accounts[i] == null) {
                accounts[i] = account;
                return true;
            }
        }

        Account[] newAccounts = new Account[size * 2];
        for (int i = 0; i < size * 2; ++i) {
            if (i < accounts.length) {
                newAccounts[i] = accounts[i];
            } else {
                if (newAccounts[i] == null) {
                    newAccounts[i] = account;
                    accounts = newAccounts;
                    size *= 2;
                    return true;
                }
            }
        }

        return false;
    }

    public boolean add(int index, Account account) throws DuplicateAccountNumberException {
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

        if (size - index + 1 >= 0) System.arraycopy(accounts,
                index + 1, accounts, index, size - index + 1);

        return changedAccount;
    }

    public Account remove(String accountNumber) {
        Objects.requireNonNull(accountNumber, "accountNumber is null");

        if (isNumberNotFormatted(accountNumber)) {
            throw new InvalidAccountNumberException("accountNumber has wrong format");
        }

        Account changedAccount = null;
        for (int i = 0; i < size; ++i) {
            if (accounts[i] != null) {
                if (accounts[i].getNumber().equals(accountNumber)) {
                    changedAccount = accounts[i];
                    accounts[i] = null;
                    i--;
                }
            }

            if (changedAccount != null && i < size - 1) {
                accounts[i] = accounts[i + 1];
            }
        }

        if (Objects.isNull(changedAccount)) {
            throw new NoSuchElementException("account with number " + accountNumber + " not found");
        }

        return changedAccount;
    }

    @Override
    public boolean remove(Account account) {
        Objects.requireNonNull(account, "account is null");

        boolean isDeleted = false;
        for (int i = 0; i < size; ++i) {
            if (!isDeleted) {
                if (accounts[i] != null) {
                    if (accounts[i].equals(account)) {
                        accounts[i] = null;
                        isDeleted = true;
                        i--;
                    }
                }
            } else if (i < size - 1) {
                accounts[i] = accounts[i + 1];
            }
        }
        return isDeleted;
    }

    public int getSize() {
        return size;
    }

    public Account[] getAccounts() {
        int newSize = 0;

        for (int i = 0; i < size; ++i) {
            if (accounts[i] != null) {
                newSize++;
            }
        }

        Account[] result = new Account[newSize];

        int j = 0;
        for (int i = 0; i < size; ++i) {
            if (accounts[i] != null) {
                result[j] = accounts[i];
                j++;
            }
        }

        return result;
    }

    public Account[] sortedAccountsByBalance() {
        int newSize = 0;

        for (int i = 0; i < size; ++i) {
            if (accounts[i] != null) {
                newSize++;
            }
        }

        Account[] result = new Account[newSize];

        int j = 0;
        for (int i = 0; i < size; ++i) {
            if (accounts[i] != null) {
                result[j] = accounts[i];
                j++;
            }
        }

        Account buffer;
        boolean isSorted = false;

        while (!isSorted) {
            for (int i = 0; i < newSize - 1; i++) {
                if (result[i] != null && result[i + 1] != null) {
                    isSorted = true;
                    if (result[i].getBalance() > result[i + 1].getBalance()) {
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
                    result[j] = accounts[i];
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

    @Override
    public boolean isNumberNotFormatted(String accountNumber) {
        Objects.requireNonNull(accountNumber, "accountNumber is null");

        return !(accountNumber.length() == 20 &&
                accountNumber.charAt(0) == '4' &&
                (accountNumber.charAt(1) == '0' || accountNumber.charAt(1) == '4' || accountNumber.charAt(1) == '5') &&
                accountNumber.startsWith("810", 5) &&
                !accountNumber.startsWith("0000", 9) &&
                !accountNumber.startsWith("0000000", 13));
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
            result ^= a.hashCode();
        }

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if ((getClass() == o.getClass()) && (size == ((Individual) o).getSize())) {
            for (int i = 0; i < size; i++) {
                if (!accounts[i].equals(((Individual) o).accounts[i])) {
                    return false;
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
            if (accounts[i].getClass() == DebitAccount.class)
            {
                resultAccounts[i] = new DebitAccount(accounts[i].getNumber(), accounts[i].getBalance(),
                        accounts[i].getCreationDate(), accounts[i].getExpirationDate());
            }
            else if (accounts[i].getClass() == CreditAccount.class)
            {
                resultAccounts[i] = new CreditAccount(accounts[i].getNumber(), accounts[i].getBalance(),
                        ((CreditAccount)accounts[i]).getAnnualPercentageRate(),
                        accounts[i].getCreationDate(), accounts[i].getExpirationDate());
            }
        }

        ((Individual)result).accounts = resultAccounts;

        return result;
    }
}
