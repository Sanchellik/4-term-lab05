package ru.gozhan.lab05.model.pack;

import ru.gozhan.lab05.constant.CourierAbility;

public abstract class Package {

    protected String size;

    protected boolean isFragile;

    protected CourierAbility requirements;

    public Package(String size, boolean isFragile, CourierAbility requirements) {
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

    public CourierAbility getRequirements() {
        return requirements;
    }

    public abstract String getType();

}
