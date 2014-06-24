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
        return new NonConstructorInjectPointFinder(klass).findInjectPoint(injectPoint);
    }

    private InjectPoint getConstructorInjectPoint(Class klass) {
        return new ConstructorInjectPointFinder(klass).findInjectPoint();
    }

    private class ConstructorInjectPointFinder {
        private Class klass;

        public ConstructorInjectPointFinder(Class klass) {
            this.klass = klass;
        }

        public InjectPoint findInjectPoint() {
            final List<Constructor> injectConstructors = getInjectConstructor();
            if (injectConstructors.size() > 1) {
                throw new MultiConstructorInjectPointFoundException(klass);
            }

            if (!injectConstructors.isEmpty()) {
                return new ParameterConstructorInjectPoint(injectConstructors.get(0));
            }

            final Constructor defaultConstructor = getDefaultConstructor();
            if (defaultConstructor == null) {
                throw new InjectPointNotFoundException(klass);
            }

            return new DefaultConstructorInjectPoint(defaultConstructor);
        }

        private List<Constructor> getInjectConstructor() {
            return Arrays.asList(klass.getConstructors()).stream()
                    .filter(this::isInjectConstructor)
                    .collect(Collectors.toList());
        }

        private boolean isInjectConstructor(Constructor constructor) {
            return constructor.getAnnotation(Inject.class) != null;
        }

        private Constructor getDefaultConstructor() {
            try {
                return klass.getConstructor();
            } catch (NoSuchMethodException e) {
                return null;
            }
        }
    }

    private class NonConstructorInjectPointFinder {
        private final Class klass;
        private final MethodInjectPointFinder methodInjectPointFinder;
        private final FieldInjectPointerFinder fieldInjectPointerFinder;

        public NonConstructorInjectPointFinder(Class klass) {
            this.klass = klass;
            this.methodInjectPointFinder = new MethodInjectPointFinder(klass);
            this.fieldInjectPointerFinder = new FieldInjectPointerFinder(klass);
        }

        public InjectPoint findInjectPoint(InjectPoint injectPoint) {
            if (klass.getSuperclass() == Object.class) {
                return methodInjectPointFinder.findInjectPoint(fieldInjectPointerFinder.findInjectPoint(injectPoint));
            }
            return methodInjectPointFinder.findInjectPoint(
                    fieldInjectPointerFinder.findInjectPoint(
                            new NonConstructorInjectPointFinder(klass.getSuperclass()).findInjectPoint(injectPoint)));
        }

    }

    private class MethodInjectPointFinder {
        private final Class klass;

        public MethodInjectPointFinder(Class klass) {
            this.klass = klass;
        }

        public InjectPoint findInjectPoint(InjectPoint injectPoint) {
            final List<Method> methods = getMethodWithInjectAnnotation();
            return methods.isEmpty() ? injectPoint : new MethodInjectPoint(injectPoint, methods);
        }

        private List<Method> getMethodWithInjectAnnotation() {
            return Arrays.asList(klass.getDeclaredMethods()).stream()
                    .filter(method -> method.getAnnotation(Inject.class) != null)
                    .collect(Collectors.toList());
        }
    }

    private class FieldInjectPointerFinder {
        private final Class klass;

        public FieldInjectPointerFinder(Class klass) {
            this.klass = klass;
        }

        public InjectPoint findInjectPoint(InjectPoint injectPoint) {
            final List<Field> fields = getFieldWithInjectAnnotation();
            return fields.isEmpty() ? injectPoint : new FieldInjectPoint(injectPoint, fields);
        }

        private List<Field> getFieldWithInjectAnnotation() {
            return Arrays.asList(klass.getDeclaredFields()).stream()
                    .filter(field -> field.getAnnotation(Inject.class) != null)
                    .collect(Collectors.toList());
        }
    }
}
