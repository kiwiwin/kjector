package org.kiwi.kjector.injectpoint;

import org.kiwi.kjector.InjectPoint;
import org.kiwi.kjector.container.Container;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Consumer;

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
                field.set(resolvedObject, container.resolve(field.getType()));
                field.setAccessible(false);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return resolvedObject;
    }
}
