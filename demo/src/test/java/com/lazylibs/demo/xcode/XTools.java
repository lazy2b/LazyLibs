//package com.lazylibs.demo.xcode;
//
//import com.tring.libs.IGlobal;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public interface XTools {
//
//    List<Project> PROJECT_URLS = new ArrayList<Project>() {
//        {
////            add(new Project("b1", "betfiery") {
////                {
////                    develop = new UrlConfig("https://client-urls.pages.dev/b1/env/version-dev.json", "https://client-urls.pages.dev/b1/env/urls-dev.json", "https://game-dev.pre-release.xyz");
////                    preview = new UrlConfig("https://client-urls.pages.dev/b1/env/version-pre.json", "https://client-urls.pages.dev/b1/env/urls-pre.json", "https://release.catgame.top");
////                    online = new UrlConfig("https://client-urls.pages.dev/b1/version.json", "https://client-urls.pages.dev/b1/urls.json", "https://betfiery.com");
////                }
////            });
////            add(new Project("b2", "aajogo") {
////                {
////                    develop = new UrlConfig("https://client-urls.pages.dev/b2/env/version-dev.json", "https://client-urls.pages.dev/b2/env/urls-dev.json", "https://aajogo.pre-release.xyz");
////                    preview = new UrlConfig("https://client-urls.pages.dev/b2/env/version-pre.json", "https://client-urls.pages.dev/b2/env/urls-pre.json", "https://aajogo-release.pre-release.xyz");
////                    online = new UrlConfig("https://client-urls.pages.dev/b2/version.json", "https://client-urls.pages.dev/b2/urls.json", "https://aajogo.com");
////                }
////            });
////            add(new Project("b3", "rico100") {
////                {
////                    develop = new UrlConfig("https://client-urls.pages.dev/b3/env/version-dev.json", "https://client-urls.pages.dev/b3/env/urls-dev.json", "https://b3.pre-release.xyz");
////                    preview = new UrlConfig("https://client-urls.pages.dev/b3/env/version-pre.json", "https://client-urls.pages.dev/b3/env/urls-pre.json", "https://b3-release.pre-release.xyz");
////                    online = new UrlConfig("https://client-urls.pages.dev/b3/version.json", "https://client-urls.pages.dev/b3/urls.json", "https://rico100.com");
////                }
////            });
////            add(new Project("v1", "vang") {
////                {
////                    develop = new UrlConfig("https://client-urls.pages.dev/v1/env/version-dev.json", "https://client-urls.pages.dev/v1/env/urls-dev.json", "https://888vang.pre-release.xyz");
////                    preview = new UrlConfig("https://client-urls.pages.dev/v1/env/version-pre.json", "https://client-urls.pages.dev/v1/env/urls-pre.json", "https://888vang-release.pre-release.xyz");
////                    online = new UrlConfig("https://client-urls.pages.dev/v1/version.json", "https://client-urls.pages.dev/v1/urls.json", "https://888vang.com");
////                }
////            });
////            add(new Project("m1", "mexlucky") {
////                {
////                    develop = new UrlConfig("https://client-urls.pages.dev/m1/env/version-dev.json", "https://client-urls.pages.dev/m1/env/urls-dev.json", "https://mexlucky.pre-release.xyz");
////                    preview = new UrlConfig("https://client-urls.pages.dev/m1/env/version-pre.json", "https://client-urls.pages.dev/m1/env/urls-pre.json", "https://mexlucky-release.pre-release.xyz");
////                    online = new UrlConfig("https://client-urls.pages.dev/m1/version.json", "https://client-urls.pages.dev/m1/urls.json", "https://mexlucky.com");
////                }
////            });
//            add(new Project("m2", "mexswin") {
//                {
//                    develop = new UrlConfig("https://client-urls.pages.dev/m2/env/version-dev.json", "https://client-urls.pages.dev/m2/env/urls-dev.json", "https://m2.pre-release.xyz/");
//                    preview = new UrlConfig("https://client-urls.pages.dev/m2/env/version-pre.json", "https://client-urls.pages.dev/m2/env/urls-pre.json", "https://m2-release.pre-release.xyz/");
//                    online = new UrlConfig("https://client-urls.pages.dev/m2/version.json", "https://client-urls.pages.dev/m2/urls.json", "https://mexswin.com");
//                }
//            });
////            add(new Project("p1", "phlwin") {
////                {
////                    develop = new UrlConfig("https://client-urls.pages.dev/p1/env/version-dev.json", "https://client-urls.pages.dev/p1/env/urls-dev.json", "https://phlwin.pre-release.xyz");
////                    preview = new UrlConfig("https://client-urls.pages.dev/p1/env/version-pre.json", "https://client-urls.pages.dev/p1/env/urls-pre.json", "https://phlwin-release.pre-release.xyz");
////                    online = new UrlConfig("https://client-urls.pages.dev/p1/version.json", "https://client-urls.pages.dev/p1/urls.json", "https://phlwin.app,https://phlwin.best,https://phlwin.bid,https://phlwin.buzz,https://phlwin.casino,https://phlwin.cc,https://phlwin.club,https://phlwin.co,https://phlwin.date,https://phlwin.icu,https://phlwin.info,https://phlwin.io,https://phlwin.life,https://phlwin.live,https://phlwin.me,https://phlwin.net,https://phlwin.one,https://phlwin.org,https://phlwin.ph,https://phlwin.shop,https://phlwin.site,https://phlwin.top,https://phlwin.win,https://phlwin.xyz");
////                }
////            });
////            add(new Project("p2", "philucky") {
////                {
////                    develop = new UrlConfig("https://client-urls.pages.dev/p2/env/version-dev.json", "https://client-urls.pages.dev/p2/env/urls-dev.json", "https://philucky.pre-release.xyz");
////                    preview = new UrlConfig("https://client-urls.pages.dev/p2/env/version-pre.json", "https://client-urls.pages.dev/p2/env/urls-pre.json", "https://philucky-release.pre-release.xyz");
////                    online = new UrlConfig("https://client-urls.pages.dev/p2/version.json", "https://client-urls.pages.dev/p2/urls.json", "https://philucky.live,https://xtaya.com,https://philucky.pro,https://philucky.net,https://philucky.top,https://philucky.online,https://philucky.app,https://philucky.asia,https://philucky.best,https://philucky.bid,https://philucky.biz,https://philucky.blog,https://philucky.buzz,https://philucky.casino,https://philucky.cc,https://philucky.click,https://philucky.cloud,https://philucky.club,https://philucky.co,https://philucky.ltd,https://philucky.me,https://philucky.men,https://philucky.mobi,https://philucky.mx,https://philucky.net,https://philucky.network,https://philucky.news,https://philucky.nl,https://philucky.one,https://philucky.org,https://philucky.plus,https://philucky.pw,https://philucky.red,https://philucky.run,https://philucky.sh,https://philucky.shop,https://philucky.site,https://philucky.store,https://philucky.today,https://philucky.trade,https://philucky.tv");
////                }
////            });
////            add(new Project("p3", "apanalo") {
////                {
////                    develop = new UrlConfig("https://client-urls.pages.dev/p3/env/version-dev.json", "https://client-urls.pages.dev/p3/env/urls-dev.json", "https://apanalo.pre-release.xyz");
////                    preview = new UrlConfig("https://client-urls.pages.dev/p3/env/version-pre.json", "https://client-urls.pages.dev/p3/env/urls-pre.json", "https://apanalo-release.pre-release.xyz");
////                    online = new UrlConfig("https://client-urls.pages.dev/p3/version.json", "https://client-urls.pages.dev/p3/urls.json", "https://apanalo.com,https://555top.win,https://555topwin.com,https://555winner.com,https://555winner.top,https://55winner.com,https://666top.win,https://666topwin.com,https://666winner.top,https://777maxwin.com,https://777top.win");
////                }
////            });
////            add(new Project("p4", "xtaya") {
////                {
////                    develop = new UrlConfig("https://client-urls.pages.dev/p4/env/version-dev.json", "https://client-urls.pages.dev/p4/env/urls-dev.json", "https://xtaya.pre-release.xyz");
////                    preview = new UrlConfig("https://client-urls.pages.dev/p4/env/version-pre.json", "https://client-urls.pages.dev/p4/env/urls-pre.json", "https://xtaya-release.pre-release.xyz");
////                    online = new UrlConfig("https://client-urls.pages.dev/p4/version.json", "https://client-urls.pages.dev/p4/urls.json", "https://xtaya.com");
////                }
////            });
//
//
//
//
//        }
//    };
//
//    static List<Project> xCode() {
//        XCode xCode = new XCode(IGlobal.pk);
//        for(Project project: PROJECT_URLS){
//            project.xCode(xCode);
//        }
//        return PROJECT_URLS;
//    }
//}
