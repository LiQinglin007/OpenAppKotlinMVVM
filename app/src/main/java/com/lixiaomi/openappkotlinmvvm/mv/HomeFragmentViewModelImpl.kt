package com.lixiaomi.openappkotlinmvvm.mv

import android.arch.lifecycle.MutableLiveData
import com.lixiaomi.baselib.config.AppConfigInIt
import com.lixiaomi.baselib.utils.MiJsonUtil
import com.lixiaomi.baselib.utils.NetWorkUtils
import com.lixiaomi.mvvmbaselib.base.BaseModel
import com.lixiaomi.mvvmbaselib.base.BaseViewModel
import com.lixiaomi.mvvmbaselib.base.MyPresenterCallBack
import com.lixiaomi.openappkotlinmvvm.R
import com.lixiaomi.openappkotlinmvvm.bean.ArticleBean
import com.lixiaomi.openappkotlinmvvm.bean.BannerBean
import com.lixiaomi.openappkotlinmvvm.bean.ProjectBean
import com.lixiaomi.openappkotlinmvvm.model.ArticleModelImpl
import com.lixiaomi.openappkotlinmvvm.model.PublicModelImpl
import com.lixiaomi.openappkotlinmvvm.ui.fragment.HomeFragmentLifecycle
import java.util.ArrayList

/**
 * @describe：<br>
 * @author：Xiaomi<br>
 * @createTime：2019/8/20<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
class HomeFragmentViewModelImpl : BaseViewModel<BaseModel, HomeFragmentLifecycle>(), HomeFragmentViewModel {

    var mBannerData = MutableLiveData<ArrayList<BannerBean.Data>>()
    var mProjectData = MutableLiveData<ArrayList<ProjectBean.Data.DataX>>()
    var mArticleData = MutableLiveData<ArrayList<ArticleBean.Data.DataX>>()

    var mBeannerList = ArrayList<BannerBean.Data>()
    var mProjectList = ArrayList<ProjectBean.Data.DataX>()
    var mArticleList = ArrayList<ArticleBean.Data.DataX>()

    override fun createModelList(): ArrayList<BaseModel> {
        var modelList = ArrayList<BaseModel>()
        modelList.add(PublicModelImpl())
        modelList.add(ArticleModelImpl())
        return modelList
    }

    override fun getBannerData() {
        mShowLoading.value = true
        mBeannerList.clear()
        (modelList.get(0) as PublicModelImpl).getBannerList(object : MyPresenterCallBack {
            override fun error(message: String?) {
                mShowLoading.value = false
                mToastMessage.value = message
                mBannerData.value = mBeannerList
            }

            override fun success(code: Int, response: String?) {
                mShowLoading.value = false
                try {
                    val bannerBean = MiJsonUtil.getClass(response, BannerBean::class.java)
                    if (bannerBean.errorCode == 0) {
                        val datas = bannerBean.data
                        if (datas.size != 0) {
                            mBeannerList.addAll(datas)

                        }
                    }
                } catch (e: Exception) {
                    mToastMessage.value = AppConfigInIt.getApplicationContext().resources.getString(
                        R.string.http_AnalysisError
                    )
                }
                mBannerData.value = mBeannerList
            }

            override fun failure(e: Throwable?) {
                mShowLoading.value = false
                mToastMessage.value = AppConfigInIt.getApplicationContext().resources.getString(
                    if (NetWorkUtils.isNetworkConnected(AppConfigInIt.getApplicationContext())) {
                        R.string.http_onFailure
                    } else {
                        R.string.http_NoNetWorkError
                    }
                )
                mBannerData.value = mBeannerList
            }
        })
    }

    override fun getArticleData() {
        mShowLoading.value = true
        mArticleList.clear()
        (modelList.get(1) as ArticleModelImpl).getArtcleList(0, object : MyPresenterCallBack {
            override fun error(message: String?) {
                mShowLoading.value = false
                mToastMessage.value = message
                mArticleData.value = mArticleList
            }

            override fun success(code: Int, response: String?) {
                mShowLoading.value = false
                try {
                    val articleBean: ArticleBean = MiJsonUtil.getClass(response, ArticleBean::class.java)
                    if (articleBean.errorCode == 0) {
                        val datas = articleBean.data.datas
                        if (datas?.size != 0) {
                            if (datas.size > 5) {
                                mArticleList.add(datas.get(0))
                                mArticleList.add(datas.get(1))
                                mArticleList.add(datas.get(2))
                                mArticleList.add(datas.get(3))
                                mArticleList.add(datas.get(4))
                            } else {
                                mArticleList.addAll(datas)
                            }
                        }
                    }
                } catch (e: Exception) {
                    mToastMessage.value = AppConfigInIt.getApplicationContext().resources.getString(
                        R.string.http_AnalysisError
                    )
                }
                mArticleData.value = mArticleList
            }

            override fun failure(e: Throwable?) {
                mShowLoading.value = false
                mToastMessage.value = AppConfigInIt.getApplicationContext().resources.getString(
                    if (NetWorkUtils.isNetworkConnected(AppConfigInIt.getApplicationContext())) {
                        R.string.http_onFailure
                    } else {
                        R.string.http_NoNetWorkError
                    }
                )
                mArticleData.value = mArticleList
            }

        })
    }

    override fun getProjetData() {
        mShowLoading.value = true
        mProjectList.clear()
        (modelList.get(1) as ArticleModelImpl).getArtcleProjectList(0, object : MyPresenterCallBack {
            override fun error(message: String?) {
                mShowLoading.value = false
                mToastMessage.value = message
                mProjectData.value = mProjectList
            }

            override fun success(code: Int, response: String?) {
                mShowLoading.value = false
                try {
                    val projectBean = MiJsonUtil.getClass(response, ProjectBean::class.java)
                    if (projectBean.errorCode == 0) {
                        val datas = projectBean.data.datas
                        if (datas.size != 0) {
                            if (datas.size > 5) {
                                mProjectList.add(datas.get(0))
                                mProjectList.add(datas.get(1))
                                mProjectList.add(datas.get(2))
                                mProjectList.add(datas.get(3))
                                mProjectList.add(datas.get(4))
                            } else {
                                mProjectList.addAll(datas)
                            }
                        }
                    }
                } catch (e: Exception) {
                    mToastMessage.value = AppConfigInIt.getApplicationContext().resources.getString(
                        R.string.http_AnalysisError
                    )
                }
                mProjectData.value = mProjectList
            }

            override fun failure(e: Throwable?) {
                mShowLoading.value = false
                mToastMessage.value = AppConfigInIt.getApplicationContext().resources.getString(
                    if (NetWorkUtils.isNetworkConnected(AppConfigInIt.getApplicationContext())) {
                        R.string.http_onFailure
                    } else {
                        R.string.http_NoNetWorkError
                    }
                )
                mProjectData.value = mProjectList
            }
        })
    }

}