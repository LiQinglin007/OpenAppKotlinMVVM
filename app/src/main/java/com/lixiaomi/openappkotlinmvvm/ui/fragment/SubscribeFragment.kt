package com.lixiaomi.openappkotlinmvvm.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.lixiaomi.baselib.ui.dialog.dialoglist.MiDialogList
import com.lixiaomi.baselib.utils.T
import com.lixiaomi.mvvmbaselib.base.BaseFragment
import com.lixiaomi.openappkotlinmvvm.R
import com.lixiaomi.openappkotlinmvvm.adapter.SubscribeFragmentAdapter
import com.lixiaomi.openappkotlinmvvm.bean.WXArticleAuthorlistBean
import com.lixiaomi.openappkotlinmvvm.bean.WXArticleListBean
import com.lixiaomi.openappkotlinmvvm.databinding.FragmentSubscribeBinding
import com.lixiaomi.openappkotlinmvvm.mv.SubscribeFragmentViewModelImpl
import com.lixiaomi.openappkotlinmvvm.ui.activity.WebActivity
import com.lixiaomi.openappkotlinmvvm.utils.WEB_VIEW_TITLE
import com.lixiaomi.openappkotlinmvvm.utils.WEB_VIEW_URL

/**
 * @describe：公众号<br>
 * @author：Xiaomi<br>
 * @createTime：2019/8/21<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
class SubscribeFragment :
    BaseFragment<SubscribeFragmentLifecycle, FragmentSubscribeBinding, SubscribeFragmentViewModelImpl>() {

    companion object {
        fun getInstance(): SubscribeFragment {
            return SubscribeFragmentHolde.mSubscribeFragment
        }
    }

    object SubscribeFragmentHolde {
        val mSubscribeFragment = SubscribeFragment()
    }


    var mRefresh: SwipeRefreshLayout? = null
    var mSubAuthorTv: TextView? = null
    var mSubRecy: RecyclerView? = null
    var mSubscribeFragmentAdapter: SubscribeFragmentAdapter? = null

    /**
     * 作者列表
     */
    var mAuthorListData = ArrayList<WXArticleAuthorlistBean.Data>()

    /**
     * 文章列表
     */
    var mArticleListData = ArrayList<WXArticleListBean.Data.DataX>()

    /**
     * 当前选择的哪个作者
     */
    var mChooseIndex = 0

    /**
     * 现在加载到了第多少页
     */
    var mPage = 0

    var mRefreshIng = false
    var mLoadMoreIng = false


    override fun initData() {

    }

    override fun createLifeCycle(): SubscribeFragmentLifecycle {
        return SubscribeFragmentLifecycle(getContext())
    }

    override fun setLayout(): Int {
        return R.layout.fragment_subscribe
    }

    override fun initView(rootView: View?, savedInstanceState: Bundle?) {
        mRefresh = rootView?.findViewById(R.id.sub_sr)
        mSubAuthorTv = rootView?.findViewById(R.id.sub_author_tv)
        mSubRecy = rootView?.findViewById(R.id.sub_recy)

        mSubRecy?.layoutManager = LinearLayoutManager(activity)
        mSubscribeFragmentAdapter = SubscribeFragmentAdapter(R.layout.item_wx_article, mArticleListData)
        mSubRecy?.adapter = mSubscribeFragmentAdapter
        mSubscribeFragmentAdapter!!.setOnItemClickListener { adapter, view, position ->
            startActivity(
                Intent(activity, WebActivity::class.java)
                    .putExtra(WEB_VIEW_URL, mArticleListData.get(position).link)
                    .putExtra(WEB_VIEW_TITLE, mArticleListData.get(position).title)
            )
        }

        mSubAuthorTv?.setOnClickListener {
            MiDialogList<String>(activity)
                .builder()
                .setData(mAuthorListData)
                .setTitle("选择公众号")
                .setGravity(MiDialogList.MILIST_DIALOG_BOTTOM)
                .setReturnType(MiDialogList.MILIST_RETURN_SINGLE)
                .setCallBack {
                    mChooseIndex = it.get(0)
                    mPage = 0
                    mSubAuthorTv!!.text = mAuthorListData.get(mChooseIndex).name
                    getData(true)
                }
                .show()
        }

        mSubscribeFragmentAdapter!!.setOnLoadMoreListener(object : BaseQuickAdapter.RequestLoadMoreListener {
            override fun onLoadMoreRequested() {
                if (!mLoadMoreIng || !mRefreshIng) {
                    mLoadMoreIng = true
                    ++mPage
                    getData()
                    mRefresh?.setEnabled(false)
                }
            }
        }, mSubRecy)

        mRefresh?.setEnabled(false)
        mRefresh?.setColorSchemeColors(
            resources.getColor(R.color.color_51D8BA),
            resources.getColor(R.color.colorAccent),
            resources.getColor(R.color.colorPrimary),
            resources.getColor(R.color.default_color)
        )
        mRefresh?.setDistanceToTriggerSync(300)
        mRefresh?.setProgressBackgroundColorSchemeColor(Color.WHITE)
        mRefresh?.setSize(SwipeRefreshLayout.LARGE)
        mRefresh?.setOnRefreshListener {
            if (!mLoadMoreIng || !mRefreshIng) {
                mPage = 0
                mRefreshIng = true
                mSubscribeFragmentAdapter?.setEnableLoadMore(false)
                getData()
            }
        }

        mViewModel.getWXAuthorList()
    }

    fun getData(showLoading: Boolean = false) {
        mViewModel.getWXArticleList(showLoading, "${mAuthorListData.get(mChooseIndex).id}", mPage)
    }

    override fun startListenerData() {
        mViewModel.getmShowLoading().observe(this,
            Observer<Boolean> { setLoading(it!!) })
        mViewModel.getmToastMessage().observe(this, Observer<String> {
            T.shortToast(context, it)
        })

        mViewModel.mAuthorData.observe(this, Observer<ArrayList<WXArticleAuthorlistBean.Data>> {
            mAuthorListData.clear()
            mAuthorListData.addAll(it!!)
            if (mAuthorListData.size > 0) {
                mSubAuthorTv?.text = mAuthorListData.get(mChooseIndex).name
                getData(true)
            }
        })

        mViewModel.mArticleData.observe(this, Observer<ArrayList<WXArticleListBean.Data.DataX>> {
            mArticleListData.clear()
            it?.let { mArticleListData.addAll(it) }
            mSubscribeFragmentAdapter?.replaceData(mArticleListData)
            mRefreshIng = false
            mLoadMoreIng = false
            mRefresh?.setEnabled(true)
            mRefresh?.isRefreshing = false
            mSubscribeFragmentAdapter?.setEnableLoadMore(true)
        })

        mViewModel.mEableLoadMore.observe(this, Observer<Boolean> {
            if (it!!) {
                mSubscribeFragmentAdapter?.loadMoreComplete()
            } else {
                mSubscribeFragmentAdapter?.loadMoreEnd()
            }
        })
    }

    override fun creatViewModel(): SubscribeFragmentViewModelImpl {
        return ViewModelProviders.of(getInstance()).get(SubscribeFragmentViewModelImpl::class.java)
    }

}