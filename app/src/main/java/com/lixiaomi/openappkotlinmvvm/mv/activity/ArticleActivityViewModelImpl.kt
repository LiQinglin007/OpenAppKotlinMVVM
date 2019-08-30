package com.lixiaomi.openappkotlinmvvm.mv.activity

import android.arch.lifecycle.MutableLiveData
import com.lixiaomi.baselib.config.AppConfigInIt
import com.lixiaomi.baselib.utils.MiJsonUtil
import com.lixiaomi.baselib.utils.NetWorkUtils
import com.lixiaomi.mvvmbaselib.base.BaseModel
import com.lixiaomi.mvvmbaselib.base.BaseViewModel
import com.lixiaomi.mvvmbaselib.base.MyPresenterCallBack
import com.lixiaomi.openappkotlinmvvm.R
import com.lixiaomi.openappkotlinmvvm.bean.ArticleBean
import com.lixiaomi.openappkotlinmvvm.model.ArticleModelImpl
import com.lixiaomi.openappkotlinmvvm.ui.activity.ArticleActivityLifeCycle
import java.util.ArrayList

/**
 * @describe：<br>
 * @author：Xiaomi<br>
 * @createTime：2019/8/26<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
class ArticleActivityViewModelImpl : BaseViewModel<BaseModel, ArticleActivityLifeCycle>(),
    ArticleActivityViewModel {

    var mArticleListData = MutableLiveData<ArrayList<ArticleBean.Data.DataX>>()
    var mArticleBean = ArrayList<ArticleBean.Data.DataX>()

    /**
     * 是否可以继续加载下一页
     */
    var mEableLoadMore = MutableLiveData<Boolean>()


    override fun createModelList(): ArrayList<BaseModel> {
        var models = ArrayList<BaseModel>()
        models.add(ArticleModelImpl())
        return models
    }
    override fun getArticleData(showLoading: Boolean, page: Int) {
        if(page==0){
            mArticleBean.clear()
        }
        (modelList.get(0) as ArticleModelImpl).getArtcleList(page, object : MyPresenterCallBack {
            override fun error(message: String?) {
                if (showLoading) {
                    mShowLoading.value = false
                }
                mToastMessage.value = message
                mArticleListData.value = mArticleBean
            }

            override fun success(code: Int, response: String?) {
                mShowLoading.value = false
                try {
                    val articleBean = MiJsonUtil.getClass(response, ArticleBean::class.java)
                    //code ==0并且文章数量!=0
                    if (articleBean.errorCode == 0 && articleBean.data.total != 0) {
                        val data = articleBean.data.datas
                        if (data != null && data.size != 0) {
                            mArticleBean.addAll(data)
                            mEableLoadMore.value =
                                if (articleBean.data.curPage >= articleBean.data.pageCount) false else true
                        }
                    }
                } catch (e: Exception) {
                    mToastMessage.value = AppConfigInIt.getApplicationContext().resources.getString(
                        R.string.http_onFailure
                    )
                }
                mArticleListData.value = mArticleBean
            }

            override fun failure(e: Throwable?) {
                mArticleListData.value = mArticleBean
                if (showLoading) {
                    mShowLoading.value = false
                }
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
}