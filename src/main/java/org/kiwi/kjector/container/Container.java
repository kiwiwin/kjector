package org.kiwi.kjector.container;

import org.kiwi.kjector.InjectPointFinder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
}
