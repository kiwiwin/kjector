package org.kiwi.kjector.injectpoint;

import org.kiwi.kjector.InjectPoint;
import org.kiwi.kjector.container.Container;

import java.lang.reflect.InvocationTargetException;

public class DefaultConstructorInjectPoint implements InjectPoint {
    private final Class klass;

    public DefaultConstructorInjectPoint(Class klass) {
        this.klass = klass;
    }

    @Override
    public Object resolveObject(Container container) {
        try {
            return klass.getConstructor().newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
}
