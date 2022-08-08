package id.deval.raport.db

import android.content.Context
import android.content.SharedPreferences
import id.deval.raport.db.models.Account

class Session(context: Context) {
    var pref: SharedPreferences
    var editor : SharedPreferences.Editor

    val token get() = pref.getString(TOKEN,"")
    val username get() = pref.getString(USERNAME,"")
    val role get() = pref.getString(ROLE,"")
    val name get() = pref.getString(NAME,"")
    val id get() = pref.getString(ID,"")

    companion object {
        val PREF_NAME = "raport-app"
        val ID = "ID"
        val USERNAME = "USERNAME"
        val ROLE = "ROLE"
        val NAME = "NAME"
        val TOKEN = "TOKEN"
    }

    init {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        editor = pref.edit()
    }

    fun login(account: Account, token:String?){
        editor.putString(ID, account.id)
        editor.putString(USERNAME, account.username)
        editor.putString(ROLE, account.role)
        editor.putString(NAME, account.name)
        editor.putString(TOKEN, token)
        editor.commit()
    }

    fun getAccount(): Account{
        return Account(
            id = pref.getString(ID,""),
            role = pref.getString(ROLE,""),
            username = pref.getString(USERNAME, ""),
            name = pref.getString(NAME, ""),
            token = pref.getString(TOKEN, "")
        )
    }

    fun logout(){
        editor.clear()
        editor.commit()
    }
}