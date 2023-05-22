package ru.gozhan.lab05.model.pack;

public class SmallPackage extends Package {

    public SmallPackage(String size, boolean isFragile) {
        super(size, isFragile, "Nope");
    }

    @Override
    public String getType() {
        return "S";
    }
}
