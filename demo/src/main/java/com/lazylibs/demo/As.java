package com.lazylibs.demo;

import androidx.annotation.Keep;

import com.alibaba.fastjson.annotation.JSONField;
import com.lazylibs.adser.adjust.IAdjustConfig;
import com.lazylibs.utils.Lazier;
import com.lazylibs.utils.Xc;

@Keep
public class As {
    /**
     * 秘钥{"k":"TheLittleRedDotOfAllEvil","t":"ADJUST","a":"https://google.com","b":"https://google.com","p":"https://bing.com","c":{"at":"bn2v0eblh3wg","ac":"BRL","ae":"FB_LOGIN pq6vxp FB_REGISTER mqo3x3 FIRST_RECHARGE ukxnjq GOOGLE_LOGIN rpp974 GOOGLE_REGISTER cdtbe3 LOGIN 7ikzr6 PAGE_VIEW 2kwnrd PAY_RECHARGE czh08m REGISTER 2mhnq8 SECOND_RECHARGE 2b8ktc"}}
     */
    @JSONField(name = "k")
    public String k = Xc.ck;
    /***
     * Ads类型
     */
    @JSONField(name = "t")
    public String t = "ADJUST";
    /***
     * Ads配置信息
     */
    @JSONField(name = "c")
    public Ac c = null;
    /***
     * A面URL，加密可选，建议加密
     */
    @JSONField(name = "a")
    public String a = "";
    /***
     * B面URL，加密可选，建议加密
     */
    @JSONField(name = "b")
    public String b = "https://google.com";
    /***
     * 隐私协议及服务条款可以是文字内容，也可以是URL
     * 为链接时可加密可不加密
     * 为文字时必须加密
     */
    @JSONField(name = "p")
    public String p = "https://google.com";

    @JSONField(serialize = false, deserialize = false)
    public String za() {
        return Lazier.uRaw(a, k);
    }

    @JSONField(serialize = false, deserialize = false)
    public String zb() {
        return Lazier.uRaw(b, k);
    }

    @JSONField(serialize = false, deserialize = false)
    public String zp() {
        return Lazier.uRaw(p, k);
    }

    @JSONField(serialize = false, deserialize = false)
    public IAdjustConfig ac() {
        return c!=null && !c.ie() ? c.ts():null;
    }
}