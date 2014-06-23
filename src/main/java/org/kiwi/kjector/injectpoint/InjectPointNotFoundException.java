package org.kiwi.kjector.injectpoint;

public class InjectPointNotFoundException extends RuntimeException {
    private final Class klass;

    public InjectPointNotFoundException(Class klass) {
        this.klass = klass;
    }
}
