package po83.kirgizov.oop.model;

import java.util.NoSuchElementException;
import java.util.Objects;

public class Entity implements Client {
    private String name;
    private int size;
    private Node head, tail;
    private int creditScore;

    public Entity(String name) {
        Objects.requireNonNull(name, "name is null");
        //todo числа в константы
        this.name = name;
        head = null;
        tail = null;
        size = 0;
        creditScore = 0;
    }

    public Entity(String name, Account[] accounts) {
        Objects.requireNonNull(name, "name is null");
        Objects.requireNonNull(accounts, "accounts array is null");

        this.name = name;
        size = accounts.length;

        head = new Node();

        Node current = head;
        for (Account account : accounts) {
            current.next = new Node(account);
            current = current.next;
        }

        tail = current;
        tail.next = head;

        creditScore = 0;
    }

    private boolean addNode(Node node) {
        if (head == null) {
            head = tail = new Node();
        }

        tail.next = node;
        tail = tail.next;
        tail.next = head;
        size++;

        return true;
    }

    private boolean addNode(int index, Node node) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("index is less than zero");
        } else if (index >= size) {
            throw new IndexOutOfBoundsException("index is greater than size");
        }

        Node current = head.next;
        for (int i = 0; i < index; ++i) {
            current = current.next;
        }

        node.next = current.next;
        current.next = node;

        size++;

        return true;
    }

    private Node getNode(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("index is greater than size");
        } else if (index < 0) {
            throw new IndexOutOfBoundsException("index is less than zero");
        }

        Node current = head.next;
        for (int i = 0; i < index; ++i) {
            current = current.next;
        }

        return current;
    }

    private Node deleteNode(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("index is greater than size");
        } else if (index < 0) {
            throw new IndexOutOfBoundsException("index is less than zero");
        }

        Node current = head;
        for (int i = 0; i < index; ++i) {
            current = current.next;
        }

        Node buffer = current.next;
        current.next = buffer.next;
        size--;

        return buffer;
    }

    private Account setNode(int index, Account account) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("index is greater than size");
        } else if (index < 0) {
            throw new IndexOutOfBoundsException("index is less than zero");
        }

        Objects.requireNonNull(account, "account is null");

        Node current = head.next;
        for (int i = 0; i <= index; ++i) {
            current = current.next;
        }

        Account buffer = current.value;
        current.value = account;

        return buffer;
    }

    public boolean add(Account account) throws DuplicateAccountNumberException {
        Objects.requireNonNull(account, "account is null");

        if (head == null) {
            head = tail = new Node();
            head.next = tail;
        }

        Node current = head.next;
        for (int i = 0; i < size; ++i) {
            //todo повторяющееся сравнение в отдельный метод
            if (current.value.getNumber().equals(account.getNumber())) {
                throw new DuplicateAccountNumberException("account number " + account.getNumber() + " already exists");
            }
            current = current.next;
        }

        return addNode(new Node(account));
    }

    public boolean add(int index, Account account) throws DuplicateAccountNumberException {
        if (index >= size) {
            throw new IndexOutOfBoundsException("index is greater than size");
        } else if (index < 0) {
            throw new IndexOutOfBoundsException("index is less than zero");
        }

        Objects.requireNonNull(account, "account is null");

        Node current = head.next;
        for (int i = 0; i < size; ++i) {
            if (current.value.getNumber().equals(account.getNumber())) {
                throw new DuplicateAccountNumberException("account number " + account.getNumber() + " already exists");
            }
            current = current.next;
        }

        return addNode(index, new Node(account));
    }

    public void addCreditScores(int creditScores) {
        this.creditScore += creditScores;
    }

    public Account set(int index, Account account) throws DuplicateAccountNumberException {
        Objects.requireNonNull(account, "account is null");

        Node current = head.next;
        for (int i = 0; i < size; ++i) {
            if (current.value.getNumber().equals(account.getNumber())) {
                throw new DuplicateAccountNumberException("account number " + account.getNumber() + " already exists");
            }
            current = current.next;
        }

        return setNode(index, account);
    }

    public Account get(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("index is greater than size");
        } else if (index < 0) {
            throw new IndexOutOfBoundsException("index is less than zero");
        }

        Node result = getNode(index);
        return result == null ? null : result.value;
    }

    public Account get(String accountNumber) {
        Objects.requireNonNull(accountNumber, "accountNumber is null");

        if (isNumberNotFormatted(accountNumber)) {
            throw new InvalidAccountNumberException("accountNumber has wrong format");
        }

        Account result = null;

        Node current = head.next;

        for (int i = 0; i < size; ++i) {
            if (current.value.getNumber().equals(accountNumber)) {
                result = current.value;
                break;
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

        Node current = head.next;

        for (int i = 0; i < size; ++i) {
            if (current.value.equals(account)) {
                return i;
            }
            current = current.next;
        }

        return -1;
    }

    public int getSize() {
        return size;
    }

    public boolean hasAccount(String accountNumber) {
        Objects.requireNonNull(accountNumber, "accountNumber is null");

        if (isNumberNotFormatted(accountNumber)) {
            throw new InvalidAccountNumberException("accountNumber has wrong format");
        }

        Node current = head.next;

        for (int i = 0; i < size; ++i) {
            if (current.value.getNumber().equals(accountNumber)) {
                return true;
            }
            current = current.next;
        }

        return false;
    }

    public Account remove(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("index is greater than size");
        } else if (index < 0) {
            throw new IndexOutOfBoundsException("index is less than zero");
        }

        Node current = head;

        return deleteNode(index).value;
    }

    public Account remove(String accountNumber) {
        Objects.requireNonNull(accountNumber, "accountNumber is null");

        if (isNumberNotFormatted(accountNumber)) {
            throw new InvalidAccountNumberException("accountNumber has wrong format");
        }

        Account result = null;

        Node current = head.next;

        for (int i = 0; i < size; ++i) {
            if (current.next.value.getNumber().equals(accountNumber)) {
                result = deleteNode(i).value;
                break;
            }
            current = current.next;
        }

        if (Objects.isNull(result)) {
            throw new NoSuchElementException("account with number " + accountNumber + " not found");
        }

        return result;
    }

    public boolean remove(Account account) {
        Objects.requireNonNull(account, "account is null");

        Node current = head.next;

        for (int i = 0; i < size; ++i) {
            if (current.value.equals(account)) {
                deleteNode(i);
                return true;
            }
        }

        return false;
    }

    public Account[] getAccounts() {
        Node current = head.next;

        Account[] result = new Account[size];
        for (int i = 0; i < size; ++i) {
            result[i] = current.value;
            current = current.next;
        }

        return result;
    }

    public Account[] sortedAccountsByBalance() {
        Node current = head.next;

        Account[] result = new Account[size];
        for (int i = 0; i < size; ++i) {
            result[i] = current.value;
            current = current.next;
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

    public Account[] getCreditAccounts() {
        Node current = head.next;

        int newSize = 0;
        for (int i = 0; i < size; ++i) {
            if (current.value.getClass() == CreditAccount.class) {
                newSize++;
            }
            current = current.next;
        }

        Account[] result = new Account[newSize];

        current = head.next;

        int j = 0;
        for (int i = 0; i < size; ++i) {
            if (current.value.getClass() == CreditAccount.class) {
                result[j] = current.value;
                j++;
            }
            current = current.next;
        }

        return result;
    }

    public double debtTotal() {
        double result = 0;

        Node current = head.next;

        for (int i = 0; i < size; ++i) {
            if (current.value.getClass() == CreditAccount.class) {
                result += current.value.getBalance();
            }
            current = current.next;
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
        Node current;
        double result = 0;

        if (size != 0) {
            current = head.next;
            for (int i = 0; i < size; ++i) {
                result += current.value.getBalance();
                current = current.next;
            }
        }

        return result;
    }

    @Override
    public boolean isNumberNotFormatted(String accountNumber) {
        Objects.requireNonNull(accountNumber, "accountNumber is null");
        //todo паттерн?
        return !(accountNumber.length() == 20 &&
                accountNumber.charAt(0) == '4' &&
                (accountNumber.charAt(1) == '0' || accountNumber.charAt(1) == '4' || accountNumber.charAt(1) == '5') &&
                accountNumber.startsWith("810", 5) &&
                !accountNumber.startsWith("0000", 9) &&
                !accountNumber.startsWith("0000000", 13));
    }

    @Override
    public String toString() {
        Node current;

        StringBuilder result = new StringBuilder("Entity:\n" + "name: " + name + "\ncreditScore: " + creditScore + "\n");

        if (size != 0) {
            current = head.next;
            for (int i = 0; i < size; ++i) {
                result.append(current.value.toString()).append("\n");
                current = current.next;
            }
        }
        result.append("total: ").append(totalBalance());
        return result.toString();
    }

    @Override
    public int hashCode() {
        Node current;
        int result = name.hashCode() ^ creditScore;

        if (size != 0) {
            current = head.next;
            for (int i = 0; i < size; ++i) {
                result ^= current.value.hashCode();
                current = current.next;
            }
        }

        return result;
    }

    @Override
    public boolean equals(Object o) {
        Node current;

        if ((this.getClass() == o.getClass()) && name.equals(((Entity) o).getName()) && (size == ((Entity) o).getSize())) {
            if (size != 0) {
                current = head.next;
                for (int i = 0; i < size; i++) {
                    if (!current.value.equals(((Entity) o).get(i))) {
                        return false;
                    }
                    current = current.next;
                }
                return true;
            }
            else
            {
                return  true;
            }
        }
        return false;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Object result = super.clone();

        ((Entity) result).head = ((Entity) result).tail = new Node();
        ((Entity) result).head.next = ((Entity) result).tail;
        ((Entity) result).tail.next = ((Entity) result).head;
        ((Entity) result).size = 0;

        Account[] accounts = getAccounts();

        for (int i = 0; i < ((Entity) result).getSize(); ++i) {
            ((Entity) result).addNode(new Node(accounts[i]));
        }
        return result;
    }
}

class Node {
    Account value;
    Node next;

    public Node() {
    }

    public Node(Account value) {
        this.value = value;
    }
}
