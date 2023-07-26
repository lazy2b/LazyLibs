package com.lazylibs.weber;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.text.TextUtils;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class LazyWebViewClient extends WebViewClient {

    public LazyWebViewClient(LazyFinishedHelper finishedHelper) {
        this.finishedHelper = finishedHelper;
    }

    LazyFinishedHelper finishedHelper;

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        finishedHelper.toPageStarted(url);
        super.onPageStarted(view, url, favicon);
//        WebViewHelper.logE("onPageStarted", url);
    }

    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
//        WebViewHelper.logE("onPageFinished", url);
    }

    @SuppressLint("WebViewClientOnReceivedSslError")
    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext().getApplicationContext());
        int message = R.string.ssl_error;
        switch (error.getPrimaryError()) {
            case SslError.SSL_UNTRUSTED:
                message = R.string.ssl_untrusted;
                break;
            case SslError.SSL_DATE_INVALID:
                message = R.string.ssl_date_invalid;
                break;
            case SslError.SSL_EXPIRED:
                message = R.string.ssl_expired;
                break;
            case SslError.SSL_IDMISMATCH:
                message = R.string.ssl_idmismatch;
                break;
            case SslError.SSL_NOTYETVALID:
                message = R.string.ssl_notypetvalid;
                break;
        }
        LazyWebHelper.logE("WebError", String.format("Failed to access %s. Error: %s", error.getUrl(), view.getResources().getString(message)));
        builder.setTitle(R.string.ssl_tips);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.ssl_load, (dialog, which) -> handler.proceed());
        builder.setNegativeButton(R.string.ssl_abort, (dialog, which) -> handler.cancel());
        final AlertDialog dialog = builder.create();
        dialog.show();
    }


    public void onReceivedError(WebView view, int errorCode, String description, String fUrl) {
        finishedHelper.toReceivedError();
//        WebViewHelper.logE("onReceivedError", String.format("=>%s:%s:%s", errorCode, description, view.getUrl() +" >>> " + fUrl));
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return filterUrlScheme(view, url) || super.shouldOverrideUrlLoading(view, url);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        Uri uri = request.getUrl();
        return (uri != null && filterUrlScheme(view, uri.toString())) || super.shouldOverrideUrlLoading(view, request);
    }

    private boolean filterUrlScheme(WebView view, String url) {
        if (!TextUtils.isEmpty(url)) {
            if (!url.startsWith("http")) {
                LazyWebHelper.logE("OverrideUrl", url);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                if (intent.resolveActivity(view.getContext().getPackageManager()) != null) {
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    view.getContext().startActivity(intent);
                } else {
                    LazyWebHelper.logE("OverrideUrl", "Application is not installed. Please install it to use this feature.");
//                    Toast.makeText(view.getContext(), "Application is not installed. Please install it to use this feature.", Toast.LENGTH_LONG).show();
                }
                return true;
            }
        }
        return false;
    }
}