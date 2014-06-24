package org.kiwi.kjector.injectpoint;

import org.kiwi.kjector.InjectPoint;
import org.kiwi.kjector.container.Container;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class MethodInjectPoint implements InjectPoint {
    private final InjectPoint injectPoint;
    private final List<Method> methods;

    public MethodInjectPoint(InjectPoint injectPoint, List<Method> methods) {
        this.injectPoint = injectPoint;
        this.methods = methods;
    }

    @Override
    public Object resolveObject(Container container) {
        final Object resolvedObject = injectPoint.resolveObject(container);
        for (Method method : methods) {
            try {
                method.invoke(resolvedObject, container.resolveExecutable(method));
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new ResolveObjectException();
            }
        }
        return resolvedObject;
    }

}
