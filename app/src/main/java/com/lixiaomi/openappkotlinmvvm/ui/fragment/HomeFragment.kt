package com.lixiaomi.openappkotlinmvvm.ui.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.ClipData
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.bigkoo.convenientbanner.ConvenientBanner
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator
import com.bigkoo.convenientbanner.holder.Holder
import com.bigkoo.convenientbanner.listener.OnItemClickListener
import com.chad.library.adapter.base.BaseQuickAdapter
import com.lixiaomi.baselib.utils.LogUtils
import com.lixiaomi.baselib.utils.MiJsonUtil
import com.lixiaomi.baselib.utils.T
import com.lixiaomi.mvvmbaselib.base.BaseFragment
import com.lixiaomi.mvvmbaselib.base.MyPresenterCallBack
import com.lixiaomi.openappkotlinmvvm.R
import com.lixiaomi.openappkotlinmvvm.adapter.ArticleAdapter
import com.lixiaomi.openappkotlinmvvm.adapter.BannerHolderView
import com.lixiaomi.openappkotlinmvvm.adapter.ProjectAdapter
import com.lixiaomi.openappkotlinmvvm.bean.ArticleBean
import com.lixiaomi.openappkotlinmvvm.bean.BannerBean
import com.lixiaomi.openappkotlinmvvm.bean.ProjectBean
import com.lixiaomi.openappkotlinmvvm.bean.UserBean
import com.lixiaomi.openappkotlinmvvm.databinding.FragmentHomeBinding
import com.lixiaomi.openappkotlinmvvm.model.ArticleModelImpl
import com.lixiaomi.openappkotlinmvvm.model.PublicModelImpl
import com.lixiaomi.openappkotlinmvvm.mv.HomeFragmentViewModelImpl
import com.lixiaomi.openappkotlinmvvm.ui.activity.ArticleActivity
import com.lixiaomi.openappkotlinmvvm.ui.activity.ProjectActivity
import com.lixiaomi.openappkotlinmvvm.ui.activity.WebActivity
import com.lixiaomi.openappkotlinmvvm.utils.WEB_VIEW_TITLE
import com.lixiaomi.openappkotlinmvvm.utils.WEB_VIEW_URL
import java.util.*
import kotlin.collections.ArrayList

