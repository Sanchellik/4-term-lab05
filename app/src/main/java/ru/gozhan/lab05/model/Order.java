package ru.gozhan.lab05.model;

import java.util.Objects;

import ru.gozhan.lab05.model.pack.Package;

public class Order {

    private final Company company;
    private final Package pack;

    private final String departureAddress;
    private final String deliveryAddress;

    private final int price;

    public Order(Company company, Package pack, String departureAddress, String deliveryAddress, int price) {
        this.company = company;
        this.pack = pack;
        this.departureAddress = departureAddress;
        this.deliveryAddress = deliveryAddress;
        this.price = price;
    }

    public Company getCompany() {
        return company;
    }

    public Package getPack() {
        return pack;
    }

    public String getDepartureAddress() {
        return departureAddress;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return getPrice() == order.getPrice() && Objects.equals(getCompany(), order.getCompany())
                && Objects.equals(getPack(), order.getPack())
                && Objects.equals(getDepartureAddress(), order.getDepartureAddress())
                && Objects.equals(getDeliveryAddress(), order.getDeliveryAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCompany(), getPack(), getDepartureAddress(), getDeliveryAddress(), getPrice());
    }
}
