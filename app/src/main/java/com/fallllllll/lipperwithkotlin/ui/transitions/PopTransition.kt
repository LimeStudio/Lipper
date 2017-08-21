package com.fallllllll.lipperwithkotlin.ui.transitions

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.transition.TransitionValues
import android.transition.Visibility
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

/**
 * Created by fall on 2017/6/28/028.
 * GitHub :  https://github.com/348476129/Lipper
 */
class PopTransition(context: Context, attributeSet: AttributeSet):Visibility(context,attributeSet) {
    override fun onAppear(sceneRoot: ViewGroup, view: View, startValues: TransitionValues,
                          endValues: TransitionValues): Animator {
        view.alpha = 0f
        view.scaleX = 0f
        view.scaleY = 0f
        return ObjectAnimator.ofPropertyValuesHolder(
                view,
                PropertyValuesHolder.ofFloat(View.ALPHA, 1f),
                PropertyValuesHolder.ofFloat(View.SCALE_X, 1f),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f))
    }

    override fun onDisappear(sceneRoot: ViewGroup, view: View, startValues: TransitionValues,
                             endValues: TransitionValues): Animator {
        return ObjectAnimator.ofPropertyValuesHolder(
                view,
                PropertyValuesHolder.ofFloat(View.ALPHA, 0f),
                PropertyValuesHolder.ofFloat(View.SCALE_X, 0f),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, 0f))
    }
}