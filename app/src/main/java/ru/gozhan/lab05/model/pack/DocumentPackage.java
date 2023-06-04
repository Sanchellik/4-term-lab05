package ru.gozhan.lab05.model.pack;

import ru.gozhan.lab05.constant.CourierAbility;

public class DocumentPackage extends Package {

    private final String fromWhom;
    private final String toWhom;

    public DocumentPackage(String size,
                           boolean isFragile,
                           String fromWhom,
                           String toWhom) {

        super(size, isFragile, CourierAbility.DOCS_DELIVERY);
        this.fromWhom = fromWhom;
        this.toWhom = toWhom;
    }

    public String getFromWhom() {
        return fromWhom;
    }

    public String getToWhom() {
        return toWhom;
    }

    @Override
    public String getType() {
        return "D";
    }

}
