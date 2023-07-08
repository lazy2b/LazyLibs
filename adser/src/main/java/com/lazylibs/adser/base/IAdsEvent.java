package com.lazylibs.adser.base;

public interface IAdsEvent {
    public interface Converter<C extends IAdsConfig, I extends IAdsEvent, O> {
        O convert(C config, I input);
    }
}
