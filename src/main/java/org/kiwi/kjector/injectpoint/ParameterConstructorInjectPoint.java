package org.kiwi.kjector.injectpoint;

import org.kiwi.kjector.InjectPoint;
import org.kiwi.kjector.container.Container;

import javax.inject.Inject;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class ParameterConstructorInjectPoint implements InjectPoint {
    private final Class klass;

    public ParameterConstructorInjectPoint(Class klass) {
        this.klass = klass;
    }

    @Override
    public Object resolveObject(Container container) {
        final Constructor constructor = getParameterConstructor();
        final Object[] parameters = resolveParameters(container, constructor);

        try {
            return constructor.newInstance(parameters);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Object[] resolveParameters(Container container, Constructor constructor) {
        return Arrays.asList(constructor.getParameterTypes()).stream().map(container::resolve).toArray();
    }

    private Constructor getParameterConstructor() {
        return Arrays.asList(klass.getConstructors()).stream().filter(cons -> cons.getAnnotation(
                Inject.class) != null).findFirst().get();
    }
}
