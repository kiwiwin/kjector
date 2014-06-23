package org.kiwi.kjector.injectpoint;

import org.kiwi.kjector.InjectPoint;
import org.kiwi.kjector.container.Container;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ParameterConstructorInjectPoint implements InjectPoint {
    private final Constructor parameterConstructor;

    public ParameterConstructorInjectPoint(Constructor parameterConstructor) {
        this.parameterConstructor = parameterConstructor;
    }

    @Override
    public Object resolveObject(Container container) {
        final Object[] parameters = container.resolveExecutable(parameterConstructor);

        try {
            return parameterConstructor.newInstance(parameters);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }
}
