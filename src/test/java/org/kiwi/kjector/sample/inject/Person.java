package org.kiwi.kjector.sample.inject;

import javax.inject.Inject;

public class Person {
    private final Phone phone;
    @Inject
    private String name;

    @Inject
    public Person(Phone phone) {
        this.phone = phone;
    }

    public void showOff() {
        System.out.println("Hi, i am " + name + ". This is my phone -- " + phone.showPhone());
    }
}
