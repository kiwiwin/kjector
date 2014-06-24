package org.kiwi.kjector.container.sample;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@java.lang.annotation.Documented
@Retention(RetentionPolicy.RUNTIME)
@javax.inject.Qualifier
public @interface Painter {
    Color backgroundColor() default Color.TAN;

    Color color() default Color.TAN;

    public enum Color {RED, BLACK, TAN}
}
