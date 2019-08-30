package com.lixiaomi.openappkotlinmvvm.mv.activity

import android.arch.lifecycle.MutableLiveData
import com.lixiaomi.baselib.config.AppConfigInIt
import com.lixiaomi.baselib.utils.MiJsonUtil
import com.lixiaomi.baselib.utils.NetWorkUtils
import com.lixiaomi.mvvmbaselib.base.BaseModel
import com.lixiaomi.mvvmbaselib.base.BaseViewModel
import com.lixiaomi.mvvmbaselib.base.MyPresenterCallBack
import com.lixiaomi.openappkotlinmvvm.R
import com.lixiaomi.openappkotlinmvvm.bean.ProjectBean
import com.lixiaomi.openappkotlinmvvm.model.ArticleModelImpl
import com.lixiaomi.openappkotlinmvvm.ui.activity.ProjectActivityLifeCycle
import java.util.ArrayList

/**
 * @describe：<br>
 * @author：Xiaomi<br>
 * @createTime：2019/8/26<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
class ProjectActivityViewModelImpl : BaseViewModel<BaseModel, ProjectActivityLifeCycle>(), ProjectActivityViewModel {

    var mProjectListData = MutableLiveData<ArrayList<ProjectBean.Data.DataX>>()
    var mProjectBean = ArrayList<ProjectBean.Data.DataX>()

    /**
     * 是否可以继续加载下一页
     */
    var mEableLoadMore = MutableLiveData<Boolean>()

    override fun createModelList(): ArrayList<BaseModel> {
        var models = ArrayList<BaseModel>()
        models.add(ArticleModelImpl())
        return models
    }

    override fun getProjectData(showLoading: Boolean, page: Int) {
        if(page==0){
            mProjectBean.clear()
        }
        (modelList.get(0) as ArticleModelImpl).getArtcleProjectList(page, object : MyPresenterCallBack {
            override fun error(message: String?) {
                if (showLoading) {
                    mShowLoading.value = false
                }
                mToastMessage.value = message
                mProjectListData.value = mProjectBean
            }

            override fun success(code: Int, response: String?) {
                mShowLoading.value = false
                try {
                    val projectBean = MiJsonUtil.getClass(response, ProjectBean::class.java)
                    //code ==0并且文章数量!=0
                    if (projectBean.errorCode == 0 && projectBean.data.total != 0) {
                        val data = projectBean.data.datas
                        if (data != null && data.size != 0) {
                            mProjectBean.addAll(data)
                            mEableLoadMore.value =
                                if (projectBean.data.curPage >= projectBean.data.pageCount) false else true
                        }
                    }
                } catch (e: Exception) {
                    mToastMessage.value = AppConfigInIt.getApplicationContext().resources.getString(
                        R.string.http_onFailure
                    )
                }
                mProjectListData.value = mProjectBean
            }

            override fun failure(e: Throwable?) {
                mProjectListData.value = mProjectBean
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