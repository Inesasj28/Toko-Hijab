package id.ac.sttpyk.tokohijab.api

import id.ac.sttpyk.tokohijab.model.HijabModel
import id.ac.sttpyk.tokohijab.model.LoginModel
import id.ac.sttpyk.tokohijab.model.SimpanModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {
    //hijab

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String,
    ): Call<LoginModel>

    @FormUrlEncoded
    @POST("hijab/simpan")
    fun entrihijab(
        @Field("hijab") hijab: String,
        @Field("harga") harga: String
    ): Call<SimpanModel>

    @FormUrlEncoded
    @POST("hijab/hapus")
    fun hapushijab(
        @Field("id") id: Int?
    ): Call<SimpanModel>

    @FormUrlEncoded
    @POST("hijab/edit")
    fun edithijab(
        @Field("id") id: Int?,
        @Field("hijab") hijab: String,
        @Field("harga") harga: String
    ): Call<SimpanModel>


    @GET("hijab/tampil")
    fun tampilhijab(): Call<HijabModel>



    companion object {
        fun create (): Api {
            val httpClient = OkHttpClient.Builder()
            var logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(logging)

            val retrofit : Retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                //.baseUrl("http://10.0.2.2:8000/api/")
                .baseUrl("http://192.168.10.57:8000/api/")
                .client(httpClient.build())
                .build()

            return retrofit.create(Api::class.java)
        }
    }
}