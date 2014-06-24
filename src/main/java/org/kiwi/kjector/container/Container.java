package org.kiwi.kjector.container;

import org.kiwi.kjector.InjectPointFinder;
import org.kiwi.kjector.container.exception.NoSuitableBeanRegisteredException;
import org.kiwi.kjector.container.exception.NoSuitableQualifierBeanRegisteredException;
import org.kiwi.kjector.injectpoint.QualifierMeta;

import javax.inject.Named;
import javax.inject.Qualifier;
import java.lang.annotation.Annotation;
import java.lang.reflect.Executable;
import java.util.*;

public class Container {
    private final InjectPointFinder injectPointFinder;
    private final Set<Class> registeredClasses = new HashSet<>();
    private final Map<Class, Object> resolvedObjects = new HashMap<>();
    private final Map<QualifierMeta, Object> qualifierObjects = new HashMap<>();

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
        qualifierObjects.put(new QualifierMeta(Named.class).meta("value", objectName), object);
        return this;
    }

    public Object resolveByName(String objectName) {
        final Object namedObject = qualifierObjects.get(new QualifierMeta(Named.class).meta("value", objectName));
        if (namedObject == null) {
            throw new NoSuitableQualifierBeanRegisteredException(objectName);
        }
        return namedObject;
    }

    public Object[] resolveExecutable(Executable executable) {
        final Annotation[][] parameterAnnotations = executable.getParameterAnnotations();
        final Class[] parameterTypes = executable.getParameterTypes();

        List<Object> resolvedParameters = new ArrayList<>();

        for (int parameterIndex = 0; parameterIndex < parameterTypes.length; parameterIndex++) {
            final QualifierMeta qualifierMeta = resolveNamedAnnotation(parameterAnnotations[parameterIndex]);
            if (qualifierMeta != null) {
                resolvedParameters.add(resolveByQualifier(qualifierMeta));
            } else {
                resolvedParameters.add(resolve(parameterTypes[parameterIndex]));
            }
        }
        return resolvedParameters.toArray();
    }

    private QualifierMeta resolveNamedAnnotation(Annotation[] annotations) {
        return Arrays.asList(annotations).stream()
                .filter(annotation -> annotation.annotationType().isAnnotationPresent(Qualifier.class))
                .map(QualifierMeta::create)
                .findFirst().orElseGet(() -> null);
    }

    public Container registerByQualifier(QualifierMeta qualifierMeta, Object object) {
        qualifierObjects.put(qualifierMeta, object);
        return this;
    }

    public Object resolveByQualifier(QualifierMeta meta) {
        final Object qualifierObject = qualifierObjects.get(meta);
        if (qualifierObject == null) {
            throw new NoSuitableQualifierBeanRegisteredException(meta.getClass().getName());
        }
        return qualifierObject;
    }
}
