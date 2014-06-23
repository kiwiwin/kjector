package org.kiwi.kjector.injectpoint;

import org.junit.Before;
import org.junit.Test;
import org.kiwi.kjector.InjectPoint;
import org.kiwi.kjector.InjectPointFinder;
import org.kiwi.kjector.injectpoint.sample.DefaultConstructorSample;
import org.kiwi.kjector.injectpoint.sample.MultiInjectConstructorSample;
import org.kiwi.kjector.injectpoint.sample.ParameterConstructorSample;
import org.kiwi.kjector.injectpoint.sample.ParameterConstructorWithInjectAnnotationSample;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

public class InjectPointFinderTest {

    private InjectPointFinder injectPointFinder;

    @Before
    public void setUp() throws Exception {
        injectPointFinder = new InjectPointFinder();
    }

    @Test
    public void should_has_constructor_as_inject_point_if_has_default_constructor() {
        final InjectPoint injectPoint = injectPointFinder.findInjectPoint(DefaultConstructorSample.class);

        assertThat(injectPoint, instanceOf(DefaultConstructorInjectPoint.class));
    }

    @Test(expected = InjectPointNotFoundException.class)
    public void should_throw_exception_has_no_inject_point_if_has_no_default_constructor() {
        injectPointFinder.findInjectPoint(ParameterConstructorSample.class);
    }

    @Test
    public void should_has_parameter_constructor_as_inject_point_if_has_parameter_constructor_with_inject_annotation() {
        final InjectPoint injectPoint = injectPointFinder.findInjectPoint(ParameterConstructorWithInjectAnnotationSample.class);

        assertThat(injectPoint, instanceOf(ParameterConstructorInjectPoint.class));
    }

    @Test(expected = MultiInjectPointFoundException.class)
    public void should_throw_exception_when_multi_inject_constructor_found() {
        injectPointFinder.findInjectPoint(MultiInjectConstructorSample.class);
    }
}
