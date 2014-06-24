package org.kiwi.kjector.injectpoint;

import org.kiwi.kjector.InjectPoint;
import org.kiwi.kjector.container.Container;
import org.kiwi.kjector.injectpoint.exception.ResolveObjectException;

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
            throw new ResolveObjectException();
        }
    }
}
