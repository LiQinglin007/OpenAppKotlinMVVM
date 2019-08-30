package com.lixiaomi.openappkotlinmvvm.bean

import com.google.gson.annotations.SerializedName


/**
 * @describe：<br>
 * @author：Xiaomi<br>
 * @createTime：2019/8/22<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
data class ProjectBean(
    @SerializedName("data")
    var `data`: Data,
    @SerializedName("errorCode")
    var errorCode: Int,
    @SerializedName("errorMsg")
    var errorMsg: String
) {
    data class Data(
        @SerializedName("curPage")
        var curPage: Int,
        @SerializedName("datas")
        var datas: List<DataX>,
        @SerializedName("offset")
        var offset: Int,
        @SerializedName("over")
        var over: Boolean,
        @SerializedName("pageCount")
        var pageCount: Int,
        @SerializedName("size")
        var size: Int,
        @SerializedName("total")
        var total: Int
    ) {
        data class DataX(
            @SerializedName("apkLink")
            var apkLink: String,
            @SerializedName("author")
            var author: String,
            @SerializedName("chapterId")
            var chapterId: Int,
            @SerializedName("chapterName")
            var chapterName: String,
            @SerializedName("collect")
            var collect: Boolean,
            @SerializedName("courseId")
            var courseId: Int,
            @SerializedName("desc")
            var desc: String,
            @SerializedName("envelopePic")
            var envelopePic: String,
            @SerializedName("fresh")
            var fresh: Boolean,
            @SerializedName("id")
            var id: Int,
            @SerializedName("link")
            var link: String,
            @SerializedName("niceDate")
            var niceDate: String,
            @SerializedName("origin")
            var origin: String,
            @SerializedName("prefix")
            var prefix: String,
            @SerializedName("projectLink")
            var projectLink: String,
            @SerializedName("publishTime")
            var publishTime: Long,
            @SerializedName("superChapterId")
            var superChapterId: Int,
            @SerializedName("superChapterName")
            var superChapterName: String,
            @SerializedName("tags")
            var tags: List<Tag>,
            @SerializedName("title")
            var title: String,
            @SerializedName("type")
            var type: Int,
            @SerializedName("userId")
            var userId: Int,
            @SerializedName("visible")
            var visible: Int,
            @SerializedName("zan")
            var zan: Int
        ) {
            data class Tag(
                @SerializedName("name")
                var name: String,
                @SerializedName("url")
                var url: String
            )
        }
    }
}

