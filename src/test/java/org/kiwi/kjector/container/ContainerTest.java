package org.kiwi.kjector.container;

import org.junit.Test;
import org.kiwi.kjector.container.sample.FieldInjectSample;
import org.kiwi.kjector.container.sample.ParameterConstructorParameterSample;
import org.kiwi.kjector.container.sample.ParameterConstructorSample;
import org.kiwi.kjector.injectpoint.sample.DefaultConstructorSample;

import java.lang.reflect.Field;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ContainerTest {

    @Test
    public void should_able_to_build_default_constructor_bean() {
        final Container container = Container.builder()
                .register(DefaultConstructorSample.class)
                .build();

        final DefaultConstructorSample resolvedObject = container.resolve(DefaultConstructorSample.class);
        assertThat(resolvedObject, notNullValue());
    }

    @Test
    public void should_able_to_build_parameter_constructor_bean() {
        final Container container = Container.builder()
                .register(ParameterConstructorSample.class)
                .register(ParameterConstructorParameterSample.class)
                .build();

        final ParameterConstructorSample resolvedObject = container.resolve(ParameterConstructorSample.class);
        assertThat(resolvedObject, notNullValue());
    }

    @Test(expected = NoSuitableBeanRegisteredException.class)
    public void should_throw_exception_when_build_unregister_type() {
        final Container container = Container.builder().build();

        container.resolve(DefaultConstructorSample.class);
    }

    @Test(expected = NoSuitableBeanRegisteredException.class)
    public void should_throw_exception_when_build_unregister_type_as_parameter() {
        final Container container = Container.builder().register(ParameterConstructorSample.class).build();

        container.resolve(ParameterConstructorSample.class);
    }

    @Test
    public void should_bind_bean_to_class() {
        final Container container = Container.builder()
                .bind(new ParameterConstructorSample(new ParameterConstructorParameterSample()), ParameterConstructorSample.class)
                .build();

        final ParameterConstructorSample resolvedObject = container.resolve(ParameterConstructorSample.class);

        assertThat(resolvedObject, notNullValue());
    }

    @Test
    public void should_able_to_build_field_inject_bean() {
        final Container container = Container.builder()
                .bind("jack", String.class)
                .register(FieldInjectSample.class)
                .build();

        final FieldInjectSample resolvedObject = container.resolve(FieldInjectSample.class);

        assertThat(resolvedObject, notNullValue());
        assertThat(resolvedObject.getName(), is("jack"));
    }

}
