package po83.kirgizov.oop.model;

public interface Client {
    boolean add(Account account);
    boolean add(int index, Account account);

    Account set(int index, Account account);

    Account get(int index);
    Account get(String accountNumber);

    int getSize();

    boolean hasAccount(String accountNumber);

    Account remove(int index);
    Account remove(String accountNumber);

    Account[] getAccounts();
    Account[] sortedAccountsByBalance();

    void setName(String name);
    String getName();
}
