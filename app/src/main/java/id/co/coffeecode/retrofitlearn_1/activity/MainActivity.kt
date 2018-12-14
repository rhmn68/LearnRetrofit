package id.co.coffeecode.retrofitlearn_1.activity

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import id.co.coffeecode.retrofitlearn_1.R
import id.co.coffeecode.retrofitlearn_1.model.ModelGithub
import id.co.coffeecode.retrofitlearn_1.network.Service
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //initialized gson
        val gson = GsonBuilder().create()

        //initialized retrofit
        val retrofit: Retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("https://api.github.com/")
            .build()

        val service: Service = retrofit.create(
            Service::class.java
        )

        //get data from github by username
        service.getUser("rhmn68")
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    getData(it)
                    Log.d(TAG,"Json Api: $it")
                },
                {
                    Log.e("Error", it.message)
                }
            )
    }

    private fun getData(user: ModelGithub?) {
        val image = findViewById<ImageView>(R.id.image)
        val username = findViewById<TextView>(R.id.username)
        val company = findViewById<TextView>(R.id.company)
        Glide.with(this).load(user?.avatarUrl).into(image)
        username.text = user?.name
        company.text = user?.company
    }

    companion object {
        val  TAG = "COBA"
    }
}
