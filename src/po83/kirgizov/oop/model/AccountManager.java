package po83.kirgizov.oop.model;

import java.util.NoSuchElementException;
import java.util.Objects;

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
        Objects.requireNonNull(clients, "clients array is null");

        size = clients.length;
        this.clients = clients;
    }


    @SuppressWarnings("ConstantConditions")
    public boolean add(Client client) {
        Objects.requireNonNull(client, "client is null");

        for (int i = 0; i < size; ++i) {
            if (clients[i] == null) {
                clients[i] = client;
                return true;
            }
        }
        return false;
    }

    public boolean add(int index, Client client) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("index is greater than size");
        } else if (index < 0) {
            throw new IndexOutOfBoundsException("index is less than zero");
        }

        Objects.requireNonNull(client, "client is null");

        if (clients[index] == null) {
            clients[index] = client;
            return true;
        } else {
            return false;
        }
    }

    public Client set(int index, Client client) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("index is greater than size");
        } else if (index < 0) {
            throw new IndexOutOfBoundsException("index is less than zero");
        }

        Objects.requireNonNull(client, "client is null");

        Client changedClient = clients[index];
        clients[index] = client;
        return changedClient;
    }

    public Client get(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("index is greater than size");
        } else if (index < 0) {
            throw new IndexOutOfBoundsException("index is less than zero");
        }

        return clients[index];
    }

    public Client remove(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("index is greater than size");
        } else if (index < 0) {
            throw new IndexOutOfBoundsException("index is less than zero");
        }

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
        Objects.requireNonNull(accountNumber, "accountNumber is null");

        if (isNumberNotFormatted(accountNumber)) {
            throw new InvalidAccountNumberException("account number has wrong format");
        }

        Account result = null;
        Account[] buffer;

        for (int i = 0; i < size; ++i) {
            if (clients[i] != null) {
                buffer = clients[i].getAccounts();
                for (Account account : buffer) {
                    if (account.getNumber().equals(accountNumber)) {
                        result = account;
                    }
                }
            }
        }

        if (Objects.isNull(result)) {
            throw new NoSuchElementException(String.format("account %s not found", accountNumber));
        }

        return result;
    }

    public Account removeAccount(String accountNumber) {
        Objects.requireNonNull(accountNumber, "accountNumber is null");

        if (isNumberNotFormatted(accountNumber)) {
            throw new InvalidAccountNumberException("account number has wrong format");
        }

        Account result = null;
        Account[] buffer;

        for (int i = 0; i < size; ++i) {
            buffer = clients[i].getAccounts();
            for (int j = 0; j < buffer.length; ++j) {
                if (buffer[i].getNumber().equals(accountNumber)) {
                    result = buffer[i];
                    buffer[i] = null;
                    break;
                }
            }
        }

        if (Objects.isNull(result)) {
            throw new NoSuchElementException(String.format("account %s not found", accountNumber));
        }

        return result;
    }

    public boolean remove(Client client) {
        Objects.requireNonNull(client, "client is null");

        boolean isDeleted = false;

        for (int i = 0; i < size; ++i) {
            if (!isDeleted) {
                if (clients[i].equals(client)) {
                    clients[i] = null;
                    isDeleted = true;
                }
            } else if (i < size - 1) {
                clients[i] = clients[i + 1];
            }
        }

        return isDeleted;
    }

    public int indexOf(Client client) {
        Objects.requireNonNull(client, "client is null");

        for (int i = 0; i < size; ++i) {
            if (clients[i].equals(client)) {
                return i;
            }
        }

        return -1;
    }

    public Account setAccount(String accountNumber, Account account) throws DuplicateAccountNumberException {
        Objects.requireNonNull(accountNumber, "accountNumber is null");
        Objects.requireNonNull(account, "account is null");

        if (isNumberNotFormatted(accountNumber)) {
            throw new InvalidAccountNumberException("account number has wrong format");
        }

        int ir, jr;
        ir = jr = 0;
        Account result = null;

        Account[] accounts;

        for (int i = 0; i < size; ++i) {
            if (clients[i] != null) {
                accounts = clients[i].getAccounts();
                for (int j = 0; j < accounts.length; ++j) {
                    if (accounts[j].getNumber().equals(accountNumber)) {
                        result = accounts[j];
                        ir = i;
                        jr = j;
                    }

                    if (accounts[j].getNumber().equals(account.getNumber())) {
                        throw new DuplicateAccountNumberException(
                                String.format("account with number %s already exists", account.getNumber())
                        );
                    }
                }
            }
        }

        if (Objects.isNull(result)) {
            throw new NoSuchElementException(String.format("account %s not found", accountNumber));
        }

        clients[ir].set(jr, account);

        return result;
    }

    public Client[] getDebtors() {
        int newSize = 0;
        for (int i = 0; i < size; i++) {
            if (clients[i] != null) {
                if (clients[i].getCreditAccounts().length > 0) {
                    newSize++;
                }
            }
        }

        Client[] result = new Client[newSize];

        int j = 0;
        for (int i = 0; i < size; i++) {
            if (clients[i] != null) {
                if (clients[i].getCreditAccounts().length > 0) {
                    result[j] = clients[i];
                    j++;
                }
            }
        }

        return result;
    }

    public Client[] getWickedDebtors() {
        int newSize = 0;
        for (int i = 0; i < size; i++) {
            if (clients[i] != null) {
                if (clients[i].getStatus() == ClientStatus.BAD) {
                    newSize++;
                }
            }
        }

        Client[] result = new Client[newSize];

        int j = 0;
        for (int i = 0; i < size; i++) {
            if (clients[i] != null) {
                if (clients[i].getStatus() == ClientStatus.BAD && clients[i].getCreditAccounts().length > 0) {
                    result[j] = clients[i];
                }
            }
        }

        return result;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Client c : clients) {
            if (c != null) {
                result.append(c.toString()).append("\n\n");
            }
        }
        return result.toString();
    }

    private boolean isNumberNotFormatted(String accountNumber) {
        Objects.requireNonNull(accountNumber, "accountNumber is null");

        return !(accountNumber.length() == 20 &&
                accountNumber.charAt(0) == '4' &&
                (accountNumber.charAt(1) == '4' || accountNumber.charAt(1) == '5' || accountNumber.charAt(1) == '0') &&
                accountNumber.startsWith("810", 5) &&
                !accountNumber.startsWith("0000", 9) &&
                !accountNumber.startsWith("0000000", 13));
    }
}
