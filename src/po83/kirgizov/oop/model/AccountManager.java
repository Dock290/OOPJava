package po83.kirgizov.oop.model;

import java.util.*;
import java.util.regex.Pattern;

public class AccountManager implements Iterable<Client> {
    private static int DEF_SIZE = 16;

    private Client[] clients;
    int size;

    public AccountManager() {
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

    public List<Client> sortedClientsByBalance() {
        ArrayList<Client> result = new ArrayList<>(Arrays.asList(clients));
        Collections.sort(result);
        return result;
    }

    public Account getAccount(String accountNumber) {
        Objects.requireNonNull(accountNumber, "accountNumber is null");

        if (isNumberNotFormatted(accountNumber)) {
            throw new InvalidAccountNumberException("account number has wrong format");
        }

        for (Client client : this) {
            if (!Objects.isNull(client)) {
                for (Account account : client) {
                    if (!Objects.isNull(account)) {
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

        for (Client client : this) {
            if (!Objects.isNull(client)) {
                for (Account account : client) {
                    if (!Objects.isNull(account)) {
                        if (account.getNumber().equals(accountNumber)) {
                            result = account;
                            client.remove(account);
                            break;
                        }
                    }
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

        int index = 0;
        for (Client buffer: this) {
            if (Objects.isNull(buffer)) {
                return true;
            }
            else
            {
                if (buffer.equals(client)) {
                    System.arraycopy(clients, index + 1, clients, index, size - index + 1);
                    return true;
                }
            }
            index++;
        }

        return false;
    }

    public int indexOf(Client client) {
        Objects.requireNonNull(client, "client is null");

        int index = 0;
        for (Client buffer : this) {
            if (!Objects.isNull(buffer)) {
                if (buffer.equals(client)) {
                    return index;
                }
            }
            index++;
        }

        return -1;
    }

    public Account setAccount(String accountNumber, Account account) throws DuplicateAccountNumberException {
        Objects.requireNonNull(accountNumber, "accountNumber is null");
        Objects.requireNonNull(account, "account is null");

        if (isNumberNotFormatted(accountNumber)) {
            throw new InvalidAccountNumberException("account number has wrong format");
        }

        int indexOfClient, indexOfAccount;
        indexOfClient = indexOfAccount = 0;
        Account result = null;

        for (Client client : this) {
            if (!Objects.isNull(client)) {
                for (Account bufferAccount : client) {
                    if (bufferAccount.getNumber().equals(account.getNumber())) {
                        throw new DuplicateAccountNumberException(
                                String.format("account with number %s already exists", account.getNumber())
                        );
                    }
                    indexOfAccount++;

                    if (bufferAccount.getNumber().equals(accountNumber)) {
                        result = bufferAccount;
                        break;
                    }
                }
            }
            indexOfClient++;
        }

        if (Objects.isNull(result)) {
            throw new NoSuchElementException(String.format("account %s not found", accountNumber));
        }

        clients[indexOfClient].set(indexOfAccount, account);

        return result;
    }

    public Set<Client> getDebtors() {
        HashSet<Client> result = new HashSet<>();

        for (Client client : this) {
            if (!Objects.isNull(client)) {
                if (client.getCreditAccounts().size() > 0) {
                    result.add(client);
                }
            }
        }

        return result;
    }

    public Set<Client> getWickedDebtors() {
        HashSet<Client> result = new HashSet<>();

        for (Client client : this) {
            if (!Objects.isNull(client)) {
                if (client.getStatus() == ClientStatus.BAD && client.getCreditAccounts().size() > 0) {
                    result.add(client);
                }
            }
        }

        return result;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Client client : this) {
            if (!Objects.isNull(client)) {
                result.append(client.toString()).append("\n");
            }
        }
        return result.toString();
    }

    private boolean isNumberNotFormatted(String accountNumber) {
        Objects.requireNonNull(accountNumber, "accountNumber is null");
        return !Pattern.matches("^4[045]\\d{3}810\\d(?!0{4})\\d{4}(?!0{7})\\d{7}$", accountNumber);
    }

    @Override
    public Iterator<Client> iterator() {
        return new ClientIterator();
    }

    private class ClientIterator implements Iterator<Client> {

        private int current = 0;

        @Override
        public boolean hasNext() {
            return current < getSize();
        }

        @Override
        public Client next() {
            if (hasNext()) {
                return get(current++);
            } else {
                throw new NoSuchElementException("Iterator not found element in AccountManager");
            }
        }
    }
}
