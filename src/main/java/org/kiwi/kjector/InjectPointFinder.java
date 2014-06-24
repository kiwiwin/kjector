package org.kiwi.kjector;

import org.kiwi.kjector.injectpoint.DefaultConstructorInjectPoint;
import org.kiwi.kjector.injectpoint.FieldInjectPoint;
import org.kiwi.kjector.injectpoint.MethodInjectPoint;
import org.kiwi.kjector.injectpoint.ParameterConstructorInjectPoint;
import org.kiwi.kjector.injectpoint.exception.InjectPointNotFoundException;
import org.kiwi.kjector.injectpoint.exception.MultiConstructorInjectPointFoundException;

import javax.inject.Inject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InjectPointFinder {
    public InjectPoint findInjectPoint(Class klass) {
        return getNonConstructorInjectPoint(klass, getConstructorInjectPoint(klass));
    }

    private InjectPoint getNonConstructorInjectPoint(Class klass, InjectPoint injectPoint) {
        if (klass.getSuperclass() == Object.class) {
            return getMethodInjectPoint(klass, getFieldInjectPoint(klass, injectPoint));
        }
        return getMethodInjectPoint(klass, getFieldInjectPoint(klass, getNonConstructorInjectPoint(klass.getSuperclass(), injectPoint)));
    }

    private InjectPoint getMethodInjectPoint(Class klass, InjectPoint injectPoint) {
        final List<Method> methods = getMethodInjectPoint(klass);
        return methods.isEmpty() ? injectPoint : new MethodInjectPoint(injectPoint, methods);
    }

    private List<Method> getMethodInjectPoint(Class klass) {
        return Arrays.asList(klass.getDeclaredMethods()).stream()
                .filter(method -> method.getAnnotation(Inject.class) != null)
                .collect(Collectors.toList());
    }

    private InjectPoint getFieldInjectPoint(Class klass, InjectPoint injectPoint) {
        final List<Field> fields = getFieldInjectPoint(klass);
        return fields.isEmpty() ? injectPoint : new FieldInjectPoint(injectPoint, fields);
    }

    private List<Field> getFieldInjectPoint(Class klass) {
        return Arrays.asList(klass.getDeclaredFields()).stream()
                .filter(field -> field.getAnnotation(Inject.class) != null)
                .collect(Collectors.toList());
    }

    private InjectPoint getConstructorInjectPoint(Class klass) {
        final List<Constructor> injectConstructors = getInjectConstructor(klass);
        if (injectConstructors.size() > 1) {
            throw new MultiConstructorInjectPointFoundException(klass);
        }

        if (!injectConstructors.isEmpty()) {
            return new ParameterConstructorInjectPoint(injectConstructors.get(0));
        }

        final Constructor defaultConstructor = getDefaultConstructor(klass);
        if (defaultConstructor == null) {
            throw new InjectPointNotFoundException(klass);
        }

        return new DefaultConstructorInjectPoint(defaultConstructor);
    }

    private List<Constructor> getInjectConstructor(Class klass) {
        return Arrays.asList(klass.getConstructors()).stream()
                .filter(this::isInjectConstructor)
                .collect(Collectors.toList());
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
