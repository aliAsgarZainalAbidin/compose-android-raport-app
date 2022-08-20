package id.deval.raport.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import id.deval.raport.R

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun TextInputEditText.hideError(){
    doAfterTextChanged {
        error = null
    }
}

fun AutoCompleteTextView.hideError(){
    doAfterTextChanged {
        error = null
    }
}

fun Int.parseIntToMonth(): String{
    val month = when(this){
        1 -> "Januari"
        2 -> "Februari"
        3 -> "Maret"
        4 -> "April"
        5 -> "Mei"
        6 -> "Juni"
        7 -> "Juli"
        8 -> "Agustus"
        9 -> "September"
        10 -> "Oktober"
        11 -> "November"
        12 -> "Desember"
        else -> "null"
    }
   return month
}

fun Float.parseIntToChar(): String{
    val char = when(this){
        in 86f..100f -> "A"
        in 81f..85f -> "A-"
        in 76f..80f -> "B+"
        in 71f..75f -> "B"
        in 66f..70f -> "B-"
        in 61f..65f -> "C+"
        in 56f..60f -> "C"
        in 51f..55f -> "C-"
        in 46f..50f -> "D+"
        in 0f..45f -> "D"
        else -> "E"
    }
    return char
}

fun Int.showToast(errorCode:Int){
    val message = when(errorCode){
        401-> "Tidak memiliki izin"
        404-> "Data tidak ditemukan"
        in 400..451->{
            "Client Sedang Error"
        }
        500-> "I"
        in 500..599->""
        else -> ""
    }
}

fun Activity.getMainNavController() : NavController {
    return this.findNavController(R.id.fcm_mainActivity_basecontainer)
}

fun FragmentActivity.getMainNavController() : NavController {
    return this.findNavController(R.id.fcm_mainActivity_basecontainer)
}

fun Fragment.getSecNavController() : NavController {
    return this.childFragmentManager.findFragmentById(R.id.fcv_base_container)?.findNavController()
        ?: this.findNavController()
}

fun Context.showToast(message : String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}