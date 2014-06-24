package org.kiwi.kjector.injectpoint;

import org.junit.Test;
import org.kiwi.kjector.container.Container;
import org.kiwi.kjector.container.exception.NoSuitableNamedBeanRegisteredException;
import org.kiwi.kjector.container.sample.FieldInjectSample;
import org.kiwi.kjector.injectpoint.sample.Dog;
import org.kiwi.kjector.injectpoint.sample.Duck;
import org.kiwi.kjector.injectpoint.sample.Farmer;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class FieldInjectPointTest {
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


    @Test
    public void should_able_to_build_parameter_constructor_bean_by_name() {
        final Container container = Container.builder()
                .registerByName("dog", new Dog())
                .registerByName("duck", new Duck())
                .register(Farmer.class)
                .build();

        final Farmer farmer = container.resolve(Farmer.class);
        assertThat(farmer.getAnimal(), instanceOf(Duck.class));
    }

    @Test(expected = NoSuitableNamedBeanRegisteredException.class)
    public void should_throw_exception_when_no_bean_provided_by_give_name() {
        final Container container = Container.builder()
                .registerByName("dog", new Dog())
                .register(Farmer.class)
                .build();

        container.resolve(Farmer.class);
    }
}
