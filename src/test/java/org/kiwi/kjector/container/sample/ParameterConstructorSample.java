package org.kiwi.kjector.container.sample;

import javax.inject.Inject;

public class ParameterConstructorSample {
    private final ParameterConstructorParameterSample parameterConstructorParameterSample;

    @Inject
    public ParameterConstructorSample(ParameterConstructorParameterSample parameterConstructorParameterSample) {
        this.parameterConstructorParameterSample = parameterConstructorParameterSample;
    }

    public ParameterConstructorParameterSample getParameterConstructorParameterSample() {
        return parameterConstructorParameterSample;
    }
}
