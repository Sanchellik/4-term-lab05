package ru.gozhan.lab05.model.pack;

import ru.gozhan.lab05.constant.CourierAbility;

public class HugePackage extends Package {

    private final int weight;

    public HugePackage(String size, boolean isFragile, int weight) {
        super(size, isFragile, CourierAbility.CAR_DELIVERY);
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String getType() {
        return "H";
    }

}
