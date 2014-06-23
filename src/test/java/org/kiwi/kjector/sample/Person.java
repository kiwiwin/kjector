package org.kiwi.kjector.sample;

import javax.inject.Inject;

public class Person {
    private final Phone phone;

    @Inject
    public Person(Phone phone) {
        this.phone = phone;
    }

    public void showOff() {
        System.out.println("This is my phone -- " + phone.showPhone());
    }
}
