package org.kiwi.kjector.injectpoint;

import org.junit.Test;

import javax.inject.Inject;
import javax.inject.Qualifier;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;

public class QualifierMetaTest {
    @Test
    public void should_qualifier_equal() {
        final QualifierMeta meta1 = new QualifierMeta(Painter.class)
                .meta("color", Painter.Color.BLACK)
                .meta("backgroundColor", Painter.Color.TAN);

        final QualifierMeta meta2 = new QualifierMeta(Painter.class)
                .meta("color", Painter.Color.BLACK)
                .meta("backgroundColor", Painter.Color.TAN);

        assertThat(meta1, is(meta2));
    }

    @Test
    public void should_qualifier_not_equal() {
        final QualifierMeta meta1 = new QualifierMeta(Painter.class)
                .meta("color", Painter.Color.RED)
                .meta("backgroundColor", Painter.Color.TAN);

        final QualifierMeta meta2 = new QualifierMeta(Painter.class)
                .meta("color", Painter.Color.BLACK)
                .meta("backgroundColor", Painter.Color.TAN);

        assertThat(meta1, not(meta2));
    }

    @Test
    public void should_create_qualifier_by_qualifier_instance() {
        final Painter painter = new Painter() {
            @Override
            public Color backgroundColor() {
                return Color.BLACK;
            }

            @Override
            public Color color() {
                return Color.RED;
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return Painter.class;
            }
        };

        final QualifierMeta painterMetaData = QualifierMeta.create(painter);

        final QualifierMeta meta = new QualifierMeta(Painter.class)
                .meta("color", Painter.Color.RED)
                .meta("backgroundColor", Painter.Color.BLACK);

        assertThat(painterMetaData, is(meta));
    }

    @Test
    public void should_create_qualifier_by_field() throws NoSuchFieldException {
        final Field field = Farmer.class.getDeclaredField("uglyPainter");
        final Annotation annotation = Arrays.asList(field.getAnnotations()).stream().filter(a -> a.annotationType().isAnnotationPresent(Qualifier.class)).findFirst().get();

        final QualifierMeta uglyPainter = QualifierMeta.create(annotation);

        final QualifierMeta meta = new QualifierMeta(Painter.class)
                .meta("color", Painter.Color.RED)
                .meta("backgroundColor", Painter.Color.BLACK);

        assertThat(uglyPainter, is(meta));
    }

    @Test
    public void should_use_default_value() throws NoSuchFieldException {
        final Field field = Tree.class.getDeclaredField("uglyTree");
        final Annotation annotation = Arrays.asList(field.getAnnotations()).stream().filter(a -> a.annotationType().isAnnotationPresent(Qualifier.class)).findFirst().get();

        final QualifierMeta uglyTree = QualifierMeta.create(annotation);

        final QualifierMeta meta = new QualifierMeta(Painter.class)
                .meta("color", Painter.Color.TAN)
                .meta("backgroundColor", Painter.Color.TAN);

        assertThat(uglyTree, is(meta));
    }
}

@java.lang.annotation.Documented
@Retention(RetentionPolicy.RUNTIME)
@javax.inject.Qualifier
@interface Painter {
    Color backgroundColor() default Color.TAN;

    Color color() default Color.TAN;

    public enum Color {RED, BLACK, TAN}
}


class Farmer {
    @Inject
    @Painter(color = Painter.Color.RED, backgroundColor = Painter.Color.BLACK)
    String uglyPainter;
}

class Tree {
    @Inject
    @Painter
    String uglyTree;
}