package org.kiwi.kjector.injectpoint.exception;

import org.junit.Test;
import org.kiwi.kjector.injectpoint.sample.Dummy;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MultiConstructorInjectPointFoundExceptionTest {
    @Test
    public void should_get_message() {
        final String message = new MultiConstructorInjectPointFoundException(Dummy.class).getMessage();

        assertThat(message, is("Multi constructor inject point found for class: org.kiwi.kjector.injectpoint.sample.Dummy"));
    }
}
