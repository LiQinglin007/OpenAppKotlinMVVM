package com.lixiaomi.openappkotlinmvvm.bean

import com.lixiaomi.baselib.ui.dialog.dialoglist.MiListInterface

/**
 * @describe：<br>
 * @author：Xiaomi<br>
 * @createTime：2019/8/21<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
class UserBean(userName: String?, userSex: String?) : MiListInterface {

    var userName: String? = null
    var userSex: String? = null

    init {
        this.userName = userName
        this.userSex = userSex
    }

    override fun getMiDialigListShowData(): String {
        return userName + "我是拼接内容"
    }
}