package org.kiwi.kjector.sample.named;

import javax.inject.Inject;
import javax.inject.Named;

public class Boy {
    @Inject
    private
    @Named("lucy")
    Girl girlfriend;


    public String showGirlFriend() {
        return girlfriend.getName() + " is my girlfriend";
    }
}
