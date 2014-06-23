package org.kiwi.kjector.injectpoint;

import org.junit.Test;
import org.kiwi.kjector.container.Container;
import org.kiwi.kjector.injectpoint.sample.MethodInjectAnnotationSample;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class MethodInjectPointTest {
    @Test
    public void should_able_to_build_field_inject_bean() {
        final Container container = Container.builder()
                .bind("jack", String.class)
                .register(MethodInjectAnnotationSample.class)
                .build();

        final MethodInjectAnnotationSample resolvedObject = container.resolve(MethodInjectAnnotationSample.class);

        assertThat(resolvedObject, notNullValue());
        assertThat(resolvedObject.getName(), is("jack"));
    }
}
