package com.lixiaomi.openappkotlinmvvm.mv

/**
 * @describe：<br>
 * @author：Xiaomi<br>
 * @createTime：2019/8/20<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
interface SubscribeFragmentViewModel {
    /**
     * 获取公众号列表
     */
    abstract fun getWXAuthorList()

    /**
     * 获取公众号下的文章列表
     *
     * @param authorId 公众号id
     * @param page     页码
     */
    abstract fun getWXArticleList(showLoading: Boolean, authorId: String, page: Int)
}