package ru.gozhan.lab05.model.pack;

public abstract class Package {

    protected String size;

    protected boolean isFragile;

    protected String requirements;

    public Package(String size, boolean isFragile, String requirements) {
        this.size = size;
        this.isFragile = isFragile;
        this.requirements = requirements;
    }

    public String getSize() {
        return size;
    }

    public boolean isFragile() {
        return isFragile;
    }

    public String getRequirements() {
        return requirements;
    }

    public abstract String getType();

}
