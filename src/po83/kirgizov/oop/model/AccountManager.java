package po83.kirgizov.oop.model;

public class AccountManager {
    private Client[] clients;
    int size;

    public AccountManager() {
        clients = new Client[16];
        size = 16;
    }

    public AccountManager(int size) {
        clients = new Client[size];
        this.size = size;
    }

    public AccountManager(Client[] clients) {
        size = clients.length;
        this.clients = clients;
    }


    public boolean add(Client client) {
        for (int i = 0; i < size; ++i) {
            if (clients[i] == null) {
                clients[i] = client;
                return true;
            }
        }
        return false;
    }

    public boolean add(int index, Client client) {
        if (clients[index] == null) {
            clients[index] = client;
            return true;
        } else {
            return false;
        }
    }

    public Client get(int index) {
        return clients[index];
    }

    public Client set(int index, Client client) {
        Client changedClient = clients[index];
        clients[index] = client;
        return changedClient;
    }

    public Client remove(int index) {
        Client changedClient = clients[index];
        clients[index] = null;
        return changedClient;
    }

    int getSize() {
        return size;
    }

    public Client[] getClients() {
        int resSize = 0;

        for (int i = 0; i < size; ++i) {
            if (clients[i] != null) {
                resSize++;
            }
        }

        Client[] result = new Client[resSize];
        int j = 0;

        for (int i = 0; i < size; ++i) {
            if (clients[i] != null) {
                result[j++] = clients[i];
            }
        }

        return result;
    }

    public Client[] sortedClientsByBalance() {
        Client[] result = getClients();
        int[] resultBalance = new int[result.length];

        for (int i = 0; i < result.length; ++i) {
            for (Account account : result[i].getAccounts()) {
                resultBalance[i] += account.getBalance();
            }
        }

        if (result.length == 0) {
            return null;
        }

        Client bufferClient;
        int bufferBalance;
        boolean isSorted = false;

        while (!isSorted) {
            for (int i = 0; i < result.length - 1; i++) {
                isSorted = true;
                if (resultBalance[i] > resultBalance[i + 1]) {
                    isSorted = false;

                    bufferClient = result[i];
                    result[i] = result[i + 1];
                    result[i + 1] = bufferClient;

                    bufferBalance = resultBalance[i];
                    resultBalance[i] = resultBalance[i + 1];
                    resultBalance[i + 1] = bufferBalance;
                }
            }
        }

        return result;
    }

    public Account getAccount(String accountNumber) {
        Account[] buffer;

        for (int i = 0; i < size; ++i) {
            buffer = clients[i].getAccounts();
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
            buffer = clients[i].getAccounts();
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
            for (int j = 0; j < clients[i].getSize(); ++j) {
                if (clients[i].getAccounts()[j].getNumber().equals(accountNumber)) {
                    result = clients[i].getAccounts()[j];
                    clients[i].getAccounts()[j] = account;
                    return result;
                }
            }
        }

        return null;
    }
}
