package ru.gozhan.lab05.model;

import java.util.ArrayList;

public class Courier {

    private final String name;
    private final String paymentAccount;
    private final String abilities;

    private ArrayList<Order> orders;

    public Courier(String name, String paymentAccount, String abilities) {
        this.name = name;
        this.paymentAccount = paymentAccount;
        this.abilities = abilities;
        this.orders = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getPaymentAccount() {
        return paymentAccount;
    }

    public String getAbilities() {
        return abilities;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void addOrder(Order order) {
        this.orders.add(order);
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }
}
