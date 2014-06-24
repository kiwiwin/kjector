package org.kiwi.kjector.injectpoint.sample;

import javax.inject.Inject;
import javax.inject.Named;

public class SuperclassInjectee {
    protected Dummy superclassField;

    @Inject
    @Named("superclassField")
    public void setSuperclassField(Dummy superclassField) {
        this.superclassField = superclassField;
    }
}
