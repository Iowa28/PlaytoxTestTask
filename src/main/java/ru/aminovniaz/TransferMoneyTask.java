package ru.aminovniaz;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class TransferMoneyTask implements Runnable {

    private static int TRANSACTION_COUNT = 30;

    List<Account> accountList;

    AtomicInteger counter;

    public TransferMoneyTask(List<Account> accountList, AtomicInteger counter) {
        this.accountList = accountList;
        this.counter = counter;
    }

    @Override
    public void run() {
        while (true) {
            ThreadLocalRandom random = ThreadLocalRandom.current();

            int sleepTime = random.nextInt(1000, 2000);
            try {
                //System.out.println(Thread.currentThread().getName() + " went to sleep for " + sleepTime);
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                System.err.printf("%s. Thread was interrupted\n", Thread.currentThread().getName());
                throw new RuntimeException(e);
            }

            //System.out.println(Thread.currentThread().getName() + " woke up");

            if (counter.get() >= TRANSACTION_COUNT) {
                break;
            }

            if (accountList.size() < 2) {
                continue;
            }

            Account a1 = accountList.remove(random.nextInt(accountList.size()));
            Account a2 = accountList.remove(random.nextInt(accountList.size()));
            if (a1.hasMoney()) {
                int money = a1.takeMoney();
                a2.addMoney(money);
                counter.incrementAndGet();
                System.out.printf("%s. №%s transferred %s money from %s to %s\n", Thread.currentThread().getName(), counter, money, a1.getId(), a2.getId());
            }
            accountList.add(a1);
            accountList.add(a2);

            int totalMoney = accountList.stream().mapToInt(Account::getMoney).sum();
            String balances = accountList.stream().map(a -> a.getId() + "=" + a.getMoney() + " ").collect(Collectors.joining());
            System.out.printf("%s. Total money: %s. Balances: %s\n", Thread.currentThread().getName(), totalMoney, balances);

            if (counter.get() >= TRANSACTION_COUNT) {
                break;
            }
        }
    }
}
