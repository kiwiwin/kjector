package org.kiwi.kjector.injectpoint.sample;

import javax.inject.Inject;

public class MethodInjectAnnotationSample {
    private String name;

    @Inject
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
