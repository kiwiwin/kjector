package org.kiwi.kjector.container.exception;

public class NoSuitableNamedBeanRegisteredException extends RuntimeException {
    private final String objectName;

    public NoSuitableNamedBeanRegisteredException(String objectName) {
        this.objectName = objectName;
    }
}
