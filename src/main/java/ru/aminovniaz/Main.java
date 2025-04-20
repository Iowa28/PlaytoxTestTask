package ru.aminovniaz;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.log4j.Logger;

public class Main {

    static {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh-mm-ss");
        System.setProperty("current_date", dateFormat.format(new Date()));
    }

    public static void main(String[] args) {
        final Logger LOGGER = Logger.getLogger(Main.class);

        List<Account> accountList = new CopyOnWriteArrayList<>();
        accountList.add(new Account());
        accountList.add(new Account());
        accountList.add(new Account());
        accountList.add(new Account());

        AtomicInteger counter = new AtomicInteger();
        Thread thread1 = new Thread(new TransferMoneyTask(accountList, counter, LOGGER));
        thread1.start();
        Thread thread2 = new Thread(new TransferMoneyTask(accountList, counter, LOGGER));
        thread2.start();
    }
}