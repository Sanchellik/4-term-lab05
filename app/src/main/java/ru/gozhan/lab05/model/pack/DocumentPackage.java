package ru.gozhan.lab05.model.pack;

public class DocumentPackage extends Package {

    private final String fromWhom;
    private final String toWhom;

    public DocumentPackage(String size,
                           boolean isFragile,
                           String requirements,
                           String fromWhom,
                           String toWhom) {

        super(size, isFragile, requirements);
        this.fromWhom = fromWhom;
        this.toWhom = toWhom;
    }

    public String getFromWhom() {
        return fromWhom;
    }

    public String getToWhom() {
        return toWhom;
    }

}
