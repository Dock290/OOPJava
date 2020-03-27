package po83.kirgizov.oop.model;

public interface Client {
    boolean add(Account account) throws DuplicateAccountNumberException;
    boolean add(int index, Account account) throws DuplicateAccountNumberException;

    void addCreditScores(int creditScores);

    Account set(int index, Account account) throws DuplicateAccountNumberException;

    Account get(int index);
    Account get(String accountNumber);

    int getCreditScore();

    default ClientStatus getStatus() {
        int creditScores = getCreditScore();

        if (creditScores >= 5)
        {
            return ClientStatus.PLATINUM;
        }
        else if (creditScores >= 3)
        {
            return ClientStatus.GOLD;
        }
        else if (creditScores >= 0)
        {
            return ClientStatus.GOOD;
        }
        else if (creditScores > -4)
        {
            return ClientStatus.RISKY;
        }
        else
        {
            return ClientStatus.BAD;
        }
    }

    int indexOf(Account account);

    int getSize();

    boolean hasAccount(String accountNumber);

    Account remove(int index);
    Account remove(String accountNumber);
    boolean remove(Account account);

    Account[] getAccounts();
    Account[] sortedAccountsByBalance();

    Account[] getCreditAccounts();

    double debtTotal();

    void setName(String name);
    String getName();

    double totalBalance();

    boolean isNumberNotFormatted(String accountNumber);
}
