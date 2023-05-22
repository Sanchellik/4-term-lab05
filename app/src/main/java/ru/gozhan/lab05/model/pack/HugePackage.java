package ru.gozhan.lab05.model.pack;

public class HugePackage extends Package {

    private final int weight;

    public HugePackage(String size, boolean isFragile, int weight) {
        super(size, isFragile, "car");
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
