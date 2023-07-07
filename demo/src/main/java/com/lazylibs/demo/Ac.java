package com.lazylibs.demo;

import androidx.annotation.Keep;

import com.alibaba.fastjson.annotation.JSONField;
import com.lazylibs.adser.adjust.IAdjustConfig;

@Keep
public class Ac {
    /***
     * Adjust App Token
     */
    @JSONField(name = "at")
    public String at = "";
    /***
     * Adjust 币种
     */
    @JSONField(name = "ac")
    public String ac = "";
    /***
     * Adjust events
     */
    @JSONField(name = "ae")
    public String ae = "";

    @JSONField(serialize = false, deserialize = false)
    public IAdjustConfig.Simple toSimple() {
        return new IAdjustConfig.Simple(at, ac, ae);
    }
}
