package com.lixiaomi.openappkotlinmvvm.mv.activity

/**
 * @describe：<br>
 * @author：Xiaomi<br>
 * @createTime：2019/8/26<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
interface SystemListActivityViewModel {
    fun getSystemArticle(showLoading: Boolean, page: Int, cId: String)
}