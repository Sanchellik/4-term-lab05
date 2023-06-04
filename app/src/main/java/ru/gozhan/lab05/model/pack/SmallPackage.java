package ru.gozhan.lab05.model.pack;

public class SmallPackage extends Package {

    public SmallPackage(String size, boolean isFragile) {
        super(size, isFragile, null);
    }

    @Override
    public String getType() {
        return "S";
    }

}
