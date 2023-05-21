package ru.gozhan.lab05.model;

import java.util.ArrayList;

public class Courier {

    private final String name;
    private final String paymentAccount;
    private final String features;

    private final ArrayList<Order> orders;

    public Courier(String name, String paymentAccount, String features) {
        this.name = name;
        this.paymentAccount = paymentAccount;
        this.features = features;
        this.orders = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getPaymentAccount() {
        return paymentAccount;
    }

    public String getFeatures() {
        return features;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void addOrder(Order order) {
        this.orders.add(order);
    }

}
