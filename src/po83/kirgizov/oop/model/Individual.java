package po83.kirgizov.oop.model;

import java.util.*;
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

    @Override
    public boolean remove(Object o) {
        Objects.requireNonNull(o, "object is null");

        for (int i = 0; i < size(); ++i)
        {
            if (!Objects.isNull(accounts[i])) {
                if (accounts[i].equals(o)) {
                    System.arraycopy(accounts, i + 1, accounts, i, size() - i - 1);
                    accounts[size() - i - 1] = null;
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        Objects.requireNonNull(c, "collection is null");

        boolean result = false;
        for (Object o : c) {
            for (Account account : this) {
                if (Objects.isNull(account)) {
                    if (Objects.isNull(o)) {
                        result = true;
                    }
                } else if (account.equals(o)) {
                    result = true;
                    break;
                }
            }

            if (!result) {
                break;
            }
        }
        return result;
    }

    @Override
    public boolean addAll(Collection<? extends Account> c) {
        Objects.requireNonNull(c, "collection is null");

        for (Account account : c) {
            for (int i = 0; i < size; i++) {
                if (Objects.isNull(accounts[i])) {
                    accounts[i] = account;
                    break;
                }

                if (i == size - 1) {
                    Account[] newAccounts = new Account[size * 2];
                    System.arraycopy(accounts, 0, newAccounts, 0, size);
                    accounts = newAccounts;
                    size *= 2;
                }
            }
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Objects.requireNonNull(c, "collection is null");

        int index;
        boolean result = false;
        for (Object o : c) {
            index = 0;
            for (int i = 0; i < size; i++) {
                if (Objects.isNull(accounts[i])) {
                    if (Objects.isNull(o)) {
                        result = true;
                        break;
                    }
                } else if (accounts[i].equals(o)) {
                    System.arraycopy(accounts, i + 1, accounts, i, size() - i - 1);
                    accounts[size() - i - 1] = null;
                    result = true;
                    break;
                }
                index++;
            }
        }
        return result;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Objects.requireNonNull(c, "collection is null");

        boolean result = false;

        int index = 0;
        boolean isEquals;
        boolean isChanged;
        for (Account account : this) {
            isEquals = false;
            isChanged = false;
            for (Object o : c) {
                if (Objects.isNull(account)) {
                    if (Objects.isNull(o)) {
                        isEquals = true;
                        break;
                    }
                } else if (account.equals(o)) {
                    isEquals = true;
                    break;
                } else {
                    isChanged = true;
                }
            }

            if (isEquals) {
                index++;
            } else if (isChanged) {
                System.arraycopy(accounts, index + 1, accounts, index, size() - index - 1);
                accounts[size() - index - 1] = null;
                result = true;
            }
        }
        return result;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; ++i) {
            accounts[i] = null;
        }
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
        if (!Objects.isNull(o) && getClass() == o.getClass() &&
                name.equals(((Individual) o).getName()) &&
                (size == ((Individual) o).size())) {
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
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        for (Account account : this) {
            if (!Objects.isNull(account)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean contains(Object o) {
        Objects.requireNonNull(o, "object is null");

        for (Account account : this) {
            if (!Objects.isNull(account)) {
                return account.equals(o);
            }
        }
        return false;
    }

    @Override
    public Iterator<Account> iterator() {
        return new AccountIterator();
    }

    @Override
    public boolean add(Account account) {
        Objects.requireNonNull(account, "account is null");

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

    private class AccountIterator implements Iterator<Account> {
        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < size();
        }

        @Override
        public Account next() {
            if (hasNext()) {
                return get(index++);
            } else {
                throw new NoSuchElementException("Iterator not found element in Individual");
            }
        }
    }
}
