package org.kiwi.kjector.container.exception;

public class NoSuitableBeanRegisteredException extends RuntimeException {
    private final Class klass;

    public NoSuitableBeanRegisteredException(Class klass) {
        this.klass = klass;
    }
}
