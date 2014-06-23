package org.kiwi.kjector.injectpoint;

import org.kiwi.kjector.InjectPoint;
import org.kiwi.kjector.container.Container;

import javax.inject.Named;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ParameterConstructorInjectPoint implements InjectPoint {
    private final Constructor parameterConstructor;

    public ParameterConstructorInjectPoint(Constructor parameterConstructor) {
        this.parameterConstructor = parameterConstructor;
    }

    @Override
    public Object resolveObject(Container container) {
        final Object[] parameters = resolveParameters(container, parameterConstructor);

        try {
            return parameterConstructor.newInstance(parameters);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Object[] resolveParameters(Container container, Constructor constructor) {
        final Annotation[][] parameterAnnotations = constructor.getParameterAnnotations();
        final Class[] parameterTypes = constructor.getParameterTypes();

        List<Object> resolvedParameters = new ArrayList<>();

        for (int parameterIndex = 0; parameterIndex < parameterTypes.length; parameterIndex++) {
            if (parameterAnnotations[parameterIndex].length > 0) {
                final Named namedAnnotation = (Named) parameterAnnotations[parameterIndex][0];
                resolvedParameters.add(container.resolveByName(namedAnnotation.value()));
            } else {
                resolvedParameters.add(container.resolve(parameterTypes[parameterIndex]));
            }
        }
        return resolvedParameters.toArray();
    }
}
