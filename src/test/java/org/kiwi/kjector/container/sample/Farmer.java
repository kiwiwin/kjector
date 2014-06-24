package org.kiwi.kjector.container.sample;


import javax.inject.Inject;

public class Farmer {
    @Inject
    @Painter(color = Painter.Color.RED, backgroundColor = Painter.Color.BLACK)
    String uglyPainter;

    public Farmer() {

    }

    public String getUglyPainter() {
        return uglyPainter;
    }
}