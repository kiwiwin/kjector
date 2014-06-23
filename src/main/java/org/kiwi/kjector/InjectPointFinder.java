package org.kiwi.kjector;

import org.kiwi.kjector.injectpoint.DefaultConstructorInjectPoint;
import org.kiwi.kjector.injectpoint.InjectPointNotFoundException;
import org.kiwi.kjector.injectpoint.MultiConstructorInjectPointFoundException;
import org.kiwi.kjector.injectpoint.ParameterConstructorInjectPoint;

import javax.inject.Inject;
import java.lang.reflect.Constructor;
import java.util.Arrays;

public class InjectPointFinder {
    public InjectPoint findInjectPoint(Class klass) {
        if (hasMultiInjectConstructor(klass)) {
            throw new MultiConstructorInjectPointFoundException(klass);
        }

        final Constructor defaultConstructor = getDefaultConstructor(klass);
        if (defaultConstructor != null) {
            return new DefaultConstructorInjectPoint(defaultConstructor);
        }

        final Constructor parameterConstructor = getParameterConstructor(klass);
        if (parameterConstructor != null) {
            return new ParameterConstructorInjectPoint(parameterConstructor);
        }

        throw new InjectPointNotFoundException(klass);
    }

    private boolean hasMultiInjectConstructor(Class klass) {
        return Arrays.asList(klass.getConstructors()).stream()
                .filter(this::isInjectConstructor)
                .count() > 1;
    }

    private Constructor getParameterConstructor(Class klass) {
        return Arrays.asList(klass.getConstructors()).stream()
                .filter(this::isInjectConstructor)
                .findFirst().orElseGet(() -> null);
    }

    private boolean isInjectConstructor(Constructor constructor) {
        return constructor.getAnnotation(Inject.class) != null;
    }

    private Constructor getDefaultConstructor(Class klass) {
        try {
            return klass.getConstructor();
        } catch (NoSuchMethodException e) {
            return null;
        }
    }
}
