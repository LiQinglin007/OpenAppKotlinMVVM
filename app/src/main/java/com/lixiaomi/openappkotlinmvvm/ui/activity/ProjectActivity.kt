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
import com.lixiaomi.openappkotlinmvvm.adapter.ProjectAdapter
import com.lixiaomi.openappkotlinmvvm.bean.ProjectBean
import com.lixiaomi.openappkotlinmvvm.databinding.ActivityProjectListBinding
import com.lixiaomi.openappkotlinmvvm.mv.activity.ProjectActivityViewModelImpl
import com.lixiaomi.openappkotlinmvvm.utils.WEB_VIEW_TITLE
import com.lixiaomi.openappkotlinmvvm.utils.WEB_VIEW_URL

/**
 * @describe：<br>
 * @author：Xiaomi<br>
 * @createTime：2019/8/22<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
class ProjectActivity :
    BaseActivity<ProjectActivityLifeCycle, ActivityProjectListBinding, ProjectActivityViewModelImpl>() {

    var mTopLeftLy: LinearLayoutCompat? = null
    var mTopRightLy: LinearLayoutCompat? = null
    var mTopTitleTv: AppCompatTextView? = null


    var mDataList = ArrayList<ProjectBean.Data.DataX>()
    var mPage = 0
    var mRefreshIng = false
    var mLoadMoreIng = false

    var mProjectRefresh: SwipeRefreshLayout? = null
    var mProjectRecy: RecyclerView? = null

    var mAdapter: ProjectAdapter? = null
    override fun initData() {

    }

    override fun setLayout(): Int {
        return R.layout.activity_project_list
    }

    override fun initView(savedInstanceState: Bundle?) {
        mTopLeftLy = findViewById(R.id.top_back_ly)
        mTopRightLy = findViewById(R.id.top_right_ly)
        mTopTitleTv = findViewById(R.id.top_title_tv)

        mTopTitleTv?.text = "最新项目"
        mTopLeftLy?.setOnClickListener { finish() }
        mTopRightLy?.visibility = View.INVISIBLE
        mProjectRefresh = findViewById(R.id.project_refresh)
        mProjectRecy = findViewById(R.id.project_recy)


        mProjectRecy?.layoutManager = LinearLayoutManager(this)
        mAdapter = ProjectAdapter(R.layout.item_project, mDataList)
        mProjectRecy?.adapter = mAdapter

        mProjectRefresh?.isEnabled = true
        mProjectRefresh?.setColorSchemeColors(
            resources.getColor(R.color.color_51D8BA),
            resources.getColor(R.color.colorAccent),
            resources.getColor(R.color.colorPrimary),
            resources.getColor(R.color.default_color)
        )
        mProjectRefresh?.setDistanceToTriggerSync(300)
        mProjectRefresh?.setProgressBackgroundColorSchemeResource(R.color.color_white)
        mProjectRefresh?.setSize(SwipeRefreshLayout.LARGE)
        mProjectRefresh?.setOnRefreshListener {
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
                    mProjectRefresh?.isEnabled = false
                    getData()
                }
            }
        }, mProjectRecy)

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
        mViewModel.getProjectData(showLoading, mPage)
    }

    override fun startListenerData() {
        mViewModel.getmShowLoading().observe(this, Observer {
            setLoading(it!!)
        })
        mViewModel.getmToastMessage().observe(this, Observer {
            T.shortToast(this, it)
        })

        mViewModel.mProjectListData.observe(this, Observer {
            mDataList.clear()
            mDataList.addAll(it!!)
            mAdapter?.replaceData(mDataList)

            mRefreshIng = false
            mLoadMoreIng = false
            mProjectRefresh?.setEnabled(true)
            mProjectRefresh?.isRefreshing = false
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

    override fun createLifeCycle(): ProjectActivityLifeCycle {
        return ProjectActivityLifeCycle(this)
    }

    override fun creatViewModel(): ProjectActivityViewModelImpl {
        return ViewModelProviders.of(this).get(ProjectActivityViewModelImpl::class.java)
    }

    override fun setStatusBarColor(): Int {
        return R.color.default_color
    }
}