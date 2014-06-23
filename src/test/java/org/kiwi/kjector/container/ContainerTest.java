package org.kiwi.kjector.container;

import org.junit.Test;
import org.kiwi.kjector.container.sample.Car;
import org.kiwi.kjector.container.sample.ParameterConstructorParameterSample;
import org.kiwi.kjector.container.sample.ParameterConstructorSample;
import org.kiwi.kjector.injectpoint.sample.DefaultConstructorSample;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ContainerTest {
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
    public void should_register_by_name() {
        final Container container = Container.builder()
                .registerByName("car", new Car("bmw"))
                .build();

        Object car = container.resolveByName("car");

        assertThat(car, instanceOf(Car.class));
        assertThat(((Car) car).getBrand(), is("bmw"));
    }

    @Test(expected = NoSuitableNamedBeanRegisteredException.class)
    public void should_throw_exception_when_not_named_bean_found() {
        final Container container = Container.builder()
                .build();

        container.resolveByName("car");
    }
}
