package ru.aminovniaz;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class Account {

    private UUID id;

    private int money;

    public Account() {
        id = UUID.randomUUID();
        money = 10000;
    }

    public boolean hasMoney() {
        return money > 0;
    }

    public int takeMoney() {
        int randomMoney = ThreadLocalRandom.current().nextInt(money);
        money -= randomMoney;
        return randomMoney;
    }

    public void addMoney(int money) {
        this.money += money;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", money=" + money +
                '}';
    }
}
