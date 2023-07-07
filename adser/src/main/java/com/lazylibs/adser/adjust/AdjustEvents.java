package com.lazylibs.adser.adjust;

import android.text.TextUtils;

import com.adjust.sdk.AdjustEvent;
import com.lazylibs.adser.Adser;
import com.lazylibs.adser.base.AdsEvent;

public class AdjustEvents implements AdsEvent {
    public String key;
    public String value;
    public String currency = "";
    public boolean isRecharge = false;

    static class Converter implements AdsEvent.Converter<IAdjustConfig, AdjustEvents, AdjustEvent> {
        @Override
        public AdjustEvent convert(IAdjustConfig config, AdjustEvents input) {
            if (!TextUtils.isEmpty(input.key)) {
                Adser.logD(String.format("Adset.trackEvent('%s','%s');", input.key, input.value));
                if (config.getEvents() != null && config.getEvents().containsKey(input.key)) {
                    String eToken = config.getEvents().get(input.key);
                    if (!TextUtils.isEmpty(eToken)) {
                        AdjustEvent adjustEvent = new AdjustEvent(eToken);
                        if (!TextUtils.isEmpty(input.value)) {
                            if (input.isRecharge) {
                                String _currency = TextUtils.isEmpty(input.currency) ? config.getCurrency() : input.currency;
                                try {
                                    adjustEvent.setRevenue(Double.parseDouble(input.value), _currency);
                                    Adser.logD(String.format("Adset.recharge('%s','%s','%s');", input.key, input.value, _currency));
                                } catch (NumberFormatException ignored) {
                                }
                            } else {
                                if (!TextUtils.isEmpty(input.value)) {
                                    adjustEvent.setCallbackId(input.value);
                                }
                            }
                        }
                        return adjustEvent;
                    }
                }
            }
            return null;
        }
    }
}
