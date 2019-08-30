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
interface PublicModel : BaseModel {
    fun getBannerList(myPresenterCallBack: MyPresenterCallBack)
}