package com.lazylibs.adsenter;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;

import com.lazylibs.utils.FragmentUtils;
import com.lazylibs.utils.Logger;
import com.lazylibs.weber.IWebHandler;
import com.lazylibs.weber.LazyWebFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;

public class Patos extends AppCompatActivity {

    private ViewFlipper viewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewFlipper = new ViewFlipper(this);
        viewFlipper.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(viewFlipper);
//        viewFlipper.setInAnimation(AnimationUtils.loadAnimation(viewFlipper.getContext(), R.anim.push_left_in));
//        viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(viewFlipper.getContext(), R.anim.push_right_out));
        vLoading = LayoutInflater.from(this).inflate(R.layout.tpl_loading, viewFlipper, false);
        if (Enter.isAgreePatos(this)) {
            v2Main();
        } else {
            v2Patos();
        }
    }

    private View vPatos, vLoading;

    private void v2Patos() {
        Logger.d("Patos.v2Patos ");
        viewFlipper.addView(vLoading);
        viewFlipper.showNext();

        vPatos = LayoutInflater.from(this).inflate(R.layout.tpl_patos, viewFlipper, false);
        TextView tvAgree = vPatos.findViewById(R.id.tv_agree);
        TextView tvPatos = vPatos.findViewById(R.id.tv_patos);
        tvAgree.setOnClickListener(v -> {
            Enter.setAgreePatos(this);
            v2Main();
        });

        String patos = Enter.skipper().getPatos();
        if (patos.startsWith("http://") || patos.startsWith("https://") || patos.startsWith("file://")) {
            Logger.d("Patos.v2Patos 1");
            vPatos.findViewById(R.id.wb_patos).setVisibility(View.VISIBLE);
            FragmentUtils.replaceFragmentToActivity(R.id.wb_patos, getSupportFragmentManager(), LazyWebFragment.newInstance(patos,
//                    new HashMap<String, Object>() {{
//                        class Events {
//                            public String key;
//                            public String value;
//                            public String currency;
//
//                            @Override
//                            public String toString() {
//                                return "Events{" +
//                                        "key='" + key + '\'' +
//                                        ", value='" + value + '\'' +
//                                        ", currency='" + currency + '\'' +
//                                        '}';
//                            }
//                        }
//                        put("AndroidWebView", new Object() {
//                            @JavascriptInterface
//                            public void firebaseEvent(String args) {
//                                Logger.d("来自Js的参数！！！：==》" + args);
//                                try {
//                                    JSONObject jobj = new JSONObject(args);
//                                    Toast.makeText(Patos.this, "来自Js的参数！！！：==》" + jobj.optString("keyddd", "sdfsd"), Toast.LENGTH_LONG).show();
//                                } catch (JSONException e) {
//                                    throw new RuntimeException(e);
//                                }
//                            }
//
//                        });
//
//                    }}
                    null
            ).setExtraWebHandler(new IWebHandler() {
                @Override
                public void onRealPageFinished(String url, boolean isReceivedError) {
                    tvAgree.setVisibility(View.VISIBLE);
                }
            }));
        } else {
            Logger.d("Patos.v2Patos 2");
            tvPatos.setText(Html.fromHtml(patos, Html.FROM_HTML_MODE_COMPACT));
            tvPatos.setVisibility(View.VISIBLE);
            tvAgree.setVisibility(View.VISIBLE);
        }
        viewFlipper.addView(vPatos);
        viewFlipper.showNext();
        viewFlipper.removeView(vLoading);
    }

    private void v2Main() {
        Logger.d("Patos.v2Main ");
        viewFlipper.addView(vLoading);
        viewFlipper.showNext();
        if (vPatos != null) viewFlipper.removeView(vPatos);
        Enter.skipper().skipPatos(this);
    }
}