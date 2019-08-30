package com.lixiaomi.openappkotlinmvvm.ui.fragment

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.view.View
import com.lixiaomi.baselib.eventmessage.MiEventMessage
import com.lixiaomi.baselib.utils.loadImageUtils.MiLoadImageUtil
import com.lixiaomi.mvvmbaselib.base.BaseFragment
import com.lixiaomi.mvvmbaselib.base.BaseLifeCycle
import com.lixiaomi.mvvmbaselib.base.BaseModel
import com.lixiaomi.mvvmbaselib.base.BaseViewModel
import com.lixiaomi.openappkotlinmvvm.R
import com.lixiaomi.openappkotlinmvvm.databinding.FragmentMeBinding
import com.lixiaomi.openappkotlinmvvm.mv.BaseViewModelImpl
import com.lixiaomi.openappkotlinmvvm.ui.activity.UtilsActivity
import com.lixiaomi.openappkotlinmvvm.ui.activity.WebActivity
import com.lixiaomi.openappkotlinmvvm.utils.BOOK_URL
import com.lixiaomi.openappkotlinmvvm.utils.CSDN_URL
import com.lixiaomi.openappkotlinmvvm.utils.WEB_VIEW_TITLE
import com.lixiaomi.openappkotlinmvvm.utils.WEB_VIEW_URL
import org.greenrobot.eventbus.EventBus

/**
 * @describe：<br>
 * @author：Xiaomi<br>
 * @createTime：2019/8/20<br>
 * @remarks：<br>
 * @changeTime:<br>
 */
class MeFragment : BaseFragment<MeFragmentLifecycle, FragmentMeBinding, BaseViewModelImpl>(),
    View.OnClickListener {


    companion object {
        fun getInstance(): MeFragment {
            return MeFragmentHolder.meFragment
        }
    }

    object MeFragmentHolder {
        val meFragment = MeFragment()
    }

    lateinit var mMineTakePic: AppCompatImageView
    lateinit var mMineUtils: AppCompatTextView
    lateinit var mSwitchFragmentTv: AppCompatTextView
    lateinit var mBlogTv: AppCompatTextView
    lateinit var mBookTv: AppCompatTextView

    override fun initData() {

    }

    override fun setLayout(): Int {
        return R.layout.fragment_me
    }

    override fun initView(rootView: View?, savedInstanceState: Bundle?) {
        mMineTakePic = rootView!!.findViewById(R.id.mine_take_pic)
        mMineUtils = rootView!!.findViewById(R.id.mine_utils)
        mSwitchFragmentTv = rootView!!.findViewById(R.id.mine_switch_fragment)
        mBlogTv = rootView!!.findViewById(R.id.mine_blog)
        mBookTv = rootView!!.findViewById(R.id.mine_book)

        mMineUtils.setOnClickListener(this)
        mSwitchFragmentTv.setOnClickListener(this)
        mBlogTv.setOnClickListener(this)
        mBookTv.setOnClickListener(this)

        MiLoadImageUtil.loadImageCircle(context, R.drawable.headview, mMineTakePic)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mine_utils -> {
                startActivity(Intent(activity, UtilsActivity::class.java))
            }
            R.id.mine_switch_fragment -> {
                EventBus.getDefault().post(MiEventMessage(MiEventMessage.SWITCH_FRAGMENT, 0))
            }
            R.id.mine_blog -> {
                startActivity(
                    Intent(activity, WebActivity::class.java)
                        .putExtra(WEB_VIEW_URL, CSDN_URL)
                        .putExtra(WEB_VIEW_TITLE, "个人博客")
                )
            }
            R.id.mine_book -> {
                startActivity(
                    Intent(activity, WebActivity::class.java)
                        .putExtra(WEB_VIEW_URL, BOOK_URL)
                        .putExtra(WEB_VIEW_TITLE, "个人博客")
                )
            }
        }
    }

    override fun startListenerData() {

    }

    override fun createLifeCycle(): MeFragmentLifecycle {
        return MeFragmentLifecycle(getContext())
    }

    override fun creatViewModel(): BaseViewModelImpl {
        return ViewModelProviders.of(getInstance()).get(BaseViewModelImpl::class.java)
    }

}