/**
 * @describe：<br>
 * @author：Xiaomi<br>
 * @createTime：2019/8/20<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
class HomeFragment : BaseFragment<HomeFragmentLifecycle, FragmentHomeBinding, HomeFragmentViewModelImpl>() {

    companion object {
        fun getInstance(): HomeFragment {
            return HomeFragmentHolder.mHomeFragment
        }
    }

    object HomeFragmentHolder {
        val mHomeFragment = HomeFragment()
    }


    var mHomeBanner: ConvenientBanner<BannerBean.Data>? = null
    var mHomeRecyArticle: RecyclerView? = null
    var mHomeRecyProject: RecyclerView? = null
    var mArticleAdapter: ArticleAdapter? = null
    var mProjectAdapter: ProjectAdapter? = null

    var mBannerDataList = ArrayList<BannerBean.Data>()
    var mArticleDataList = ArrayList<ArticleBean.Data.DataX>()
    var mProjectDataList = ArrayList<ProjectBean.Data.DataX>()

    override fun initData() {

    }

    override fun setLayout(): Int {
        return R.layout.fragment_home
    }

    override fun initView(rootView: View, savedInstanceState: Bundle?) {
        mHomeBanner = rootView.findViewById<ConvenientBanner<BannerBean.Data>>(R.id.home_banner)
        mHomeRecyArticle = rootView.findViewById<RecyclerView>(R.id.home_recy_article)
        mHomeRecyProject = rootView.findViewById<RecyclerView>(R.id.home_recy_project)

        mArticleAdapter = ArticleAdapter(R.layout.item_article, mArticleDataList)
        mHomeRecyArticle?.layoutManager = LinearLayoutManager(context)
        mHomeRecyArticle?.adapter = mArticleAdapter
        val view = LayoutInflater.from(context).inflate(R.layout.head_home_article, mHomeRecyArticle, false)
        view.setOnClickListener {
            startActivity(Intent(activity, ArticleActivity::class.java))
        }
        mArticleAdapter!!.addHeaderView(view)



        mArticleAdapter!!.setOnItemClickListener { adapter, view, position ->
            startActivity(
                Intent(activity, WebActivity::class.java)
                    .putExtra(WEB_VIEW_URL, mArticleDataList.get(position).link)
                    .putExtra(WEB_VIEW_TITLE, mArticleDataList.get(position).title)
            )

        }




        mProjectAdapter = ProjectAdapter(R.layout.item_project, mProjectDataList)
        mHomeRecyProject?.layoutManager = LinearLayoutManager(context)
        mHomeRecyProject?.adapter = mProjectAdapter
        val view1 = LayoutInflater.from(context).inflate(R.layout.head_home_project, mHomeRecyProject, false)
        view1.setOnClickListener {
            startActivity(Intent(activity, ProjectActivity::class.java))
        }
        mProjectAdapter!!.addHeaderView(view1)
        mProjectAdapter!!.setOnItemClickListener { adapter, view, position ->
            startActivity(
                Intent(activity, WebActivity::class.java)
                    .putExtra(WEB_VIEW_URL, mProjectDataList.get(position).link)
                    .putExtra(WEB_VIEW_TITLE, mProjectDataList.get(position).title)
            )
        }



        mViewModel.getBannerData()
        mViewModel.getArticleData()
        mViewModel.getProjetData()
    }

    override fun startListenerData() {
        mViewModel.getmShowLoading().observe(this, Observer<Boolean> {
            setLoading(it!!)
        })
        mViewModel.getmToastMessage().observe(this, object : Observer<String> {
            override fun onChanged(t: String?) {
                T.shortToast(context, t)
            }
        })

        mViewModel.mBannerData.observe(this, object : Observer<java.util.ArrayList<BannerBean.Data>> {
            override fun onChanged(t: java.util.ArrayList<BannerBean.Data>?) {
                mBannerDataList.clear()
                mBannerDataList.addAll(t!!)
                mHomeBanner
                    ?.setPages(object : CBViewHolderCreator {
                        override fun createHolder(itemView: View?): Holder<*> {
                            return BannerHolderView(itemView!!)
                        }

                        override fun getLayoutId(): Int {
                            return R.layout.item_localimage
                        }
                    }, mBannerDataList)
                    ?.setOnItemClickListener(object : OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            startActivity(
                                Intent(activity, WebActivity::class.java)
                                    .putExtra(WEB_VIEW_URL, mBannerDataList.get(position).url)
                                    .putExtra(WEB_VIEW_TITLE, mBannerDataList.get(position).title)
                            )

                        }
                    })
                    ?.setPageIndicator(intArrayOf(R.drawable.dot_focus, R.drawable.dot_normal))
                    ?.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
            }
        })

        mViewModel.mArticleData.observe(this, object : Observer<ArrayList<ArticleBean.Data.DataX>> {
            override fun onChanged(t: ArrayList<ArticleBean.Data.DataX>?) {
                mArticleDataList.clear()
                mArticleDataList.addAll(t!!)
                LogUtils.loge("mArticleDataList" + MiJsonUtil.getJson(mArticleDataList))
                mArticleAdapter!!.replaceData(mArticleDataList)
            }
        })

        mViewModel.mProjectData.observe(this, object : Observer<ArrayList<ProjectBean.Data.DataX>> {
            override fun onChanged(t: ArrayList<ProjectBean.Data.DataX>?) {
                mProjectDataList.clear()
                mProjectDataList.addAll(t!!)
                LogUtils.loge("mProjectDataList" + MiJsonUtil.getJson(mProjectDataList))
                mProjectAdapter!!.replaceData(mProjectDataList)
            }
        })
    }

    override fun createLifeCycle(): HomeFragmentLifecycle {
        return getContext()?.let { HomeFragmentLifecycle(it) }!!
    }

    override fun creatViewModel(): HomeFragmentViewModelImpl {
        return ViewModelProviders.of(getInstance()).get(HomeFragmentViewModelImpl::class.java)
    }

}