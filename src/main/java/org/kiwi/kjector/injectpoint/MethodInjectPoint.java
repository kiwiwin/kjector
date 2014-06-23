package org.kiwi.kjector.injectpoint;

import org.kiwi.kjector.InjectPoint;
import org.kiwi.kjector.container.Container;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
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
            final Class[] parameterTypes = method.getParameterTypes();
            final Object[] parameters = resolveParameters(parameterTypes, container);
            try {
                method.invoke(resolvedObject, parameters);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                return null;
            }
        }
        return resolvedObject;
    }

    private Object[] resolveParameters(Class[] parameterTypes, Container container) {
        return Arrays.asList(parameterTypes).stream()
                .map(container::resolve)
                .toArray();
    }
}
