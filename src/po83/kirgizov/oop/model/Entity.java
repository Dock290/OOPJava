package po83.kirgizov.oop.model;

public class Entity implements Client {
    String name;
    int size;
    Node head, tail;

    public Entity(String name) {
        this.name = name;
        head = null;
        tail = null;
        size = 0;
    }

    public Entity(String name, Account[] accounts)
    {
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
        if (index <= size) {
            return false;
        }

        Node current = head;
        for (int i = 0; i < index; ++i){
            current = current.next;
        }

        node.next = current.next;
        current.next = node;

        size++;

        return true;
    }

    private Node getNode(int index) {
        if (index >= size) {
            return null;
        }

        Node current = head.next;
        for (int i = 0; i < index; ++i){
            current = current.next;
        }

        return current.next;
    }

    private Node deleteNode(int index) {
        Node current = head;
        for (int i = 0; i < index; ++i){
            current = current.next;
        }

        Node buffer = current.next;
        current.next = buffer.next;
        size--;

        return buffer;
    }

    private Account setNode(int index, Account account)
    {
        if (index >= size)
        {
            return null;
        }

        Node current = head.next;
        for (int i = 0; i < index; ++i){
            current = current.next;
        }

        current = current.next;

        Account buffer = current.value;
        current.value = account;

        return buffer;
    }

    public boolean add(Account account) {
        return addNode(new Node(account));
    }

    public boolean add(int index, Account account) {
        return addNode(index, new Node(account));
    }

    public Account set(int index, Account account) {
        return setNode(index, account);
    }

    public Account get(int index) {
        if (index >= size)
        {
            return null;
        }

        Node current = head.next;
        for (int i = 0; i < index; ++i)
        {
            current = current.next;
        }

        return current.value;
    }

    public Account get(String accountNumber) {
        Node current = head.next;

        for (int i = 0; i < size; ++i)
        {
            if (current.value.getNumber().equals(accountNumber))
            {
                return current.value;
            }
        }

        return null;
    }

    public int getSize() {
        return size;
    }

    public boolean hasAccount(String accountNumber) {
        Node current = head.next;

        for (int i = 0; i < size; ++i)
        {
            if (current.value.getNumber().equals(accountNumber))
            {
                return true;
            }
            current = current.next;
        }

        return false;
    }

    public Account remove(int index) {
        if (index >= size)
        {
            return null;
        }

        Node current = head;

        for (int i = 0; i < index - 1; ++i)
        {
            current = current.next;
        }

        Node buffer = current.next;

        current.next = buffer.next;
        buffer.next = null;

        size--;

        return buffer.value;
    }

    public Account remove(String accountNumber) {
        Node current = head.next;

        for (int i = 0; i < size; ++i)
        {
            if (current.next.value.getNumber().equals(accountNumber))
            {
                Node buffer = current.next;
                current.next = buffer.next;
                buffer.next = null;
                size--;
                return buffer.value;
            }
            current = current.next;
        }

        return null;
    }

    public Account[] getAccounts() {
        Node current = head.next;

        Account[] result = new Account[size];
        for (int i = 0; i < size; ++i)
        {
            result[i] = current.value;
            current = current.next;
        }

        return result;
    }

    public Account[] sortedAccountsByBalance() {
        Node current = head.next;

        Account[] result = new Account[size];
        for (int i = 0; i < size; ++i)
        {
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

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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
