package org.kiwi.kjector.container.sample;

import javax.inject.Inject;

public class FieldInjectSample {
    @Inject
    private String name;

    public String getName() {
        return name;
    }
}
