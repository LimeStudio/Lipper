package com.fallllllll.lipperwithkotlin.core.expandFunction

import java.util.*

/**
 * Created by fallllllll on 2017/6/9/009.
 * GitHub :  https://github.com/348476129/Lipper
 */
fun getRandomString(length: Int): String {
    val base = "abcdefghijklmnopqrstuvwxyz0123456789"
    val random = java.util.Random()
    val sb = StringBuilder()
    for (i in 0..length) {
        val number = random.nextInt(base.length)
        sb.append(number)
    }
    return sb.toString()
}