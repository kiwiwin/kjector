package org.kiwi.kjector.container;

public class NoSuitableBeanRegisteredException extends RuntimeException {
    private final Class klass;

    public NoSuitableBeanRegisteredException(Class klass) {
        this.klass = klass;
    }
}
