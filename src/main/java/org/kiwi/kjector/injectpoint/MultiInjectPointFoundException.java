package org.kiwi.kjector.injectpoint;

public class MultiInjectPointFoundException extends RuntimeException {
    private final Class klass;

    public MultiInjectPointFoundException(Class klass) {
        this.klass = klass;
    }
}
