package org.kiwi.kjector.sample;

import org.kiwi.kjector.container.Container;

public class KjectorApplication {
    public static void main(String[] args) {
        final Container container = Container.builder()
                .register(Person.class)
                .bind(new Phone("iPhone", "13880007000"), Phone.class)
                .build();

        final Person person = container.resolve(Person.class);

        person.showOff();
    }
}
