package org.kiwi.kjector.injectpoint.sample;

import javax.inject.Inject;

public class MultiInjectConstructorSample {
    @Inject
    public MultiInjectConstructorSample() {

    }

    @Inject
    public MultiInjectConstructorSample(String parameter) {
    }
}
