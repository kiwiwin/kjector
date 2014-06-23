package org.kiwi.kjector.injectpoint.sample;

import javax.inject.Inject;
import javax.inject.Named;

public class Farmer {
    @Inject
    private
    @Named("duck")
    Animal animal;

    public Animal getAnimal() {
        return this.animal;
    }
}
