package com.lixiaomi.openappkotlinmvvm.bean

import com.lixiaomi.baselib.ui.dialog.dialoglist.MiListInterface

/**
 * @describe：<br>
 * @author：Xiaomi<br>
 * @createTime：2019/8/29<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
class NoticeBean(var NoticeTitle: String, var NoticeContent: String) : MiListInterface {
    override fun getMiDialigListShowData(): String {
        return NoticeTitle
    }
}