package com.fallllllll.lipperwithkotlin.ui.main.home

import android.app.ActivityOptions
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.fallllllll.lipperwithkotlin.R
import com.fallllllll.lipperwithkotlin.core.activity.BaseActivity
import com.fallllllll.lipperwithkotlin.core.constants.USER_IMAGE_SIZE
import com.fallllllll.lipperwithkotlin.core.expandFunction.getStatusBarHeight
import com.fallllllll.lipperwithkotlin.core.expandFunction.setImageTranslucent
import com.fallllllll.lipperwithkotlin.core.expandFunction.showImage
import com.fallllllll.lipperwithkotlin.data.databean.HomeListFilterBean
import com.fallllllll.lipperwithkotlin.data.local.user.LipperUser
import com.fallllllll.lipperwithkotlin.ui.login.DribbbleLoginActivity
import com.fallllllll.lipperwithkotlin.ui.search.SearchActivity
import com.fallllllll.lipperwithkotlin.ui.shoslist.HOME_TYPE
import com.fallllllll.lipperwithkotlin.ui.shoslist.ShotsListFragment
import com.fallllllll.lipperwithkotlin.ui.transitions.FabTransform
import kotlinx.android.synthetic.main.activity_shots.*

/**
 * Created by 康颢曦 on 2017/6/18.
 */
class ShotsActivity : BaseActivity(), ShotsActivityContract.ShotsActivityView {


    val popWindow by lazy {
        HomeItemLayoutPopWindow(this)
    }
    private val shotsListFragment by lazy {
        ShotsListFragment.newInstance(HOME_TYPE, "")
    }
    var homeBottomSheetFragment: HomeBottomSheetFragment? = null
    private var presenter: ShotsActivityContract.ShotsActivityPresenter? = null

    override fun LogOut() {
        userIcon.showImage("", false)
        shotsToolbar.title = getString(R.string.app_name)
    }

    override fun showUserUI(lipperUser: LipperUser) {
        userIcon.showImage(USER_IMAGE_SIZE, USER_IMAGE_SIZE, lipperUser.avatarUrl ?: "")
        if ((lipperUser.username ?: "").length > 8) {
            shotsToolbar.title = lipperUser.username?.substring(0, 7) + "..."
        } else {
            shotsToolbar.title = lipperUser.username
        }
    }


    fun setPresenter(presenter: ShotsActivityContract.ShotsActivityPresenter) {
        this.presenter = presenter
    }

    override fun showBottomSheet(homeListFilterBean: HomeListFilterBean) {
        if (homeBottomSheetFragment == null) {
            homeBottomSheetFragment = HomeBottomSheetFragment.newInstance(homeListFilterBean.time, homeListFilterBean.type, homeListFilterBean.sort)
        }
        if (!homeBottomSheetFragment!!.isAdded) {
            homeBottomSheetFragment!!.show(supportFragmentManager, "bottomSheet")
        }
    }

    override fun initViewAndData() {
        setContentView(R.layout.activity_shots)
        shotsToolbar.setPadding(0, getStatusBarHeight(), 0, 0)
        setImageTranslucent()
        setSupportActionBar(shotsToolbar)
        showFragment()
        if (presenter == null) {
            presenter = ShotsActivityPresenter(this)
        }
        presenterLifecycleHelper.addPresenter(presenter!!)
        presenter?.onPresenterCreate()
    }

    override fun initListeners() {
        userIcon.setOnClickListener {
            presenter?.userImageViewClick()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.shots_menu_search -> {
                val searchMenuView = shotsToolbar.findViewById<View>(R.id.shots_menu_search)
                val options = ActivityOptions.makeSceneTransitionAnimation(this, searchMenuView, getString(R.string.transition_search_back)).toBundle()
                startActivity(Intent(this, SearchActivity::class.java), options)
            }
            R.id.shots_menu_layout -> {
                popWindow.showPopUpWindow(shotsToolbar)
            }
            R.id.filter_list -> {
                presenter?.showBottomSheet()
            }

        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.shots_menu, menu)
        return true
    }

    private fun showFragment() {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, shotsListFragment)
        fragmentTransaction.commit()

    }

    override fun goDribbbeLoginActivity() {
        val intent = Intent(this, DribbbleLoginActivity::class.java)
        FabTransform.addExtras(intent, ContextCompat.getColor(this, R.color.primary), R.drawable.ic_person_black)
        val options = ActivityOptions.makeSceneTransitionAnimation(
                this, userIcon, getString(R.string.transition_dribbble_login))
        startActivity(intent, options.toBundle())
    }

    override fun goUserActivity() {

    }
}