package org.kiwi.kjector.injectpoint;

import org.kiwi.kjector.InjectPoint;
import org.kiwi.kjector.container.Container;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class DefaultConstructorInjectPoint implements InjectPoint {
    private final Constructor defaultConstructor;

    public DefaultConstructorInjectPoint(Constructor defaultConstructor) {
        this.defaultConstructor = defaultConstructor;
    }

    @Override
    public Object resolveObject(Container container) {
        try {
            return defaultConstructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new ResolveObjectException();
        }
    }
}
