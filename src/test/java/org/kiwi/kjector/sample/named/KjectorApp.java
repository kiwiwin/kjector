package org.kiwi.kjector.sample.named;

import org.kiwi.kjector.container.Container;

public class KjectorApp {
    public static void main(String[] args) {
        final Container container = Container.builder()
                .registerByName("lucy", new Girl("lucy"))
                .register(Boy.class)
                .build();

        final Boy boy = container.resolve(Boy.class);

        System.out.println(boy.showGirlFriend());
    }
}
