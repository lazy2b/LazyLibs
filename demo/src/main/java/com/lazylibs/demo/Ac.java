package com.lazylibs.demo;

import android.text.TextUtils;

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

    public Ac() {
    }

    public Ac(Ac c) {
        this.at = c.at;
        this.ac = c.ac;
        this.ae = c.ae;
    }

    @JSONField(serialize = false, deserialize = false)
    public boolean ie() {
        return TextUtils.isEmpty(at) || TextUtils.isEmpty(ac);
    }

    @JSONField(serialize = false, deserialize = false)
    public IAdjustConfig.Simple ts() {
        return new IAdjustConfig.Simple(at, ac, ae);
    }
}
