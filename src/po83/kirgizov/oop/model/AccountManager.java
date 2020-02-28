package po83.kirgizov.oop.model;

public class AccountManager {
    private Individual[] individuals;
    int size;

    public AccountManager() {
        individuals = new Individual[16];
        size = 16;
    }

    public AccountManager(int size) {
        individuals = new Individual[size];
        this.size = size;
    }

    public AccountManager(Individual[] individuals) {
        size = individuals.length;
        this.individuals = new Individual[size];
        for (int i = 0; i < size; ++i) {
            this.individuals[i] = new Individual(individuals[i].getAccounts());
        }
    }


    public boolean add(Individual individual) {
        for (int i = 0; i < size; ++i) {
            if (individuals[i] == null) {
                individuals[i] = individual;
                return true;
            }
        }
        return false;
    }

    public boolean add(int index, Individual individual) {
        if (individuals[index] == null) {
            individuals[index] = individual;
            return true;
        } else {
            return false;
        }
    }

    public Individual get(int index) {
        return individuals[index];
    }

    public Individual set(int index, Individual account) {
        Individual changedAccount = individuals[index];
        individuals[index] = account;
        return changedAccount;
    }

    public Individual remove(int index) {
        Individual changedAccount = individuals[index];
        individuals[index] = null;
        return changedAccount;
    }

    int getSize() {
        return size;
    }

    public Individual[] getIndividuals() {
        int resSize = 0;

        for (int i = 0; i < size; ++i) {
            if (individuals[i] != null) {
                resSize++;
            }
        }

        Individual[] result = new Individual[resSize];
        int j = 0;

        for (int i = 0; i < size; ++i) {
            if (individuals[i] != null) {
                result[j++] = new Individual(individuals[i].getAccounts());
            }
        }

        return result;
    }

    public Individual[] sortedIndividualsByBalance() {
        Individual[] result = getIndividuals();

        if (result.length == 0) {
            return null;
        }

        Individual buffer;
        boolean isSorted = false;

        while (!isSorted) {
            for (int i = 0; i < result.length - 1; i++) {
                isSorted = true;
                if (result[i].totalBalance() > result[i + 1].totalBalance()) {
                    isSorted = false;

                    buffer = result[i];
                    result[i] = result[i + 1];
                    result[i + 1] = buffer;
                }
            }
        }

        return result;
    }

    public Account getAccount(String accountNumber) {
        Account[] buffer;

        for (int i = 0; i < size; ++i) {
            buffer = individuals[i].getAccounts();
            for (int j = 0; j < buffer.length; ++j) {
                if (buffer[i].getNumber().equals(accountNumber)) {
                    return buffer[i];
                }
            }
        }

        return null;
    }

    public Account removeAccount(String accountNumber) {
        Account[] buffer;

        for (int i = 0; i < size; ++i) {
            buffer = individuals[i].accounts;
            for (int j = 0; j < buffer.length; ++j) {
                if (buffer[i].getNumber().equals(accountNumber)) {
                    Account result = buffer[i];
                    buffer[i] = null;
                    return result;
                }
            }
        }

        return null;
    }

    public Account setAccount(String accountNumber, Account account) {
        Account result;

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < individuals[i].getSize(); ++j) {
                if (individuals[i].getAccounts()[j].getNumber().equals(accountNumber)) {
                    result = individuals[i].getAccounts()[j];
                    individuals[i].getAccounts()[j] = account;
                    return result;
                }
            }
        }

        return null;
    }
}
