package org.kiwi.kjector.container.exception;

public class NoSuitableQualifierBeanRegisteredException extends RuntimeException {
    private final String objectName;

    public NoSuitableQualifierBeanRegisteredException(String objectName) {
        this.objectName = objectName;
    }
}
