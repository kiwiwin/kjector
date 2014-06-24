package org.kiwi.kjector.container;

import org.kiwi.kjector.InjectPointFinder;
import org.kiwi.kjector.container.exception.NoSuitableBeanRegisteredException;
import org.kiwi.kjector.container.exception.NoSuitableNamedBeanRegisteredException;

import javax.inject.Named;
import java.lang.annotation.Annotation;
import java.lang.reflect.Executable;
import java.util.*;

public class Container {
    private final InjectPointFinder injectPointFinder;
    private final Set<Class> registeredClasses = new HashSet<>();
    private final Map<Class, Object> resolvedObjects = new HashMap<>();
    private final Map<String, Object> namedObjects = new HashMap<>();

    private Container(InjectPointFinder injectPointFinder) {
        this.injectPointFinder = injectPointFinder;
    }

    public static Container builder() {
        return new Container(new InjectPointFinder());
    }

    public Container register(Class klass) {
        registeredClasses.add(klass);
        return this;
    }

    public Container build() {
        return this;
    }

    public <T> T resolve(Class<T> klass) {
        if (!registeredClasses.contains(klass)) {
            throw new NoSuitableBeanRegisteredException(klass);
        }

        if (!resolvedObjects.containsKey(klass)) {
            resolvedObjects.put(klass, injectPointFinder.findInjectPoint(klass).resolveObject(this));
        }
        return (T) resolvedObjects.get(klass);
    }

    public Container bind(Object bindObject, Class bindClass) {
        registeredClasses.add(bindClass);
        resolvedObjects.put(bindClass, bindObject);
        return this;
    }

    public Container registerByName(String objectName, Object object) {
        namedObjects.put(objectName, object);
        return this;
    }

    public Object resolveByName(String objectName) {
        final Object namedObject = namedObjects.get(objectName);
        if (namedObject == null) {
            throw new NoSuitableNamedBeanRegisteredException(objectName);
        }
        return namedObject;
    }

    public Object[] resolveExecutable(Executable executable) {
        final Annotation[][] parameterAnnotations = executable.getParameterAnnotations();
        final Class[] parameterTypes = executable.getParameterTypes();

        List<Object> resolvedParameters = new ArrayList<>();

        for (int parameterIndex = 0; parameterIndex < parameterTypes.length; parameterIndex++) {
            final String named = resolveNamedAnnotation(parameterAnnotations[parameterIndex]);
            if (named != null && !named.isEmpty()) {
                resolvedParameters.add(resolveByName(named));
            } else {
                resolvedParameters.add(resolve(parameterTypes[parameterIndex]));
            }
        }
        return resolvedParameters.toArray();
    }

    private String resolveNamedAnnotation(Annotation[] annotations) {
        final Named namedAnnotation = (Named) Arrays.asList(annotations).stream()
                .filter(annotation -> annotation.annotationType() == Named.class)
                .findFirst().orElseGet(() -> null);

        return namedAnnotation == null ? null : namedAnnotation.value();
    }
}
