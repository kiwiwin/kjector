package org.kiwi.kjector.sample.inheritance;

import javax.inject.Inject;
import javax.inject.Named;

public class Animal {
    @Inject
    @Named("name")
    protected String name;
}
