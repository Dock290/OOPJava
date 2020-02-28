package po83.kirgizov.oop.model;

public class Individual {
    protected Account[] accounts;
    int size;

    public Individual() {
        accounts = new Account[16];
        size = 16;
    }

    public Individual(int size) {
        accounts = new Account[size];
        this.size = size;
    }

    public Individual(Account[] accounts) {
        size = accounts.length;
        this.accounts = new Account[size];
        for (int i = 0; i < size; ++i) {
            this.accounts[i] = new Account(accounts[i].getNumber(), accounts[i].getBalance());
        }
    }


    public boolean add(Account account) {
        for (int i = 0; i < size; ++i) {
            if (accounts[i] == null) {
                accounts[i] = account;
                return true;
            }
        }
        return false;
    }

    public boolean add(int index, Account account) {
        if (accounts[index] == null) {
            accounts[index] = account;
            return true;
        } else {
            return false;
        }
    }

    public Account get(int index) {
        return accounts[index];
    }

    public Account get(String accountNumber) {
        for (int i = 0; i < size; ++i) {
            if (accounts[i].getNumber().equals(accountNumber)) {
                return accounts[i];
            }
        }
        return null;
    }

    public boolean hasAccount(String accountNumber) {
        for (int i = 0; i < size; ++i) {
            if (accounts[i].getNumber().equals(accountNumber)) {
                return true;
            }
        }
        return false;
    }

    public Account set(int index, Account account) {
        Account changedAccount = accounts[index];
        accounts[index] = account;
        return changedAccount;
    }

    public Account remove(int index) {
        Account changedAccount = accounts[index];
        accounts[index] = null;
        return changedAccount;
    }

    public Account remove(String accountNumber) {
        Account changedAccount;
        for (int i = 0; i < size; ++i) {
            if (accounts[i].getNumber().equals(accountNumber)) {
                changedAccount = accounts[i];
                accounts[i] = null;
                return changedAccount;
            }
        }
        return null;
    }

    int getSize() {
        return size;
    }

    public Account[] getAccounts() {
        int resSize = 0;

        for (int i = 0; i < size; ++i) {
            if (accounts[i] != null) {
                resSize++;
            }
        }

        Account[] result = new Account[resSize];
        int j = 0;

        for (int i = 0; i < size; ++i) {
            if (accounts[i] != null) {
                result[j++] = new Account(accounts[i].getNumber(), accounts[i].getBalance());
            }
        }

        return result;
    }

    public Account[] sortedAccountsByBalance() {
        Account[] result = getAccounts();

        if (result.length == 0) {
            return null;
        }

        Account buffer;
        boolean isSorted = false;

        while (!isSorted) {
            for (int i = 0; i < result.length - 1; i++) {
                isSorted = true;
                if (result[i].getBalance() > result[i + 1].getBalance()) {
                    isSorted = false;

                    buffer = result[i];
                    result[i] = result[i + 1];
                    result[i + 1] = buffer;
                }
            }
        }

        return result;
    }

    public double totalBalance() {
        Account[] buffer = getAccounts();
        double result = 0;

        for (Account account : buffer) {
            result += account.getBalance();
        }

        return result;
    }
}
