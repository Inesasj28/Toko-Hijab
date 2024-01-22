package com.dikodei.sipasan.helper

import android.content.Context
import com.google.gson.Gson
import id.ac.sttpyk.tokohijab.model.LoginModel

class SessionManager(context : Context) {
    companion object{
        fun init(context: Context) : SessionManager{
            return SessionManager(context)
        }
    }

    private val sharedPrefsName = "SipasanPrefs"

    private val prefs = context.getSharedPreferences(sharedPrefsName,0)
    private val gson = Gson()

    var loginSession : LoginModel?
        get(){
            val jsonLogin = prefs.getString("jsonLogin",null)
            return gson.fromJson(jsonLogin, LoginModel::class.java)
        }
        set(value) {
            val editor = prefs.edit()
            val jsonLogin = gson.toJson(value)
            editor.putString("jsonLogin",jsonLogin)
            editor.apply()
        }


    val isLoggin : Boolean
        get() = loginSession != null

    fun logout(){
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }

}