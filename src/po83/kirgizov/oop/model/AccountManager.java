package po83.kirgizov.oop.model;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.regex.Pattern;

//todo комментарии из Individual применимы сюда
// Добавил System.arraycopy в методы getClients, remove(Client client), getDebtors, getWickedDebtors
// Изменил метод sortedClientsByBalance. Не удаляет null-элементы, использует System.arraycopy
public class AccountManager {
    private static int DEF_SIZE = 16;

    private Client[] clients;
    int size;

    public AccountManager() {
        //todo вынести числа в константу
        // Сделал
        clients = new Client[DEF_SIZE];
        size = DEF_SIZE;
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


    public boolean add(Client client) throws NullPointerException {
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
        Client[] result = new Client[size];

        System.arraycopy(clients, 0, result, 0, size);

        return result;
    }

    public Client[] sortedClientsByBalance() {
        Client[] result = new Client[size];
        System.arraycopy(clients, 0, result, 0, size);

        Client buffer;
        boolean isSorted = false;

        while (!isSorted) {
            isSorted = true;
            for (int i = 0; i < size - 1; i++) {
                if (result[i + 1] != null) {
                    if (result[i] == null || result[i].totalBalance() > result[i + 1].totalBalance()) {
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

    public Account getAccount(String accountNumber) {
        Objects.requireNonNull(accountNumber, "accountNumber is null");

        if (isNumberNotFormatted(accountNumber)) {
            throw new InvalidAccountNumberException("account number has wrong format");
        }

        Account[] buffer;

        for (int i = 0; i < size; ++i) {
            if (clients[i] != null) {
                buffer = clients[i].getAccounts();
                for (Account account : buffer) {
                    if (account != null) {
                        if (account.getNumber().equals(accountNumber)) {
                            return account;
                        }
                    }
                }
            }
        }

        throw new NoSuchElementException(String.format("account %s not found", accountNumber));
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

        for (int i = 0; i < size; ++i) {
            if (clients[i].equals(client)) {
                clients[i] = null;
                if (size - i + 1 >= 0) {
                    System.arraycopy(clients, i + 1, clients, i, size - i + 1);
                }
                return true;
            }
        }

        return false;
    }

    public int indexOf(Client client) {
        Objects.requireNonNull(client, "client is null");

        for (int i = 0; i < size; ++i) {
            if (clients[i] != null) {
                if (clients[i].equals(client)) {
                    return i;
                }
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
        //todo что с именованием переменных? для чего они служат?
        // Исправил названия переменных, немного изменил метод

        int indexOfClient, indexOfAccount;
        indexOfClient = indexOfAccount = 0;
        Account result = null;

        Account[] bufferAccounts;

        for (int i = 0; i < size; ++i) {
            if (clients[i] != null) {
                bufferAccounts = clients[i].getAccounts();
                for (int j = 0; j < bufferAccounts.length; ++j) {
                    if (bufferAccounts[j].getNumber().equals(account.getNumber())) {
                        throw new DuplicateAccountNumberException(
                                String.format("account with number %s already exists", account.getNumber())
                        );
                    }

                    if (bufferAccounts[j].getNumber().equals(accountNumber)) {
                        result = bufferAccounts[j];
                        indexOfClient = i;
                        indexOfAccount = j;
                        break;
                    }
                }
            }
        }

        if (Objects.isNull(result)) {
            throw new NoSuchElementException(String.format("account %s not found", accountNumber));
        }

        clients[indexOfClient].set(indexOfAccount, account);

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
                    System.arraycopy(clients, i, result, j, 1);
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
                    System.arraycopy(clients, i, result, j, 1);
                    j++;
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
        //todo паттерн?
        // Добавил
        return !Pattern.matches("^4[045]\\d{3}810\\d(?!0{4})\\d{4}(?!0{7})\\d{7}$", accountNumber);
    }
}
