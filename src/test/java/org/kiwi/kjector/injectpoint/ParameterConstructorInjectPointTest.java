package org.kiwi.kjector.injectpoint;

import org.junit.Test;
import org.kiwi.kjector.container.Container;
import org.kiwi.kjector.container.sample.ParameterConstructorParameterSample;
import org.kiwi.kjector.container.sample.ParameterConstructorSample;
import org.kiwi.kjector.injectpoint.sample.NamedParameterConstructorSample;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

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

    @Test
    public void should_able_to_register_by_name() {
        final Container container = Container.builder()
                .registerByName("easy", "easy-name")
                .register(NamedParameterConstructorSample.class)
                .build();

        final NamedParameterConstructorSample resolvedObject = container.resolve(NamedParameterConstructorSample.class);
        assertThat(resolvedObject.getName(), is("easy-name"));
    }
}
