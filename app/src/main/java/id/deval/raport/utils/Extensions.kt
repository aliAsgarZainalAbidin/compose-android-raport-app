package id.deval.raport.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
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