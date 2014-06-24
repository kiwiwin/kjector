package org.kiwi.kjector.sample.qualifier;

import org.kiwi.kjector.container.Container;
import org.kiwi.kjector.container.sample.Painter;
import org.kiwi.kjector.injectpoint.QualifierMeta;

public class KjectorApp {
    public static void main(String[] args) {
        final Container container = Container.builder()
                .register(Farmer.class)
                .registerByQualifier(new QualifierMeta(org.kiwi.kjector.container.sample.Painter.class).meta("color", Painter.Color.RED).meta("backgroundColor", Painter.Color.BLACK), "Messi")
                .build();

        final Farmer farmer = container.resolve(Farmer.class);

        System.out.println(farmer.getUglyPainter());
    }
}
