package com.lazylibs.adser.base;

public interface AdsEvent {
    public interface Converter<C extends AdsConfig, I extends AdsEvent, O> {
        O convert(C config, I input);
    }
}
