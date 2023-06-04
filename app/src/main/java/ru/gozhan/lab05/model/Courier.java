package ru.gozhan.lab05.model;

import java.util.ArrayList;

import ru.gozhan.lab05.constant.CourierAbility;

public class Courier {

    private final String name;
    private final String paymentAccount;
    private final ArrayList<CourierAbility> abilities;

    private ArrayList<Order> orders;

    public Courier(String name, String paymentAccount, ArrayList<CourierAbility> abilities) {
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

    public ArrayList<CourierAbility> getAbilities() {
        return abilities;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public boolean hasAbility(CourierAbility courierAbility) {
        return getAbilities().contains(courierAbility);
    }

    public void addOrder(Order order) {
        orders.add(order);
    }
}
