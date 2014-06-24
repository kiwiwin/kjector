package org.kiwi.kjector.injectpoint;

import org.kiwi.kjector.InjectPoint;
import org.kiwi.kjector.container.Container;
import org.kiwi.kjector.injectpoint.exception.ResolveObjectException;

import javax.inject.Qualifier;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class FieldInjectPoint implements InjectPoint {
    private final InjectPoint constructorInjectPoint;
    private final List<Field> fields;

    public FieldInjectPoint(InjectPoint constructorInjectPoint, List<Field> fields) {
        this.constructorInjectPoint = constructorInjectPoint;
        this.fields = fields;
    }

    @Override
    public Object resolveObject(Container container) {
        final Object resolvedObject = constructorInjectPoint.resolveObject(container);
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                final QualifierMeta qualifierMeta = getQualifier(field);
                if (qualifierMeta == null) {
                    field.set(resolvedObject, container.resolve(field.getType()));
                } else {
                    field.set(resolvedObject, container.resolveByQualifier(qualifierMeta));
                }
                field.setAccessible(false);
            } catch (IllegalAccessException e) {
                throw new ResolveObjectException();
            }
        }
        return resolvedObject;
    }

    private QualifierMeta getQualifier(Field field) {
        return Arrays.asList(field.getAnnotations()).stream()
                .filter(annotation -> annotation.annotationType().isAnnotationPresent(Qualifier.class))
                .map(annotation -> QualifierMeta.create(annotation))
                .findFirst().orElseGet(() -> null);
    }

}
