package org.kiwi.kjector.sample.inheritance;

import org.kiwi.kjector.container.Container;

public class KjectorApp {
    public static void main(String[] args) {
        final Container container = Container.builder()
                .registerByName("name", "dog")
                .registerByName("nickname", "doggy")
                .register(Dog.class)
                .register(Animal.class)
                .build();

        final Dog dog = container.resolve(Dog.class);

        System.out.println(dog.name);
        System.out.println(dog.nickname);
    }
}
