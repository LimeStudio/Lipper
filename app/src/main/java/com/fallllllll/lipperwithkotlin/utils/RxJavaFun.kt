package com.fallllllll.lipperwithkotlin.utils

import com.fallllllll.lipperwithkotlin.core.rxjava.ConvertToAPIException
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by fallllllll on 2017/6/13/013.
 * GitHub :  https://github.com/348476129/Lipper
 */
fun <T> Flowable<T>.commonChange() :Flowable<T> {
return    this.onErrorResumeNext(ConvertToAPIException())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}