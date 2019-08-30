package com.lixiaomi.openappkotlinmvvm.mv

import android.arch.lifecycle.MutableLiveData
import com.lixiaomi.baselib.config.AppConfigInIt
import com.lixiaomi.baselib.utils.MiJsonUtil
import com.lixiaomi.baselib.utils.NetWorkUtils
import com.lixiaomi.mvvmbaselib.base.BaseModel
import com.lixiaomi.mvvmbaselib.base.BaseViewModel
import com.lixiaomi.mvvmbaselib.base.MyPresenterCallBack
import com.lixiaomi.openappkotlinmvvm.R
import com.lixiaomi.openappkotlinmvvm.bean.WXArticleAuthorlistBean
import com.lixiaomi.openappkotlinmvvm.bean.WXArticleListBean
import com.lixiaomi.openappkotlinmvvm.model.WXArticleModelImpl
import com.lixiaomi.openappkotlinmvvm.ui.fragment.SubscribeFragmentLifecycle
import java.util.ArrayList

/**
 * @describe：<br>
 * @author：Xiaomi<br>
 * @createTime：2019/8/20<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
class SubscribeFragmentViewModelImpl : BaseViewModel<BaseModel, SubscribeFragmentLifecycle>(),
    SubscribeFragmentViewModel {

    /**
     * 公众号作者列表
     */
    var mAuthorData = MutableLiveData<ArrayList<WXArticleAuthorlistBean.Data>>()
    var mAuthorList = ArrayList<WXArticleAuthorlistBean.Data>()

    /**
     * 公众号下的文章列表
     */
    var mArticleData = MutableLiveData<ArrayList<WXArticleListBean.Data.DataX>>()
    var mArticleList = ArrayList<WXArticleListBean.Data.DataX>()

    /**
     * 是否可以继续加载下一页
     */
    var mEableLoadMore = MutableLiveData<Boolean>()

    init {
        mEableLoadMore.value = false
    }

    override fun getWXAuthorList() {
        mShowLoading.value = true
        mAuthorList.clear()
        (modelList.get(0) as WXArticleModelImpl).getWXArticleAuthorList(object : MyPresenterCallBack {
            override fun error(message: String?) {
                mShowLoading.value = false
                mToastMessage.value = message
                mAuthorData.value = mAuthorList
            }

            override fun success(code: Int, response: String?) {
                mShowLoading.value = false
                try {
                    val authorlistBean = MiJsonUtil.getClass(response, WXArticleAuthorlistBean::class.java)
                    if (authorlistBean.errorCode == 0) {
                        val data = authorlistBean.data
                        if (data != null && data.size != 0) {
                            mAuthorList.addAll(data)
                        }
                    }
                } catch (e: Exception) {
                    mToastMessage.value = AppConfigInIt.getApplicationContext().resources.getString(
                        R.string.http_onFailure
                    )
                }
                mAuthorData.value = mAuthorList
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
                mAuthorData.value = mAuthorList
            }

        })
    }

    override fun getWXArticleList(showLoading: Boolean, authorId: String, page: Int) {
        mShowLoading.value = showLoading
        if (page == 0) {
            mArticleList.clear()
        }
        (modelList.get(0) as WXArticleModelImpl).getWXArticleListByAuthorId(
            authorId,
            page,
            object : MyPresenterCallBack {
                override fun error(message: String?) {
                    mShowLoading.value = false
                    mToastMessage.value = message
                    mArticleData.value = mArticleList
                }

                override fun success(code: Int, response: String?) {
                    mShowLoading.value = false
                    try {
                        val articeBean = MiJsonUtil.getClass(response, WXArticleListBean::class.java)
                        //code ==0并且文章数量!=0
                        if (articeBean.errorCode == 0 && articeBean.data.total != 0) {
                            val data = articeBean.data.datas
                            if (data != null && data.size != 0) {
                                mArticleList.addAll(data)
                                mEableLoadMore.value =
                                    if (articeBean.data.curPage >= articeBean.data.pageCount) false else true
                            }
                        }
                    } catch (e: Exception) {
                        mToastMessage.value = AppConfigInIt.getApplicationContext().resources.getString(
                            R.string.http_onFailure
                        )
                    }
                    mArticleData.value = mArticleList
                }

                override fun failure(e: Throwable?) {
                    mArticleData.value = mArticleList
                    mShowLoading.value = false
                    mToastMessage.value = AppConfigInIt.getApplicationContext().resources.getString(
                        if (NetWorkUtils.isNetworkConnected(AppConfigInIt.getApplicationContext())) {
                            R.string.http_onFailure
                        } else {
                            R.string.http_NoNetWorkError
                        }
                    )
                }
            })
    }

    override fun createModelList(): ArrayList<BaseModel> {
        var modelList = ArrayList<BaseModel>()
        modelList.add(WXArticleModelImpl())
        return modelList
    }

}