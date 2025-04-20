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

        final int accountCount = 4;
        final int threadCount = 2;

        List<Account> accountList = new CopyOnWriteArrayList<>();
        for (int i = 0; i < accountCount; i++) {
            accountList.add(new Account());
        }

        AtomicInteger counter = new AtomicInteger();
        for (int i = 0; i < threadCount; i++) {
            Thread thread = new Thread(new TransferMoneyTask(accountList, counter, LOGGER));
            thread.start();
        }
    }
}