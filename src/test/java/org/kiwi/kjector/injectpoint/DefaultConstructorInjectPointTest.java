package org.kiwi.kjector.injectpoint;

import org.junit.Test;
import org.kiwi.kjector.container.Container;
import org.kiwi.kjector.injectpoint.sample.DefaultConstructorSample;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class DefaultConstructorInjectPointTest {
    @Test
    public void should_able_to_build_default_constructor_bean() {
        final Container container = Container.builder()
                .register(DefaultConstructorSample.class)
                .build();

        final DefaultConstructorSample resolvedObject = container.resolve(DefaultConstructorSample.class);
        assertThat(resolvedObject, notNullValue());
    }
}
