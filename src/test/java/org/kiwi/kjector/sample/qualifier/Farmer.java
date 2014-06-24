package org.kiwi.kjector.sample.qualifier;

import javax.inject.Inject;

public class Farmer {
    @Inject
    @org.kiwi.kjector.container.sample.Painter(color = org.kiwi.kjector.container.sample.Painter.Color.RED, backgroundColor = org.kiwi.kjector.container.sample.Painter.Color.BLACK)
    String uglyPainter;

    public Farmer() {

    }

    public String getUglyPainter() {
        return uglyPainter;
    }
}
