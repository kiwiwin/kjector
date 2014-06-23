package org.kiwi.kjector.sample;

public class Phone {
    private final String brand;
    private final String number;

    public Phone(String brand, String number) {
        this.brand = brand;
        this.number = number;
    }

    public String showPhone() {
        return "Brand: " + brand + ", Number: " + number;
    }
}
