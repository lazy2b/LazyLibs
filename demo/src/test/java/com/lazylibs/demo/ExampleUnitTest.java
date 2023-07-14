package com.lazylibs.demo;

import static org.junit.Assert.assertEquals;

import com.alibaba.fastjson.JSON;
import com.lazylibs.utils.Logger;
import com.lazylibs.utils.Xc;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

//    void verifyResult(XResult xResult) {
//        assertEquals(xResult.verifyResult(), xResult.localConfigString);
//    }

    public class Config {
        public String code = Xc.ck;
        public String getAppSettingsUrl = "";
        public String asUrlCode = "";
        public As as;
        public As asc;
        public String asCode = "";

        public void xCode() {
            asUrlCode = Xc.akCode(getAppSettingsUrl, code);
            asc = new As();
            asc.k = as.k;
            asc.t = as.t;
            asc.a = Xc.akCode(as.a, as.k);
            asc.b = Xc.akCode(as.b, as.k);
            asc.p = Xc.akCode(as.p, as.k);
            asc.c = new Ac(as.c);
            asCode = Xc.akCode(JSON.toJSONString(asc), code);
        }
    }

    @Test
    public void xCode() {
        Config config = new Config() {{
            getAppSettingsUrl = "https://i.v2o.top/api/v1/gadsts/7";
            as = new As() {{
                k = Xc.ck;
                t = "ADJUST";
                c = new Ac() {{
                    at = "bn2v0eblh3wg";
                    ac = "BRL";
                    ae = "FB_LOGIN pq6vxp FB_REGISTER mqo3x3 FIRST_RECHARGE ukxnjq GOOGLE_LOGIN rpp974 GOOGLE_REGISTER cdtbe3 LOGIN 7ikzr6 PAGE_VIEW 2kwnrd PAY_RECHARGE czh08m REGISTER 2mhnq8 SECOND_RECHARGE 2b8ktc";
                }};
                a = "";// A面
                b = "";// B面
                p = "";// Patos隐私协议和服务条款
            }};
        }};
        config.xCode();
        System.out.println(JSON.toJSONString(config));
        System.out.println("本地AsUrl配置：" + config.asUrlCode);
        System.out.println("本地As配置：" + config.asCode.replace("\n", "\\n"));
        assertEquals("sl2A2String", "sl2A2String");
    }

    @Test
    public void dLocalCode() {
        String localFinalCode = "0v9OJSPFvrh1Lqr4ea5bKA054eK0WEOluSdFpzJus0ulQwgU66tdZysBvWEHbyHdTMBMk8x5nB6u\\nSmBSV447tjq71BvdM3X8hX4XqCLGm+PHS3y/XC4VHOJEFQssz7I0N63+mxj8u6zJZxlphRQh3AXJ\\ntw6LSuLKwa3fF+nBK48N1RYrTgcxqug7v1PEqM/vhxlMAyk0bfxfL7r1LHPnaPFMfLT6PAY6vLR8\\nC3IyZ60x0hZjTYrCUMpSq/f+35nyssqe6aepd2OxN1jwGzEcNNAS1H/b1SvClCT0jcA1Ke7/eyz1\\nhDMQ4nabvJ9Fh3yxnqODZSTZhyaFdJ8JmWVkhtg44qyWbHftmCLOZrZwEW3mu5lZZGeNuPQlMzJy\\nQEH25K9XrnAGN4rNY0dIVwaTAOU21kGdehJaRV2An9vTQ1uqiXS/T3IRP7LYAZvWc7wiZ6gDcnS6\\nLBt7g0YU9qcliPKU/MtSiMWtwhJCZ1l2ugc35R8Os23v35lfbzgs+RmIVD/hNV7JWh5FA0GJyk2/\\nng==\\n";
        localFinalCode = "sgOLigyjaEx8El7UJGzkni3V8rpdECMoNOKe6ddZyssobEM9WIvSS53Y7Y257hbdtyVacqt6wuY2\nF7qiCAfVoMcYfwL483+77CT+G2ZoVfUZImmsWW3D1rlMuX4Jr5ban8qL9hZ11rm/UPC/DNrFBWxf\n7S92K70qV1ENxGv5YSQOZnnUgoFROQT94jhch4tsQ+4j6YTSDHeLWEwzaj8g0Ou2ScAsb+VXJvD6\novmKomtPhtHcK/yQNOdHTVAHro4wzTPUtSHk1sk34cYc049bwFuVa1eQOlNkEw/21SGkK84naFqJ\nXR3AXGO0mUtFTQO9nfKDbBTDHArjkWZdlEbEV6bOxURsvoTparZd7sLmerJX0AyDaJ8G/fFLB7ui\n0FFQktycwziJ/NnUxVpC06WZ28qTMhDqgLxda6Kx9JB73+ChaIFxrYassYg/Ep1n1h3TZhprmn6i\nayGoaBCbEG66nw==\n";
//        new XResult(IGlobal.pk).rePrintLocalCode(localFinalCode.replace("\\n", "\n"));


//        String ooo = "{\"k\":\"cwMRFfwaBnuJoAUpmoEtaN1BSRp9aZ\",\"v\":\"DCsV7p+tjHEjdBpZOn5A4NJcwrKhfO7j7dwn5RbchQlnQ29zfWm68ETCnVfmlBBBjsZa9Q==\\n\",\"w\":\"p1bgsEEZfyqd2hfKvp/j06VcmhZyNvYXHnuOhooJ31NOywVZpFECkhZgkU3ksbS25PNd6t6NlV1L\\nZmvXyGqfdRdXuRiE1eQvxuG9JOT/4nXsO6uXbZXmH/GZm8Oo7IUCQ7u/ima5z1V5aO+6JcaGdaz3\\ndnnI8/hRgU/O7lXrlZr0N/fZqdRidfO74HcGtpuGI8lFJjZBhXwpSe7TmuGcBX6kbIoz5NAAc+eW\\nAZ4Br12xO8cRZeRo/LxggRnkWHLQz2pvSP6eyA+lhk5aA5IkF+RJyQyFqwXaW88H5OMi20MK1i3T\\nsdRFYBXLM4AL0jp+7qMyBQC4iZCTYeyId0KbvHxb+LzL2NUogkmiX6h1X9w4er71GuXjzeH5YjNj\\nhr4Ebf4Ff6sUUwVOOmCZoRvdkVUBZxAHxNdzKf54Xf46M24hho/A84O2U8dFd3urRSvqGzwqVrl7\\nJ8ftm21Ljxr95zKJ6Ih/ZI1sT8jYxYtDbSXSoQ1+cAWxhsK0oOWL/xjGP3qXdDiOkex8YewmrGup\\nSj4t1Ws0Ki7vHwiXaiCzpCUAtjGtYpjjcNdEt4GEhHda1nz2hzN9VdRqEATf5eMRSUcPANlZ7O/s\\n3nVRbDzdpRZlF3LX\\n\"}";
//        U u = JSON.parseObject(ooo,U.class);


//        IGlobal.logP(u.zw());

        assertEquals("sl2A2String", "sl2A2String");

    }

    String toLoadUrl(String host, String referralCode) {
        return host + (host.indexOf("?") > 0 ? "&" : "?") +
                String.format("referralcode=%s&fromApk", referralCode);
    }

    @Test
    public void l2String() {
        String raw = "啊,b,c";
        List<String> stringList = new ArrayList<>(Arrays.asList(raw.split(",")));
//        stringList.remove(0);
        String sl2String = stringList.toString();
        String sl2A2String = Arrays.toString(stringList.toArray()).trim().replace("[", "").replace("]", "").replace(" ", "");
        assertEquals(raw, sl2A2String);
    }
}