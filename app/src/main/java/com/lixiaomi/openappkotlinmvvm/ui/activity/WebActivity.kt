package com.lixiaomi.openappkotlinmvvm.ui.activity

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.LinearLayoutCompat
import android.view.View

import com.lixiaomi.mvvmbaselib.base.BaseActivity
import com.lixiaomi.openappkotlinmvvm.R
import com.lixiaomi.openappkotlinmvvm.databinding.ActivityWebviewBinding
import com.lixiaomi.openappkotlinmvvm.mv.BaseViewModelImpl
import com.lixiaomi.openappkotlinmvvm.utils.WEB_VIEW_TITLE
import com.lixiaomi.openappkotlinmvvm.utils.WEB_VIEW_URL
import com.lixiaomi.openappkotlinmvvm.view.ProgressWebview
import com.tencent.smtt.sdk.WebView

/**
 * @describe：<br>
 * @author：Xiaomi<br>
 * @createTime：2019/8/22<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
class WebActivity :
    BaseActivity<WebActivityLifecycle, ActivityWebviewBinding, BaseViewModelImpl>() {
    var mTopLeftLy: LinearLayoutCompat? = null
    var mTopRightLy: LinearLayoutCompat? = null
    var mTopTitleTv: AppCompatTextView? = null

    var mWebViewUrl: String = ""
    var mTitle: String = ""

    var mWebView: ProgressWebview? = null

    override fun initData() {
        mWebViewUrl = intent.getStringExtra(WEB_VIEW_URL)
        mTitle = intent.getStringExtra(WEB_VIEW_TITLE)
    }

    override fun setLayout(): Int {
        return R.layout.activity_webview
    }

    override fun initView(savedInstanceState: Bundle?) {
        mWebView = findViewById(R.id.web_view)
        mTopLeftLy = findViewById(R.id.top_back_ly)
        mTopRightLy = findViewById(R.id.top_right_ly)
        mTopTitleTv = findViewById(R.id.top_title_tv)

        mTopTitleTv?.text = mTitle
        mTopLeftLy?.setOnClickListener {
            if (mWebView!!.canGoBack()) {
                mWebView!!.goBack()
            } else {
                finish()
            }
        }
        mTopRightLy?.visibility = View.INVISIBLE

        mWebView?.webViewClient = object : com.tencent.smtt.sdk.WebViewClient() {
            override fun shouldOverrideUrlLoading(webView: WebView?, s: String?): Boolean {
                mWebView!!.loadUrl(s)
                return true
            }
        }
        mWebView?.settings?.javaScriptEnabled = true
        mWebView?.loadUrl(mWebViewUrl)
    }

    override fun onBackPressed() {
        if (mWebView!!.canGoBack()) {
            mWebView!!.goBack()
        } else {
            finish()
        }
    }

    override fun startListenerData() {
    }

    override fun createLifeCycle(): WebActivityLifecycle {
        return WebActivityLifecycle(this)
    }

    override fun creatViewModel(): BaseViewModelImpl {
        return ViewModelProviders.of(this).get(BaseViewModelImpl::class.java)
    }

    override fun setStatusBarColor(): Int {
        return R.color.default_color
    }
}