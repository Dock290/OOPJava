package po83.kirgizov.oop;

import po83.kirgizov.oop.model.*;

public class Test {
    public static void main(String[] args) {
        //lab1tests();
        //lab2tests();
        lab3tests();

        System.out.println("Готово!");
    }

    /*public static void lab1tests() {

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
    }*/

    /*public static void lab2tests() {
        DebitAccount a1 = new DebitAccount();
        a1.setNumber("1234");
        a1.setBalance(7533);

        System.out.println("A1");
        System.out.println(a1.getNumber() + " " + a1.getBalance());

        Individual i1 = new Individual(5);
        i1.add(new DebitAccount("2345", 45.6));
        i1.add(3, new DebitAccount("2346", 65.0));
        i1.add(new DebitAccount("76", 2.0));

        Account[] accounts;

        System.out.println("\nIndividual 1:");
        accounts = i1.getAccounts();
        for (Account element : accounts) {
            System.out.println(element.getNumber() + " " + element.getBalance());
        }

        Individual i2 = new Individual();
        i2.add(i1.set(1, a1));

        System.out.println("\nIndividual 1:");
        accounts = i1.getAccounts();
        for (Account element : accounts) {
            System.out.println(element.getNumber() + " " + element.getBalance());
        }

        System.out.println("\nIndividual 2:");
        accounts = i2.getAccounts();
        for (Account element : accounts) {
            System.out.println(element.getNumber() + " " + element.getBalance());
        }

        System.out.println();

        Entity e0 = new Entity("Entity Name_0 Test", i1.getAccounts());
        accounts = i1.getAccounts();
        System.out.println(e0.getName());
        for (Account element : accounts) {
            System.out.println(element.getNumber() + " " + element.getBalance());
        }
        System.out.println();

        Entity e1 = new Entity("Entity Name_1 Test");
        Entity e2 = new Entity("Entity Name_2 Test");

        e1.add(new DebitAccount("1000", 0));

        accounts = i1.getAccounts();
        for (Account element : accounts) {
            e1.add(element);
        }

        System.out.println(e1.getName());

        accounts = e1.getAccounts();
        for (Account element : accounts) {
            System.out.println(element.getNumber() + " " + element.getBalance());
        }

        Account a3 = e1.set(0, new DebitAccount("2000", 90));
        e2.add(a3);

        AccountManager am = new AccountManager();
        am.add(new Entity("E1"));
        am.add(new Entity("E2"));
        am.add(new Individual(2));

        Client[] clients = am.getClients();
        clients[2].setName("I1");

        clients[0].add(new DebitAccount("0001", 8734));
        clients[0].add(new DebitAccount("0002", 97));
        clients[0].add(new DebitAccount("0003", 456));

        clients[1].add(new DebitAccount("0011", 13));
        clients[1].add(new DebitAccount("0012", 43));
        clients[1].add(new DebitAccount("0013", 32.5));

        clients[2].add(new DebitAccount("0021", 42.4));
        clients[2].add(new DebitAccount("0022", 54));
        clients[2].add(new DebitAccount("0023", 1.09));

        System.out.println();

        clients = am.getClients();
        for (Client client : clients) {
            System.out.println(client.getName());
            for (Account account : client.getAccounts()) {
                System.out.println("Number: " + account.getNumber() + "\tBalance: " + account.getBalance());
            }
            System.out.println();
        }

        Entity entity = new Entity("E_C");
        entity.add(new DebitAccount("0101", 54.5));
        entity.add(new DebitAccount("0102", 875.3434));
        entity.add(new DebitAccount("0103", 98.1));
        Client c = am.set(0, entity);

        System.out.println("Client");

        accounts = c.getAccounts();
        for (Account element : accounts) {
            System.out.println(element.getNumber() + " " + element.getBalance());
        }

        System.out.println();

        clients = am.getClients();

        clients[1].remove(1);
        clients[1].remove("0013");

        clients[2].remove("0021");

        clients = am.getClients();
        for (Client client : clients) {
            System.out.println(client.getName());
            for (Account account : client.getAccounts()) {
                System.out.println("Number: " + account.getNumber() + "\tBalance: " + account.getBalance());
            }
            System.out.println();
        }
    }*/

