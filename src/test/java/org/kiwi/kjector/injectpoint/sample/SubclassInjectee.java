package org.kiwi.kjector.injectpoint.sample;

import javax.inject.Inject;
import javax.inject.Named;

public class SubclassInjectee extends SuperclassInjectee {
    protected Dummy subclassField;

    @Inject
    @Named("subclassField")
    public void setSubclassField(Dummy subclassField) {
        if (subclassField == null) {
            throw new RuntimeException("should inject superclass first");
        }
        this.subclassField = subclassField;
    }
}
