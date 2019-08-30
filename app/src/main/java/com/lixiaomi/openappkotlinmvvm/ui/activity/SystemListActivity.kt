package com.lixiaomi.openappkotlinmvvm.ui.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
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
import com.lixiaomi.mvvmbaselib.base.BaseLifeCycle
import com.lixiaomi.openappkotlinmvvm.R
import com.lixiaomi.openappkotlinmvvm.adapter.ProjectAdapter
import com.lixiaomi.openappkotlinmvvm.adapter.SystemActivityAdapter
import com.lixiaomi.openappkotlinmvvm.bean.ArticleBean
import com.lixiaomi.openappkotlinmvvm.bean.TreeArticleListBean
import com.lixiaomi.openappkotlinmvvm.databinding.ActivityProjectListBinding
import com.lixiaomi.openappkotlinmvvm.databinding.ActivitySystemListBinding
import com.lixiaomi.openappkotlinmvvm.mv.activity.ProjectActivityViewModelImpl
import com.lixiaomi.openappkotlinmvvm.mv.activity.SystemListActivityViewModelImpl
import com.lixiaomi.openappkotlinmvvm.utils.SYSTEM_TYPE_ID
import com.lixiaomi.openappkotlinmvvm.utils.SYSTEM_TYPE_TITLE
import com.lixiaomi.openappkotlinmvvm.utils.WEB_VIEW_TITLE
import com.lixiaomi.openappkotlinmvvm.utils.WEB_VIEW_URL

/**
 * @describe：<br>
 * @author：Xiaomi<br>
 * @createTime：2019/8/22<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
class SystemListActivity :
    BaseActivity<SystemListActivityLifecycle, ActivitySystemListBinding, SystemListActivityViewModelImpl>() {

    var mTopLeftLy: LinearLayoutCompat? = null
    var mTopRightLy: LinearLayoutCompat? = null
    var mTopTitleTv: AppCompatTextView? = null


    var mDataList = ArrayList<TreeArticleListBean.Data.DataX>()
    var mPage = 0
    var mRefreshIng = false
    var mLoadMoreIng = false

    var mRefreshLy: SwipeRefreshLayout? = null
    var mSystemRecy: RecyclerView? = null

    var mAdapter: SystemActivityAdapter? = null

    var mCId: Int? = null
    var mTitle: String = ""


    override fun initData() {
        mCId = intent.getIntExtra(SYSTEM_TYPE_ID, -1)
        mTitle = intent.getStringExtra(SYSTEM_TYPE_TITLE)
    }

    override fun setLayout(): Int {
        return R.layout.activity_system_list
    }

    override fun initView(savedInstanceState: Bundle?) {
        mTopLeftLy = findViewById(R.id.top_back_ly)
        mTopRightLy = findViewById(R.id.top_right_ly)
        mTopTitleTv = findViewById(R.id.top_title_tv)

        mTopTitleTv?.text = mTitle
        mTopLeftLy?.setOnClickListener { finish() }
        mTopRightLy?.visibility = View.INVISIBLE


        mRefreshLy = findViewById(R.id.system_refresh)
        mSystemRecy = findViewById(R.id.system_recy)


        mSystemRecy?.layoutManager = LinearLayoutManager(this)
        mAdapter = SystemActivityAdapter(R.layout.item_sys_article, mDataList)
        mSystemRecy?.adapter = mAdapter

        mRefreshLy?.isEnabled = true
        mRefreshLy?.setColorSchemeColors(
            resources.getColor(R.color.color_51D8BA),
            resources.getColor(R.color.colorAccent),
            resources.getColor(R.color.colorPrimary),
            resources.getColor(R.color.default_color)
        )
        mRefreshLy?.setDistanceToTriggerSync(300)
        mRefreshLy?.setProgressBackgroundColorSchemeResource(R.color.color_white)
        mRefreshLy?.setSize(SwipeRefreshLayout.LARGE)
        mRefreshLy?.setOnRefreshListener {
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
                    mRefreshLy?.isEnabled = false
                    getData()
                }
            }
        }, mSystemRecy)

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
        if (mCId != -1) {
            mViewModel.getSystemArticle(showLoading, mPage, "${mCId}")
        }
    }


    override fun startListenerData() {
        mViewModel.getmShowLoading().observe(this, Observer {
            setLoading(it!!)
        })
        mViewModel.getmToastMessage().observe(this, Observer {
            T.shortToast(this, it)
        })

        mViewModel.mTreeArticleListData.observe(this, Observer {
            mDataList.clear()
            mDataList.addAll(it!!)
            mAdapter?.replaceData(mDataList)

            mRefreshIng = false
            mLoadMoreIng = false
            mRefreshLy?.setEnabled(true)
            mRefreshLy?.isRefreshing = false
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

    override fun createLifeCycle(): SystemListActivityLifecycle {
        return SystemListActivityLifecycle(this)
    }

    override fun creatViewModel(): SystemListActivityViewModelImpl {
        return ViewModelProviders.of(this).get(SystemListActivityViewModelImpl::class.java)
    }

    override fun setStatusBarColor(): Int {
        return R.color.default_color
    }
}