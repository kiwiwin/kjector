package org.kiwi.kjector.injectpoint.sample;

import javax.inject.Inject;
import javax.inject.Named;

public class Person {

    private Animal pet;

    @Inject
    public void setPet(@Named("lucy") Animal pet) {
        this.pet = pet;
    }

    public Animal getPet() {
        return pet;
    }
}
