package com.lixiaomi.openappkotlinmvvm.mv.activity

import android.arch.lifecycle.MutableLiveData
import com.lixiaomi.baselib.config.AppConfigInIt
import com.lixiaomi.baselib.utils.MiJsonUtil
import com.lixiaomi.baselib.utils.NetWorkUtils
import com.lixiaomi.mvvmbaselib.base.BaseModel
import com.lixiaomi.mvvmbaselib.base.BaseViewModel
import com.lixiaomi.mvvmbaselib.base.MyPresenterCallBack
import com.lixiaomi.openappkotlinmvvm.R
import com.lixiaomi.openappkotlinmvvm.bean.TreeArticleListBean
import com.lixiaomi.openappkotlinmvvm.bean.WXArticleListBean
import com.lixiaomi.openappkotlinmvvm.model.TreeModelImpl
import com.lixiaomi.openappkotlinmvvm.ui.activity.SystemListActivityLifecycle
import java.util.ArrayList

/**
 * @describe：<br>
 * @author：Xiaomi<br>
 * @createTime：2019/8/26<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
class SystemListActivityViewModelImpl : BaseViewModel<BaseModel, SystemListActivityLifecycle>(),
    SystemListActivityViewModel {
    var mTreeArticleListData = MutableLiveData<ArrayList<TreeArticleListBean.Data.DataX>>()
    var mTreeArticleBean = ArrayList<TreeArticleListBean.Data.DataX>()

    /**
     * 是否可以继续加载下一页
     */
    var mEableLoadMore = MutableLiveData<Boolean>()

    override fun getSystemArticle(showLoading: Boolean, page: Int, cId: String) {
        mShowLoading.value = showLoading
        if (page == 0) {
            mTreeArticleBean.clear()
        }
        (modelList.get(0) as TreeModelImpl).getTreeList(page, cId, object : MyPresenterCallBack {
            override fun error(message: String?) {
                if (showLoading) {
                    mShowLoading.value = false
                }
                mToastMessage.value = message
                mTreeArticleListData.value = mTreeArticleBean
            }

            override fun success(code: Int, response: String?) {
                mShowLoading.value = false
                try {
                    val treeArticleListBean = MiJsonUtil.getClass(response, TreeArticleListBean::class.java)
                    //code ==0并且文章数量!=0
                    if (treeArticleListBean.errorCode == 0 && treeArticleListBean.data.total != 0) {
                        val data = treeArticleListBean.data.datas
                        if (data != null && data.size != 0) {
                            mTreeArticleBean.addAll(data)
                            mEableLoadMore.value =
                                if (treeArticleListBean.data.curPage >= treeArticleListBean.data.pageCount) false else true
                        }
                    }
                } catch (e: Exception) {
                    mToastMessage.value = AppConfigInIt.getApplicationContext().resources.getString(
                        R.string.http_onFailure
                    )
                }
                mTreeArticleListData.value = mTreeArticleBean
            }

            override fun failure(e: Throwable?) {
                mTreeArticleListData.value = mTreeArticleBean
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


    override fun createModelList(): ArrayList<BaseModel> {
        var models = ArrayList<BaseModel>()
        models.add(TreeModelImpl())
        return models
    }
}