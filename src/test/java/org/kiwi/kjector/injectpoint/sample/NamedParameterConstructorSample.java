package org.kiwi.kjector.injectpoint.sample;

import javax.inject.Inject;
import javax.inject.Named;

public class NamedParameterConstructorSample {
    private final String name;

    @Inject
    public NamedParameterConstructorSample(@Named("easy") String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}
