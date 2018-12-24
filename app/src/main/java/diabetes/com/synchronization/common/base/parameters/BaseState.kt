package diabetes.com.synchronization.common.base.parameters

import android.os.Bundle

interface BaseState {

    fun saveInstanceState(outState: Bundle?)

    fun restoreInstanceState(savedInstanceState: Bundle)
}