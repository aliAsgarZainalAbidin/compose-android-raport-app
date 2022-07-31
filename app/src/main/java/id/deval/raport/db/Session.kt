package id.deval.raport.db

import android.content.Context
import android.content.SharedPreferences

class Session(context: Context) {
    var pref: SharedPreferences
    var editor : SharedPreferences.Editor

    companion object {
        val PREF_NAME = "raport-app"
    }

    init {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        editor = pref.edit()
    }

    fun logout(){
        editor.clear()
        editor.commit()
    }
}