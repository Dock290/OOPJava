package po83.kirgizov.oop.model;

public class Individual implements Client {
    private String name;
    private int size;
    protected Account[] accounts;
    private int creditScores;

    public Individual() {
        accounts = new Account[16];
        size = 16;
        name = "";
        creditScores = 0;
    }

    public Individual(int size) {
        accounts = new Account[size];
        this.size = size;
        name = "";
        creditScores = 0;
    }

    public Individual(Account[] accounts) {
        size = accounts.length;
        this.accounts = new Account[size];

        for (int i = 0; i < size; ++i) {
            this.accounts[i] = new DebitAccount(accounts[i].getNumber(), accounts[i].getBalance());
        }

        name = "";
        creditScores = 0;
    }


    public boolean add(Account account) {
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

    public boolean add(int index, Account account) {
        if (accounts[index] == null) {
            accounts[index] = account;
            return true;
        } else {
            return false;
        }
    }

    public void addCreditScores(int creditScores) {
        this.creditScores += creditScores;
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

    public int getCreditScores() {
        return creditScores;
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
        Account changedAccount = null;

        if (index < size) {
            changedAccount = accounts[index];
            accounts[index] = null;

            if (size - index + 1 >= 0) System.arraycopy(accounts, index + 1, accounts, index, size - index + 1);
        }

        return changedAccount;
    }

    public Account remove(String accountNumber) {
        Account changedAccount = null;
        for (int i = 0; i < size; ++i) {
            if (accounts[i] != null) {
                if (accounts[i].getNumber().equals(accountNumber)) {
                    changedAccount = accounts[i];
                    accounts[i] = null;
                }
            }

            if (changedAccount != null && i < size - 1) {
                accounts[i] = accounts[i + 1];
            }
        }
        return changedAccount;
    }

    public int getSize() {
        return size;
    }

    public Account[] getAccounts() {
        Account[] result = new Account[size];

        for (int i = 0; i < size; ++i) {
            if (accounts[i] != null) {
                result[i] = accounts[i];
            } else {
                result[i] = new DebitAccount();
            }
        }

        return result;
    }

    public Account[] sortedAccountsByBalance() {
        Account[] result = new Account[size];

        for (int i = 0; i < size; ++i) {
            if (accounts[i] != null) {
                result[i] = accounts[i];
            }
        }

        Account buffer;
        boolean isSorted = false;

        while (!isSorted) {
            for (int i = 0; i < size; i++) {
                isSorted = true;
                if (result[i] != null) {
                    if (result[i].getBalance() > result[i + 1].getBalance()) {
                        isSorted = false;

                        buffer = result[i];
                        result[i] = result[i + 1];
                        result[i + 1] = buffer;
                    }
                }
            }
        }

        for (int i = 0; i < size; ++i) {
            if (result[i] == null) {
                result[i] = new DebitAccount();
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

    public void setName(String name) {
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
}
