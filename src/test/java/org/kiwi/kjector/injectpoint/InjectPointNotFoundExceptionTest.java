package org.kiwi.kjector.injectpoint;

import org.junit.Test;
import org.kiwi.kjector.injectpoint.exception.InjectPointNotFoundException;
import org.kiwi.kjector.injectpoint.sample.Dummy;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class InjectPointNotFoundExceptionTest {
    @Test
    public void should_get_message() {
        final String message = new InjectPointNotFoundException(Dummy.class).getMessage();

        assertThat(message, is("No inject point found for class: org.kiwi.kjector.injectpoint.sample.Dummy"));
    }
}
