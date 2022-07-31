package id.deval.raport.utils

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import id.deval.raport.R

object HelperView {
    fun getMainNavController(activity: Activity) : NavController{
        return activity.findNavController(R.id.fcm_mainActivity_basecontainer)
    }

    fun getSecNavController(fragment: Fragment) : NavController{
        return fragment.childFragmentManager.findFragmentById(R.id.fcv_base_container)?.findNavController() ?: fragment.findNavController()
    }

    fun showToast(message: String, context: Context){
        return Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}