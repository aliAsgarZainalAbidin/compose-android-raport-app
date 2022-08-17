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