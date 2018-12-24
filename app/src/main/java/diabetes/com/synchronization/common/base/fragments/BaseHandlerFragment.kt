package diabetes.com.synchronization.common.base.fragments

import android.content.Context

abstract class BaseHandlerFragment<HANDLER : Any> : BaseFragment() {

    protected lateinit var handler: HANDLER

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        @Suppress("UNCHECKED_CAST")
        this.handler = this.activity as HANDLER
    }
}