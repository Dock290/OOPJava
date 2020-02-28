package po83.kirgizov.oop;

import po83.kirgizov.oop.model.*;

public class Test {
    public static void main(String[] args) {
        lab1tests();

        System.out.println("Готово!");
    }

    public static void lab1tests() {

        Account a1 = new Account();
        Account a2 = new Account("2000", 500);

        System.out.println(a1.getNumber() + " " + a1.getBalance());
        System.out.println(a2.getNumber() + " " + a2.getBalance());

        a1.setNumber("2354");
        a1.setBalance(56.4);

        System.out.println(a1.getNumber() + " " + a1.getBalance());

        Individual i1 = new Individual();
        Individual i2 = new Individual(3);

        i2.set(1, a1);
        i2.add(a2);

        Individual i3 = new Individual(i2.getAccounts());

        Account[] accounts;

        i1.add(new Account("5637", 954));

        accounts = i1.getAccounts();
        System.out.println("\nIndividual 1:");
        for (Account account3 : accounts) {
            System.out.println(account3.getNumber() + " " + account3.getBalance());
        }

        accounts = i2.getAccounts();
        System.out.println("\nIndividual 2:");
        for (Account account2 : accounts) {
            System.out.println(account2.getNumber() + " " + account2.getBalance());
        }

        accounts = i3.getAccounts();
        System.out.println("\nIndividual 3:");
        for (Account account1 : accounts) {
            System.out.println(account1.getNumber() + " " + account1.getBalance());
        }

        System.out.println("Удаление аккаунта 5637 из i1 и добавление его i2");

        i2.add(i1.remove("5637"));
        i3.set(0 , new Account("2001", 500));

        accounts = i1.getAccounts();
        System.out.println("\nIndividual 1:");
        for (Account element : accounts) {
            System.out.println(element.getNumber() + " " + element.getBalance());
        }

        accounts = i2.getAccounts();
        System.out.println("\nIndividual 2:");
        for (Account item : accounts) {
            System.out.println(item.getNumber() + " " + item.getBalance());
        }

        accounts = i3.getAccounts();
        System.out.println("\nIndividual 3:");
        for (Account value : accounts) {
            System.out.println(value.getNumber() + " " + value.getBalance());
        }

        System.out.println();

        System.out.println("Account Manager:");

        AccountManager accountManager = new AccountManager();
        accountManager.add(i1);
        accountManager.add(i2);
        accountManager.add(i3);

        Individual[] individuals;
        individuals = accountManager.getIndividuals();
        for (int i = 0; i < individuals.length; ++i) {
            System.out.println("Individual " + (i + 1) + ":");
            accounts = individuals[i].getAccounts();
            for (Account account : accounts) {
                System.out.println(account.getNumber() + " " + account.getBalance());
            }
        }

        System.out.println();
    }
}
