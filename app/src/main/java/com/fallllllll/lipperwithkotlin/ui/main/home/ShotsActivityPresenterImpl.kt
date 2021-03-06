package com.fallllllll.lipperwithkotlin.ui.main.home

import com.fallllllll.lipperwithkotlin.R
import com.fallllllll.lipperwithkotlin.core.constants.KEY_FILTER_SORT
import com.fallllllll.lipperwithkotlin.core.constants.KEY_FILTER_TIME
import com.fallllllll.lipperwithkotlin.core.constants.KEY_FILTER_TYPE
import com.fallllllll.lipperwithkotlin.core.expandFunction.commonChange
import com.fallllllll.lipperwithkotlin.core.expandFunction.isTokenOutOfDate
import com.fallllllll.lipperwithkotlin.core.presenter.BasePresenter
import com.fallllllll.lipperwithkotlin.core.rxjava.RxBus
import com.fallllllll.lipperwithkotlin.data.databean.eventBean.LoginEvent
import com.fallllllll.lipperwithkotlin.data.databean.eventBean.ShotsListFilterEvent
import com.fallllllll.lipperwithkotlin.data.local.datatank.DelegatesExt
import com.fallllllll.lipperwithkotlin.data.local.user.UserManager
import com.fallllllll.lipperwithkotlin.data.network.model.DribbbleModel

/**
 * Created by fall on 2017/6/18.
 * GitHub :  https://github.com/348476129/LipperWithKotlin
 */
class ShotsActivityPresenterImpl(private val dribbbleModel: DribbbleModel, private val view: ShotsActivityContract.ShotsActivityView)
    : BasePresenter(), ShotsActivityContract.ShotsActivityPresenter {



    private var time: String by DelegatesExt.valuePreference(KEY_FILTER_TIME, "")
    private var sort: String by DelegatesExt.valuePreference(KEY_FILTER_SORT, "")
    private var type: String by DelegatesExt.valuePreference(KEY_FILTER_TYPE, "")


    override fun sortTextClick() {
        view.showChoiceSortDialog(sort)
    }

    override fun timeTextClick() {
        view.showChoiceTimeDialog(time)
    }

    override fun typeTextClick() {
        view.showChoiceTypeDialog(type)
    }
    override fun changeSort(name: String) {
        sort = name
        RxBus.get().post(ShotsListFilterEvent(time, sort, type))
    }

    override fun changeTime(name: String) {
        time = name
        RxBus.get().post(ShotsListFilterEvent(time, sort, type))

    }

    override fun changeType(name: String) {
        type = name
        RxBus.get().post(ShotsListFilterEvent(time, sort, type))
    }


    override fun userImageClick() {
        if (UserManager.instance.isLogin()) {
            view.goUserCenterActivity()
        } else {
            view.showUserImageLoginAnimation()
        }
    }

    override fun menuActivityClick() {
        if (UserManager.instance.isLogin()) {
            view.goUserActivity()
        } else {
            view.showMenuLoginAnimation()
        }
    }


    override fun onPresenterCreate() {
        view.showTabText(sort, type, time)
        subscribeLoginEvent()
        if (UserManager.instance.isLogin()) {
            view.showUserUI(UserManager.instance.lipperUser)
            updateUserData()
        } else {
            view.showLogoutUI()
        }
    }

    private fun updateUserData() {
        val userManager = UserManager.instance
        dribbbleModel.getUserInfo(userManager.access_token)
                .commonChange()
                .subscribe({
                    userManager.updateUser(it)
                    view.showUserUI(userManager.lipperUser)
                }, {
                    onError(it)
                })
    }

    private fun onError(throwable: Throwable) {
        if (throwable.isTokenOutOfDate()) {
            view.showErrorDialog(view.getString(R.string.login_expire))
            UserManager.instance.logOut()
        }
    }

    private fun subscribeLoginEvent() {
        compositeDisposable.add(RxBus.get().toFlowable<LoginEvent>()
                .subscribe({
                    if (it.isLogin) {
                        view.showUserUI(UserManager.instance.lipperUser)
                    } else {
                        view.showLogoutUI()
                    }
                }, { subscribeLoginEvent() }))
    }
}