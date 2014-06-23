package org.kiwi.kjector.injectpoint;

import org.junit.Test;
import org.kiwi.kjector.container.Container;
import org.kiwi.kjector.container.sample.ParameterConstructorParameterSample;
import org.kiwi.kjector.container.sample.ParameterConstructorSample;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class ParameterConstructorInjectPointTest {
    @Test
    public void should_able_to_build_parameter_constructor_bean() {
        final Container container = Container.builder()
                .register(ParameterConstructorSample.class)
                .register(ParameterConstructorParameterSample.class)
                .build();

        final ParameterConstructorSample resolvedObject = container.resolve(ParameterConstructorSample.class);
        assertThat(resolvedObject, notNullValue());
    }
}
