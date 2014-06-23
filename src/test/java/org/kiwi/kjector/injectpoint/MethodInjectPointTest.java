package org.kiwi.kjector.injectpoint;

import org.junit.Test;
import org.kiwi.kjector.container.Container;
import org.kiwi.kjector.injectpoint.sample.Dog;
import org.kiwi.kjector.injectpoint.sample.MethodInjectAnnotationSample;
import org.kiwi.kjector.injectpoint.sample.Person;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class MethodInjectPointTest {
    @Test
    public void should_able_to_build_method_inject_bean() {
        final Container container = Container.builder()
                .bind("jack", String.class)
                .register(MethodInjectAnnotationSample.class)
                .build();

        final MethodInjectAnnotationSample resolvedObject = container.resolve(MethodInjectAnnotationSample.class);

        assertThat(resolvedObject, notNullValue());
        assertThat(resolvedObject.getName(), is("jack"));
    }

    @Test
    public void should_able_to_build_named_method_inject_bean() {
        final Container container = Container.builder()
                .registerByName("lucy", new Dog())
                .register(Person.class)
                .build();

        final Person person = container.resolve(Person.class);
        assertThat(person.getPet(), instanceOf(Dog.class));
    }
}
