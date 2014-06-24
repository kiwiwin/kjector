package org.kiwi.kjector.sample.inheritance;

import javax.inject.Inject;
import javax.inject.Named;

public class Dog extends Animal {
    @Inject
    @Named("nickname")
    String nickname;
}
