package com.lixiaomi.openappkotlinmvvm.mv

import com.lixiaomi.mvvmbaselib.base.BaseLifeCycle
import com.lixiaomi.mvvmbaselib.base.BaseModel
import com.lixiaomi.mvvmbaselib.base.BaseViewModel
import java.util.ArrayList

/**
 * @describe：<br>
 * @author：Xiaomi<br>
 * @createTime：2019/8/21<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
class BaseViewModelImpl :BaseViewModel<BaseModel,BaseLifeCycle>(){
    override fun createModelList(): ArrayList<BaseModel> {
        val modelList=ArrayList<BaseModel>()
        return modelList
    }

}