    public static void lab3tests() {
        DebitAccount da1 = new DebitAccount();

        System.out.println("\nDebit Account 1\n");
        System.out.println(da1.getNumber() + " " + da1.getBalance());

        da1.setNumber("1000");
        da1.setBalance(1000);

        System.out.println("\nDebit Account 1\n");
        System.out.println(da1.getNumber() + " " + da1.getBalance());

        DebitAccount da2 = new DebitAccount("1001", 200.15);

        System.out.println("\nDebit Account 2\n");
        System.out.println(da2.getNumber() + " " + da2.getBalance());

        CreditAccount ca1 = new CreditAccount();

        System.out.println("\nCredit Account 1\n");
        System.out.println(ca1.getNumber() + " " + ca1.getBalance() + " " + ca1.getAnnualPercentageRate());

        ca1.setNumber("2000");
        ca1.setBalance(2100);
        ca1.setAnnualPercentageRate(25);

        System.out.println("\nCredit Account 1\n");
        System.out.println(ca1.getNumber() + " " + ca1.getBalance() + " " + ca1.getAnnualPercentageRate());

        CreditAccount ca2 = new CreditAccount("2001", 100.15, 75);

        System.out.println("\nCredit Account 2\n");
        System.out.println(ca2.getNumber() + " " + ca2.getBalance() + " " + ca2.getAnnualPercentageRate());

        // Test Individual & Entity

        Account[] accounts;

        Individual i1 = new Individual();
        i1.setName("Dmitriy");

        i1.add(ca1);
        i1.add(new DebitAccount("3000", 87.4));
        i1.add(ca2);
        i1.add(new DebitAccount("3001", 27.908));
        i1.add(new DebitAccount("3002", 93));
        i1.add(new CreditAccount("5673", -456.3, 50));
        i1.addCreditScores(-10);

        accounts = i1.getAccounts();

        System.out.println("\nIndividual 1\n");
        for (Account a : accounts) {
            if (a.getClass() == DebitAccount.class) {
                System.out.println(a.getNumber() + " " + a.getBalance());
            } else if (a.getClass() == CreditAccount.class) {
                System.out.println(a.getNumber() + " " + a.getBalance() + " " + ((CreditAccount) a).getAnnualPercentageRate());
            }
        }
        System.out.println("Status: " + i1.getStatus() + " Scores: " + i1.getCreditScores());

        Individual i2 = new Individual(2);
        i2.setName("Vasiliy");

        i2.add(da1);
        i2.add(da2);

        accounts = i2.getAccounts();

        System.out.println("\nIndividual 2\n");
        for (Account a : accounts) {
            if (a.getClass() == DebitAccount.class) {
                System.out.println(a.getNumber() + " " + a.getBalance());
            } else if (a.getClass() == CreditAccount.class) {
                System.out.println(a.getNumber() + " " + a.getBalance() + " " + ((CreditAccount) a).getAnnualPercentageRate());
            }
        }
        System.out.println("Status: " + i2.getStatus() + " Scores: " + i2.getCreditScores());

        Entity e1 = new Entity("Anatoly");

        e1.add(new CreditAccount("7374", 320, 10));
        e1.add(new CreditAccount("7734", 520, 10));
        e1.add(new CreditAccount("7373", 380, 10));
        e1.addCreditScores(5);

        accounts = e1.getAccounts();

        System.out.println("\nEntity 1\n");
        for (Account a : accounts) {
            if (a.getClass() == DebitAccount.class) {
                System.out.println(a.getNumber() + " " + a.getBalance());
            } else if (a.getClass() == CreditAccount.class) {
                System.out.println(a.getNumber() + " " + a.getBalance() + " " + ((CreditAccount) a).getAnnualPercentageRate());
            }
        }
        System.out.println("Status: " + e1.getStatus() + " Scores: " + e1.getCreditScores());

        // Test AccountManager

        Client[] clients;

        AccountManager am = new AccountManager();

        am.add(i1);
        am.add(i2);
        am.add(e1);

        clients = am.getClients();

        System.out.println("\nAccount Manager: all:");
        for (Client client : clients) {
            System.out.println(client.getName());
            accounts = client.getAccounts();
            for (Account a : accounts) {
                if (a.getClass() == DebitAccount.class) {
                    System.out.println(a.getNumber() + " " + a.getBalance());
                } else if (a.getClass() == CreditAccount.class) {
                    System.out.println(a.getNumber() + " " + a.getBalance() + " " + ((CreditAccount) a).getAnnualPercentageRate());
                }
            }
            System.out.println();
        }

        clients = am.getDebtors();

        System.out.println("Account Manager: debtors:");
        for (Client client : clients) {
            System.out.println(client.getName());
            accounts = client.getAccounts();
            for (Account a : accounts) {
                if (a.getClass() == DebitAccount.class) {
                    System.out.println(a.getNumber() + " " + a.getBalance());
                } else if (a.getClass() == CreditAccount.class) {
                    System.out.println(a.getNumber() + " " + a.getBalance() + " " + ((CreditAccount) a).getAnnualPercentageRate());
                }
            }
            System.out.println();
        }

        clients = am.getWickedDebtors();

        System.out.println("Account Manager: wicked debtors:");
        for (Client client : clients) {
            System.out.println(client.getName());
            accounts = client.getAccounts();
            for (Account a : accounts) {
                if (a.getClass() == DebitAccount.class) {
                    System.out.println(a.getNumber() + " " + a.getBalance());
                } else if (a.getClass() == CreditAccount.class) {
                    System.out.println(a.getNumber() + " " + a.getBalance() + " " + ((CreditAccount) a).getAnnualPercentageRate());
                }
            }
            System.out.println();
        }

        System.out.println();
    }
}
