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
interface WXArticleModel : BaseModel {
    /**
     * 获取公众号作者列表
     */
    fun getWXArticleAuthorList(myPresenterCallBack: MyPresenterCallBack)

    /**
     * 获取某个公众号下的文章列表
     */
    fun getWXArticleListByAuthorId(authorId: String, page: Int, myPresenterCallBack: MyPresenterCallBack)
}