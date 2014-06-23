package org.kiwi.kjector.sample;

import javax.inject.Inject;

public class Person {
    private final Phone phone;

    @Inject
    public Person(Phone phone) {
        this.phone = phone;
    }

    public void showOff() {
        if (phone != null) {
            System.out.println("I have an iPhone5s");
        } else {
            System.out.println("I will have an iPhone6");
        }
    }
}
