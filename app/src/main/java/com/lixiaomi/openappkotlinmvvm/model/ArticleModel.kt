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
interface ArticleModel : BaseModel {
    /**
     * 获取文章列表，分页
     */
    fun getArtcleList(page: Int, myPresenterCallBack: MyPresenterCallBack)

    /**
     * 获取首页最新项目 分页
     */
    fun getArtcleProjectList(page: Int, myPresenterCallBack: MyPresenterCallBack)
}