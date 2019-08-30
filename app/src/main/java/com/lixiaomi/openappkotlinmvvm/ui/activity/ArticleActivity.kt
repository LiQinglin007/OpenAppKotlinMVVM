package com.lixiaomi.openappkotlinmvvm.ui.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.LinearLayoutCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.lixiaomi.baselib.utils.T
import com.lixiaomi.mvvmbaselib.base.BaseActivity
import com.lixiaomi.openappkotlinmvvm.R
import com.lixiaomi.openappkotlinmvvm.adapter.ArticleAdapter
import com.lixiaomi.openappkotlinmvvm.bean.ArticleBean

import com.lixiaomi.openappkotlinmvvm.databinding.ActivityArticleListBinding
import com.lixiaomi.openappkotlinmvvm.mv.activity.ArticleActivityViewModelImpl
import com.lixiaomi.openappkotlinmvvm.utils.WEB_VIEW_TITLE
import com.lixiaomi.openappkotlinmvvm.utils.WEB_VIEW_URL

/**
 * @describe：<br>
 * @author：Xiaomi<br>
 * @createTime：2019/8/22<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
class ArticleActivity :
    BaseActivity<ArticleActivityLifeCycle, ActivityArticleListBinding, ArticleActivityViewModelImpl>() {

    var mTopLeftLy: LinearLayoutCompat? = null
    var mTopRightLy: LinearLayoutCompat? = null
    var mTopTitleTv: AppCompatTextView? = null


    var mDataList = ArrayList<ArticleBean.Data.DataX>()
    var mPage = 0
    var mRefreshIng = false
    var mLoadMoreIng = false

    var mArticleRefresh: SwipeRefreshLayout? = null
    var mArticleRecy: RecyclerView? = null


    var mAdapter: ArticleAdapter? = null
    override fun initData() {

    }

    override fun setLayout(): Int {
        return R.layout.activity_article_list
    }

    override fun initView(savedInstanceState: Bundle?) {
        mTopLeftLy = findViewById(R.id.top_back_ly)
        mTopRightLy = findViewById(R.id.top_right_ly)
        mTopTitleTv = findViewById(R.id.top_title_tv)

        mTopTitleTv?.text = "最新文章"
        mTopLeftLy?.setOnClickListener { finish() }
        mTopRightLy?.visibility = View.INVISIBLE
        mArticleRefresh = findViewById(R.id.article_refresh)
        mArticleRecy = findViewById(R.id.article_recy)

        mArticleRecy?.layoutManager = LinearLayoutManager(this)
        mAdapter = ArticleAdapter(R.layout.item_article, mDataList)
        mArticleRecy?.adapter = mAdapter

        mArticleRefresh?.isEnabled = true
        mArticleRefresh?.setColorSchemeColors(
            resources.getColor(R.color.color_51D8BA),
            resources.getColor(R.color.colorAccent),
            resources.getColor(R.color.colorPrimary),
            resources.getColor(R.color.default_color)
        )
        mArticleRefresh?.setDistanceToTriggerSync(300)
        mArticleRefresh?.setProgressBackgroundColorSchemeResource(R.color.color_white)
        mArticleRefresh?.setSize(SwipeRefreshLayout.LARGE)
        mArticleRefresh?.setOnRefreshListener {
            if (!mLoadMoreIng && !mRefreshIng) {
                mRefreshIng = true
                mPage = 0
                mAdapter?.setEnableLoadMore(false)
                getData()
            }
        }

        mAdapter?.setOnLoadMoreListener(object : BaseQuickAdapter.RequestLoadMoreListener {
            override fun onLoadMoreRequested() {
                if (!mLoadMoreIng && !mRefreshIng) {
                    mLoadMoreIng = true
                    ++mPage
                    mArticleRefresh?.isEnabled = false
                    getData()
                }
            }
        }, mArticleRecy)

        mAdapter?.setOnItemClickListener { adapter, view, position ->
            startActivity(
                Intent(this, WebActivity::class.java)
                    .putExtra(WEB_VIEW_URL, mDataList.get(position).link)
                    .putExtra(WEB_VIEW_TITLE, mDataList.get(position).title)
            )
        }

        getData(true)
    }

    fun getData(showLoading: Boolean = false) {
        mViewModel.getArticleData(showLoading, mPage)
    }

    override fun startListenerData() {
        mViewModel.getmShowLoading().observe(this, Observer {
            setLoading(it!!)
        })
        mViewModel.getmToastMessage().observe(this, Observer {
            T.shortToast(this, it)
        })

        mViewModel.mArticleListData.observe(this, Observer {
            mDataList.clear()
            mDataList.addAll(it!!)
            mAdapter?.replaceData(mDataList)

            mRefreshIng = false
            mLoadMoreIng = false
            mArticleRefresh?.setEnabled(true)
            mArticleRefresh?.isRefreshing = false
            mAdapter?.setEnableLoadMore(true)
        })

        mViewModel.mEableLoadMore.observe(this, Observer {
            if (it!!) {
                mAdapter?.loadMoreComplete()
            } else {
                mAdapter?.loadMoreEnd()
            }
        })
    }

    override fun createLifeCycle(): ArticleActivityLifeCycle {
        return ArticleActivityLifeCycle(this)
    }

    override fun creatViewModel(): ArticleActivityViewModelImpl {
        return ViewModelProviders.of(this).get(ArticleActivityViewModelImpl::class.java)
    }

    override fun setStatusBarColor(): Int {
        return R.color.default_color
    }
}