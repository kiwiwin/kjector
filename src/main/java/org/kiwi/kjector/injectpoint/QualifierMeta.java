package org.kiwi.kjector.injectpoint;

import javax.inject.Qualifier;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class QualifierMeta {
    private final Class qualifierClass;
    private Map<String, Object> meta = new HashMap<>();

    public QualifierMeta(Class qualifierClass) {
        this.qualifierClass = qualifierClass;
    }

    public QualifierMeta meta(String metaKey, Object metaValue) {
        meta.put(metaKey, metaValue);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QualifierMeta that = (QualifierMeta) o;

        if (qualifierClass != null ? !qualifierClass.equals(that.qualifierClass) : that.qualifierClass != null) {
            return false;
        }
        if (meta.size() != that.meta.size()) return false;

        final List<String> keys = meta.keySet().stream().collect(Collectors.toList());
        for (String key : keys) {
            if (!meta.get(key).equals(that.meta.get(key))) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = qualifierClass != null ? qualifierClass.hashCode() : 0;
        return result;
    }

    public static QualifierMeta create(Annotation annotation) {
        final Class<? extends Annotation> annotationType = annotation.annotationType();
        final QualifierMeta qualifierMeta = new QualifierMeta(annotationType);
        if (annotationType.isAnnotationPresent(Qualifier.class)) {
//            System.out.println(annotation.annotationType());
            for (Method method : annotationType.getDeclaredMethods()) {
                try {
                    qualifierMeta.meta(method.getName(), method.invoke(annotation));
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException();
                }
            }
        }

        return qualifierMeta;
    }
}
