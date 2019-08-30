package com.lixiaomi.openappkotlinmvvm.http

/**
 * @describe：<br>
 * @author：Xiaomi<br>
 * @createTime：2019/8/21<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
class MyUrl {
    companion object {
        /**
         * 首页Banner
         */
        val BANNER_GET = "banner/json"
        /**
         * 首页文章  可分页
         */
        val ARTICLE_GET = "article/list/"
        /**
         * 首页最新项目
         */
        val ARTICLE_PROJECT_GET = "article/listproject/"
        //    https://wanandroid.com/wxarticle/chapters/json
        /**
         * 获取公众号列表
         */
        val WX_ARTICLE_GET = "wxarticle/chapters/json"
        /**
         * 获取某个公众号下的历史文章
         * wxarticle/list/408/1/json
         */
        val WX_ARTICLE_LIST_GET = "wxarticle/list/"
        /**
         * 体系类型
         */
        val TREE_GET = "tree/json"
        /**
         * 体系下的文章列表
         * 这里的cid是二级分类的id
         * 0/json?cid=60
         */
        val TREE_LIST_GET = "article/list/"
    }
}