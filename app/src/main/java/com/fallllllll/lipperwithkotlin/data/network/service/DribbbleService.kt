package com.fallllllll.lipperwithkotlin.data.network.service

import com.fall.retrofitannotation.RetrofitService
import com.fallllllll.lipperwithkotlin.core.constants.BASE_URL
import com.fallllllll.lipperwithkotlin.data.databean.ShotBean
import com.fallllllll.lipperwithkotlin.data.local.user.LipperUser
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**
 * Created by fall on 2017/6/2/002.
 * GitHub :  https://github.com/348476129/LipperWithKotlin
 */
@RetrofitService(BASE_URL)
interface DribbbleService {
    @GET("shots")
     fun getShot(@Query("list") list: String, @Query("timeframe") timeframe: String, @Query("sort") sort: String, @Query("page") page: String, @Query("per_page") perPage: String): Flowable<List<ShotBean>>

    @GET("user")
     fun getUserInfo(@Header("Authorization") token:String): Flowable<LipperUser>

}
fun main(args:Array<String>){
    print(Class.forName("com.fallllllll.lipperwithkotlin.data.network.service.DribbbleService").canonicalName)
}