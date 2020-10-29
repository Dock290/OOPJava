package po83.kirgizov.oop.model;

import java.util.*;
import java.util.regex.Pattern;

public class Entity implements Client {
    private static int DEF_SIZE = 0;
    private static int DEF_CREDIT_SCORE = 0;

    private String name;
    private int size;
    private Node head, tail;
    private int creditScore;

    public Entity(String name) {
        Objects.requireNonNull(name, "name is null");
        this.name = name;
        head = null;
        tail = null;
        size = DEF_SIZE;
        creditScore = DEF_CREDIT_SCORE;
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

        if (tail == head)
        {
            tail = node;
            tail.next = head;
            head.next = tail;
        }
        else
        {
            tail.next = node;
            tail = tail.next;
            tail.next = head;
        }
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

        Node result = current.next;
        current.next = current.next.next;
        size--;

        if (index == size)
        {
            tail = current;
        }

        return result;
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

        return addNode(index, new Node(account));
    }

    public void addCreditScores(int creditScores) {
        this.creditScore += creditScores;
    }

    public Account set(int index, Account account) throws DuplicateAccountNumberException {
        Objects.requireNonNull(account, "account is null");

        if (isNumberMatchFound(account.getNumber())) {
            throw new DuplicateAccountNumberException("account number " + account.getNumber() + " already exists");
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

    public int getCreditScore() {
        return creditScore;
    }

    public int indexOf(Account account) {
        Objects.requireNonNull(account, "account is null");

        int index = 0;
        for (Account buffer : this) {
            if (buffer.equals(account)) {
                return index;
            }
            index++;
        }

        return -1;
    }

    public Account remove(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException("index is greater than size");
        } else if (index < 0) {
            throw new IndexOutOfBoundsException("index is less than zero");
        }

        return deleteNode(index).value;
    }

    public Account remove(String accountNumber) {
        Objects.requireNonNull(accountNumber, "accountNumber is null");

        if (isNumberNotFormatted(accountNumber)) {
            throw new InvalidAccountNumberException("accountNumber has wrong format");
        }

        Account result = null;

        int index = 0;
        for (Account account : this) {
            if (account.getNumber().equals(accountNumber)) {
                result = remove(index);
                break;
            }
            index++;
        }

        if (Objects.isNull(result)) {
            throw new NoSuchElementException("account with number " + accountNumber + " not found");
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

    private boolean isNumberMatchFound(String accountNumber) {
        for (Account account : this) {
            if (account.getNumber().equals(accountNumber)) {
                return true;
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
        StringBuilder result = new StringBuilder("Entity:\n" + "name: " + name + "\ncreditScore: " + creditScore + "\n");

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
        if (!Objects.isNull(o) && this.getClass() == o.getClass() &&
                name.equals(((Entity) o).getName()) &&
                (size == ((Entity) o).size())) {
            int index = 0;
            for (Account account : this) {
                if (!Objects.isNull(account)) {
                    if (!account.equals(((Entity) o).get(index))) {
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

        ((Entity) result).head = ((Entity) result).tail = new Node();
        ((Entity) result).head.next = ((Entity) result).tail;
        ((Entity) result).tail.next = ((Entity) result).head;
        ((Entity) result).size = 0;

        Account[] accounts = this.toArray();

        for (Account account : this) {
            ((Entity) result).addNode(new Node(account));
        }

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
        return size == 0;
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
    public boolean add(Account account) {
        Objects.requireNonNull(account, "account is null");
        return addNode(new Node(account));
    }

    @Override
    public boolean remove(Object o) {
        Objects.requireNonNull(o, "object is null");

        Node current = head.next;

        int index = 0;
        for (Account buffer : this) {
            if (!Objects.isNull(buffer)) {
                if (buffer.equals(o)) {
                    remove(index);
                    return true;
                }
            }
            index++;
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
            addNode(new Node(account));
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
            for (Account account : this) {
                if (Objects.isNull(account)) {
                    if (Objects.isNull(o)) {
                        deleteNode(index);
                        result = true;
                        break;
                    }
                } else if (account.equals(o)) {
                    deleteNode(index);
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
                deleteNode(index);
                result = true;
            }
        }
        return result;
    }

    @Override
    public void clear() {
        while (!isEmpty()) {
            deleteNode(0);
        }
    }

    @Override
    public Iterator<Account> iterator() {
        return new AccountIterator();
    }

    private class AccountIterator implements Iterator<Account> {
        private int index = 0;
        Node current = head.next;

        @Override
        public boolean hasNext() {
            return index < size();
        }

        @Override
        public Account next() {
            if (hasNext()) {
                Account result;
                result = current.value;
                current = current.next;
                index++;
                return result;
            } else {
                throw new NoSuchElementException("Iterator not found element in Individual");
            }
        }
    }


    private class Node {
        Account value;
        Node next;

        public Node() {

        }

        public Node(Account value) {
            this.value = value;
        }
    }
}
