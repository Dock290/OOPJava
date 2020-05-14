package po83.kirgizov.oop.model;

import java.util.Iterator;
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

        System.arraycopy(accounts, 0, this.accounts, 0, size);

        this.name = name;
        creditScore = 0;
    }


    public boolean add(Account account) throws DuplicateAccountNumberException {
        Objects.requireNonNull(account, "account is null");

        if (isNumberMatchFound(account.getNumber())) {
            throw new DuplicateAccountNumberException("account number " + account.getNumber() + " already exists");
        }

        int index = 0;
        for (Account buffer : this) {
            if (Objects.isNull(buffer)) {
                accounts[index] = account;
                return true;
            }
            index++;
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

        if (Objects.isNull(accounts[index])) {
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

    public int getCreditScore() {
        return creditScore;
    }

    public int indexOf(Account account) {
        Objects.requireNonNull(account, "account is null");

        int index = 0;
        for (Account buffer : this) {
            if (!Objects.isNull(buffer)) {
                if (buffer.equals(account)) {
                    return index;
                }
            }
            index++;
        }

        return -1;
    }

    public Account set(int index, Account account) throws DuplicateAccountNumberException {
        if (index >= size) {
            throw new IndexOutOfBoundsException("index is greater than size");
        } else if (index < 0) {
            throw new IndexOutOfBoundsException("index is less than zero");
        }

        Objects.requireNonNull(account, "account is null");

        for (Account buffer : accounts) {
            if (!Objects.isNull(buffer)) {
                if (buffer.getNumber().equals(account.getNumber())) {
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

        int index = 0;
        for (Account account : this) {
            if (!Objects.isNull(account)) {
                if (account.getNumber().equals(accountNumber)) {
                    return remove(index);
                }
            }
            index++;
        }

        throw new NoSuchElementException("account with number " + accountNumber + " not found");
    }

    @Override
    public boolean remove(Account account) {
        Objects.requireNonNull(account, "account is null");

        int index = 0;
        for (Account buffer : this) {
            if (!Objects.isNull(buffer)) {
                if (buffer.equals(account)) {
                    remove(index);
                    return true;
                }
            }
            index++;
        }

        return false;
    }

    public int getSize() {
        return size;
    }

    public void setName(String name) {
        Objects.requireNonNull(name, "name is null");

        this.name = name;
    }

    public String getName() {
        return name;
    }

    private void doubleAccountsArraySize() {
        Account[] newAccounts = new Account[size * 2];

        System.arraycopy(accounts, 0, newAccounts, 0, size);
        size = newAccounts.length;

        accounts = newAccounts;
    }

    private boolean isNumberMatchFound(String accountNumber) {
        for (Account account : accounts) {
            if (!Objects.isNull(account)) {
                if (account.getNumber().equals(accountNumber)) {
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

        for (Account account : this) {
            if (!Objects.isNull(account)) {
                result.append(account.toString()).append("\n");
            }
        }

        result.append("total: ").append(totalBalance());
        return result.toString();
    }

    @Override
    public int hashCode() {
        int result = name.hashCode() ^ creditScore;

        for (Account account : this) {
            if (!Objects.isNull(account)) {
                result ^= account.hashCode();
            }
        }

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if ((getClass() == o.getClass()) &&
                name.equals(((Individual) o).getName()) &&
                (size == ((Individual) o).getSize())) {
            int index = 0;
            for (Account account : this) {
                if (!Objects.isNull(account)) {
                    if (!account.equals(((Individual) o).get(index))) {
                        return false;
                    }
                }
                index++;
            }
            return true;
        }
        return false;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Object result = super.clone();

        Account[] resultAccounts = new Account[size];

        int index = 0;
        for (Account account : this) {
            if (account.getClass() == DebitAccount.class) {
                resultAccounts[index] = (Account) ((DebitAccount) account).clone();
            } else if (account.getClass() == CreditAccount.class) {
                resultAccounts[index] = (Account) ((CreditAccount) account).clone();
            }
            index++;
        }

        ((Individual) result).accounts = resultAccounts;

        return result;
    }

    @Override
    public int compareTo(Client o) {
        return Double.compare(totalBalance(), o.totalBalance());
    }

    @Override
    public Iterator<Account> iterator() {
        return new AccountIterator();
    }

    private class AccountIterator implements Iterator<Account> {

        private int current = 0;

        @Override
        public boolean hasNext() {
            return current < getSize();
        }

        @Override
        public Account next() {
            if (hasNext()) {
                return get(current++);
            } else {
                throw new NoSuchElementException("Iterator not found element in Individual");
            }
        }
    }
}
