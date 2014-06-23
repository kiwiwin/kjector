package org.kiwi.kjector.injectpoint;

import org.kiwi.kjector.InjectPoint;
import org.kiwi.kjector.container.Container;

import javax.inject.Named;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
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
            try {
                injectMethod(container, resolvedObject, method);
            } catch (InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return resolvedObject;
    }

    private void injectMethod(Container container, Object resolvedObject, Method method) throws InvocationTargetException, IllegalAccessException {
        final Class[] parameterTypes = method.getParameterTypes();
        final Annotation[][] parameterAnnotations = method.getParameterAnnotations();

        List<Object> resolvedParameters = new ArrayList<>();

        for (int parameterIndex = 0; parameterIndex < parameterTypes.length; parameterIndex++) {
            if (parameterAnnotations[parameterIndex].length > 0) {
                final Named namedAnnotation = (Named) parameterAnnotations[parameterIndex][0];
                resolvedParameters.add(container.resolveByName(namedAnnotation.value()));
            } else {
                resolvedParameters.add(container.resolve(parameterTypes[parameterIndex]));
            }
        }
        method.invoke(resolvedObject, resolvedParameters.toArray());
    }

    private Object[] resolveParameters(Class[] parameterTypes, Container container) {
        return Arrays.asList(parameterTypes).stream()
                .map(container::resolve)
                .toArray();
    }
}
