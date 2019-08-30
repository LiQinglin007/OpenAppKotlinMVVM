package com.lixiaomi.openappkotlinmvvm.mv

import android.arch.lifecycle.MutableLiveData
import com.lixiaomi.baselib.config.AppConfigInIt
import com.lixiaomi.baselib.utils.MiJsonUtil

import com.lixiaomi.baselib.utils.NetWorkUtils
import com.lixiaomi.mvvmbaselib.base.BaseModel
import com.lixiaomi.mvvmbaselib.base.BaseViewModel
import com.lixiaomi.mvvmbaselib.base.MyPresenterCallBack
import com.lixiaomi.openappkotlinmvvm.R
import com.lixiaomi.openappkotlinmvvm.bean.MyTreeBean
import com.lixiaomi.openappkotlinmvvm.bean.TreeBean
import com.lixiaomi.openappkotlinmvvm.model.TreeModelImpl
import com.lixiaomi.openappkotlinmvvm.ui.fragment.SystemFragmentLifecycle
import java.util.ArrayList

/**
 * @describe：<br>
 * @author：Xiaomi<br>
 * @createTime：2019/8/20<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
class SystemFragmentViewMoldeImpl : BaseViewModel<BaseModel, SystemFragmentLifecycle>(), SystemFragmentViewMolde {
    var mTreeData = MutableLiveData<ArrayList<MyTreeBean>>()
    var mTreeList = ArrayList<MyTreeBean>()


    override fun createModelList(): ArrayList<BaseModel> {
        var modelList = ArrayList<BaseModel>()
        modelList.add(TreeModelImpl())
        return modelList
    }

    override fun getTreeTypeList() {
        mShowLoading.value = true
        mTreeList.clear()
        (modelList.get(0) as TreeModelImpl).getTreeTypeList(
            object : MyPresenterCallBack {
                override fun error(message: String?) {
                    mShowLoading.value = false
                    mToastMessage.value = message
                    mTreeData.value = mTreeList
                }

                override fun success(code: Int, response: String?) {
                    mShowLoading.value = false
                    try {
                        val treeBean = MiJsonUtil.getClass(response, TreeBean::class.java)
                        if (treeBean.errorCode == 0) {
                            val data = treeBean.data
                            var myDataList = ArrayList<MyTreeBean>()
                            if (data != null && data.size != 0) {
                                for (index in data.indices) {
                                    val dataBean = data.get(index)
                                    val childrenList = dataBean.children
                                    var myChildrenList = ArrayList<MyTreeBean.ChildrenBean>()
                                    if (childrenList != null && childrenList.size != 0) {
                                        for (childrenIndex in childrenList.indices) {
                                            val childrenBean = childrenList.get(childrenIndex)
                                            var myChildrenBean = MyTreeBean.ChildrenBean()
                                            myChildrenBean.children = childrenBean.children
                                            myChildrenBean.courseId = childrenBean.courseId
                                            myChildrenBean.id = childrenBean.id
                                            myChildrenBean.name = childrenBean.name
                                            myChildrenBean.order = childrenBean.order
                                            myChildrenBean.parentChapterId = childrenBean.parentChapterId
                                            myChildrenBean.userControlSetTop = childrenBean.userControlSetTop
                                            myChildrenBean.visible = childrenBean.visible
                                            myChildrenBean.parentPosition = index
                                            myChildrenBean.position = childrenIndex
                                            myChildrenList.add(myChildrenBean)
                                        }
                                    }
                                    var myTreeBean = MyTreeBean()
                                    myTreeBean.position = index
                                    myTreeBean.children = myChildrenList
                                    myTreeBean.visible = dataBean.visible
                                    myTreeBean.courseId = dataBean.courseId
                                    myTreeBean.id = dataBean.id
                                    myTreeBean.name = dataBean.name
                                    myTreeBean.order = dataBean.order
                                    myTreeBean.parentChapterId = dataBean.parentChapterId
                                    myTreeBean.userControlSetTop = dataBean.userControlSetTop
                                    myTreeBean.subItems = myChildrenList
                                    myDataList.add(myTreeBean)
                                }
                                mTreeList.addAll(myDataList)
                            }
                        }
                    } catch (e: Exception) {
                        mToastMessage.value = AppConfigInIt.getApplicationContext().resources.getString(
                            R.string.http_onFailure
                        )
                    }
                    mTreeData.value = mTreeList
                }

                override fun failure(e: Throwable?) {
                    mTreeData.value = mTreeList
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

}