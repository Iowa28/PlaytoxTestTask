package ru.aminovniaz;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) {
        List<Account> accountList = new CopyOnWriteArrayList<>();
        accountList.add(new Account());
        accountList.add(new Account());
        accountList.add(new Account());
        accountList.add(new Account());

        AtomicInteger counter = new AtomicInteger();
        Thread thread1 = new Thread(new TransferMoneyTask(accountList, counter));
        thread1.start();
        Thread thread2 = new Thread(new TransferMoneyTask(accountList, counter));
        thread2.start();
    }
}