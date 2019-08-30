package com.lixiaomi.openappkotlinmvvm.model

import com.lixiaomi.mvvmbaselib.base.BaseModel
import com.lixiaomi.mvvmbaselib.base.MyPresenterCallBack

/**
 * @describe：<br>
 * @author：Xiaomi<br>
 * @createTime：2019/8/21<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
interface TreeModel : BaseModel {
    /**
     * 获取体系类型列表
     */
    fun getTreeTypeList(myPresenterCallBack: MyPresenterCallBack)

    /**
     * 获取体系下的文章列表
     */
    fun getTreeList(page: Int, cId: String, myPresenterCallBack: MyPresenterCallBack)
}