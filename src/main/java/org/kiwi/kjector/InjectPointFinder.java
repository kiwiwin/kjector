package org.kiwi.kjector;

import org.kiwi.kjector.injectpoint.DefaultConstructorInjectPoint;
import org.kiwi.kjector.injectpoint.InjectPointNotFoundException;
import org.kiwi.kjector.injectpoint.MultiInjectPointFoundException;
import org.kiwi.kjector.injectpoint.ParameterConstructorInjectPoint;

import javax.inject.Inject;
import java.lang.reflect.Constructor;
import java.util.Arrays;

public class InjectPointFinder {
    public InjectPoint findInjectPoint(Class klass) {
        if (hasMultiInjectConstructor(klass)) {
            throw new MultiInjectPointFoundException(klass);
        }
        if (hasDefaultConstructor(klass)) {
            return new DefaultConstructorInjectPoint(klass);
        }
        if (hasParameterConstructor(klass)) {
            return new ParameterConstructorInjectPoint(klass);
        }

        throw new InjectPointNotFoundException(klass);
    }

    private boolean hasMultiInjectConstructor(Class klass) {
        return Arrays.asList(klass.getConstructors()).stream()
                .filter(this::isInjectConstructor)
                .count() > 1;
    }

    private boolean hasParameterConstructor(Class klass) {
        return Arrays.asList(klass.getConstructors()).stream()
                .filter(this::isInjectConstructor)
                .count() > 0;
    }

    private boolean isInjectConstructor(Constructor constructor) {
        return constructor.getAnnotation(Inject.class) != null;
    }

    private boolean hasDefaultConstructor(Class klass) {
        try {
            return klass.getConstructor() != null;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }
}
