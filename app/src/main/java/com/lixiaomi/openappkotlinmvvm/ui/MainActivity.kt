package com.lixiaomi.openappkotlinmvvm.ui


import android.support.v4.app.Fragment
import com.lixiaomi.baselib.eventmessage.MiEventMessage
import com.lixiaomi.baselib.utils.MiFinalData
import com.lixiaomi.baselib.utils.PreferenceUtils
import com.lixiaomi.mvvmbaselib.bottom.BaseBottomActivity
import com.lixiaomi.mvvmbaselib.bottom.BottomTabBean
import com.lixiaomi.openappkotlinmvvm.R
import com.lixiaomi.openappkotlinmvvm.ui.fragment.HomeFragment
import com.lixiaomi.openappkotlinmvvm.ui.fragment.MeFragment
import com.lixiaomi.openappkotlinmvvm.ui.fragment.SubscribeFragment
import com.lixiaomi.openappkotlinmvvm.ui.fragment.SystemFragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.LinkedHashMap

class MainActivity : BaseBottomActivity() {
    override fun setStatusBarColor(): Int {
        return R.color.default_color
    }

    override fun setOnBottomItemClickListener(): OnBottomItemClickListener {
        return OnBottomItemClickListener { true }
    }

    override fun setChooseIndex(): Int {
        return 0
    }

    override fun init() {
        EventBus.getDefault().register(this)
        PreferenceUtils.setBoolean(MiFinalData.IS_OPEN_APP, true)
    }

    override fun setItems(): LinkedHashMap<BottomTabBean, Fragment> {
        var mFragmentList: LinkedHashMap<BottomTabBean, Fragment> = LinkedHashMap<BottomTabBean, Fragment>()
        mFragmentList.put(
            BottomTabBean("首页", R.drawable.icon_bottom_home_sel, R.drawable.icon_bottom_home),
            HomeFragment.getInstance()
        )
        mFragmentList.put(
            BottomTabBean("公众号", R.drawable.icon_bottom_sub_sel, R.drawable.icon_bottom_sub),
            SubscribeFragment.getInstance()
        )
        mFragmentList.put(
            BottomTabBean("体系", R.drawable.icon_bottom_sys_sel, R.drawable.icon_bottom_sys),
            SystemFragment.getInstance()
        )
        mFragmentList.put(
            BottomTabBean("我的", R.drawable.icon_bottom_me_sel, R.drawable.icon_bottom_me),
            MeFragment.getInstance()
        )
        return mFragmentList
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun appDownload(message: MiEventMessage) {
        if(message.messageType==MiEventMessage.SWITCH_FRAGMENT){
            setSwitchIndex(message.messageInt)
        }

    }


    override fun setUnClickedColor(): Int {
        return R.color.color_666
    }

    override fun initCompletion() {
    }

    override fun setBackGroundColor(): Int {
        return R.color.color_white
    }

    override fun setClickedColor(): Int {
        return R.color.default_color
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}
