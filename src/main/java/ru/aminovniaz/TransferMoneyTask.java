package ru.aminovniaz;

import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class TransferMoneyTask implements Runnable {

    private static final int TRANSACTION_COUNT = 30;

    List<Account> accountList;

    AtomicInteger counter;

    final Logger LOGGER;

    public TransferMoneyTask(List<Account> accountList, AtomicInteger counter, Logger LOGGER) {
        this.accountList = accountList;
        this.counter = counter;
        this.LOGGER = LOGGER;
    }

    @Override
    public void run() {
        while (true) {
            ThreadLocalRandom random = ThreadLocalRandom.current();

            int sleepTime = random.nextInt(1000, 2000);
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                LOGGER.error(String.format("%s was interrupted.", Thread.currentThread().getName()));
                throw new RuntimeException(e);
            }

            if (counter.get() >= TRANSACTION_COUNT) {
                break;
            }

            if (accountList.size() < 2) {
                continue;
            }

            Account a1 = accountList.remove(random.nextInt(accountList.size()));
            Account a2 = accountList.remove(random.nextInt(accountList.size()));
            int money = 0;
            if (a1.hasMoney()) {
                money = a1.takeMoney();
                a2.addMoney(money);
                counter.incrementAndGet();
            }
            accountList.add(a1);
            accountList.add(a2);

            int totalMoney = accountList.stream().mapToInt(Account::getMoney).sum();
            String balances = accountList.stream().map(a -> a.getId() + "=" + a.getMoney() + " ").collect(Collectors.joining());

            LOGGER.info(String.format("%s. №%s transfer money: %s from %s to %s. Total money: %s. Balances: %s.", Thread.currentThread().getName(), counter, money, a1.getId(), a2.getId(), totalMoney, balances));
            System.out.printf("%s. №%s transfer money: %s from %s to %s. Total money: %s. Balances: %s.\n", Thread.currentThread().getName(), counter, money, a1.getId(), a2.getId(), totalMoney, balances);

            if (counter.get() >= TRANSACTION_COUNT) {
                break;
            }
        }
    }
}
