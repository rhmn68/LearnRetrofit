package id.co.coffeecode.retrofitlearn_1.network

import id.co.coffeecode.retrofitlearn_1.model.ModelGithub
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface Service {
    @GET("users/{username}")
    fun getUser(@Path("username") username: String): Observable<ModelGithub>
}