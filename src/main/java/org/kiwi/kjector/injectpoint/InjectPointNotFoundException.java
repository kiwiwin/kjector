package org.kiwi.kjector.injectpoint;

public class InjectPointNotFoundException extends RuntimeException {
    private final Class klass;

    public InjectPointNotFoundException(Class klass) {
        this.klass = klass;
    }

    @Override
    public String getMessage() {
        return String.format("No inject point found for class: %s", klass.getName());
    }
}
