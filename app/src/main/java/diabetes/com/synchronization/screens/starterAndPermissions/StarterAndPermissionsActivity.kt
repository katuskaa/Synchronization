package diabetes.com.synchronization.screens.starterAndPermissions

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import diabetes.com.synchronization.R
import diabetes.com.synchronization.common.base.activities.BaseApplicationActivity
import diabetes.com.synchronization.common.base.parameters.BaseParameters
import diabetes.com.synchronization.common.base.parameters.BaseState
import diabetes.com.synchronization.common.base.views.BaseViewModels
import diabetes.com.synchronization.common.base.views.BaseViews
import diabetes.com.synchronization.screens.main.MainActivity


class StarterAndPermissionsActivity : BaseApplicationActivity<StarterAndPermissionsActivity.Parameters, StarterAndPermissionsActivity.State, StarterAndPermissionsActivity.ViewModels, StarterAndPermissionsActivity.Views>(), StarterAndPermissionsFragmentHandler {

    override fun initializeParameters(): Parameters = Parameters()
    override fun initializeState(parameters: Parameters): State = State()
    override fun initializeViewModels(): ViewModels = ViewModels()
    override fun initializeViews(): Views = Views()

    override fun setActivityLayout() = R.layout.starter_and_permissions_activity__activity_layout
    override fun setDrawer() = null
    override fun setToolbar() = null


    inner class Parameters : BaseParameters {

        val READ_EXTERNAL_STORAGE__REQUEST_CODE: Int = 1

        override fun loadParameters(extras: Bundle?) {}
    }

    inner class State : BaseState {
        override fun saveInstanceState(outState: Bundle?) {}
        override fun restoreInstanceState(savedInstanceState: Bundle) {}
    }

    inner class ViewModels : BaseViewModels {
        override fun setViewModelsObservers() {}
    }

    inner class Views : BaseViews {

        var starterAndPermissionsFragment = StarterAndPermissionsFragment.newInstance()

        override fun modifyViews(context: Context?, bundle: Bundle?) {}
        override fun modifyFragments(context: Context?) {}
    }

    override fun onActivityLoadingFinished() {
        val readExternalStoragePermissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)

        if (readExternalStoragePermissionCheck == PackageManager.PERMISSION_GRANTED) {
            MainActivity.startActivity(this@StarterAndPermissionsActivity)
            this@StarterAndPermissionsActivity.finish()
        } else {
            this@StarterAndPermissionsActivity.requestPermissions()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            this@StarterAndPermissionsActivity.parameters.READ_EXTERNAL_STORAGE__REQUEST_CODE -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                MainActivity.startActivity(this@StarterAndPermissionsActivity)
            } else {
                val snackBar = Snackbar.make(super.activityLayout, getString(R.string.starter_and_permissions__allow_permissions), Snackbar.LENGTH_INDEFINITE)
                snackBar.setAction(getString(R.string.starter_and_permissions__accept)) { this@StarterAndPermissionsActivity.requestPermissions() }.show()
            }
        }
    }


    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), this@StarterAndPermissionsActivity.parameters.READ_EXTERNAL_STORAGE__REQUEST_CODE)
    }

}
