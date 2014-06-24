package org.kiwi.kjector.injectpoint.exception;

public class MultiConstructorInjectPointFoundException extends RuntimeException {
    private final Class klass;

    public MultiConstructorInjectPointFoundException(Class klass) {
        this.klass = klass;
    }

    @Override
    public String getMessage() {
        return String.format("Multi constructor inject point found for class: %s", klass.getName());
    }
}
