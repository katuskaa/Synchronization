package diabetes.com.synchronization.common.base.fragments

import android.os.Bundle
import diabetes.com.synchronization.common.base.views.BaseViewModels

abstract class BaseViewModelFragment<VIEW_MODELS : BaseViewModels, HANDLER : Any> : BaseHandlerFragment<HANDLER>() {

    protected lateinit var viewModels: VIEW_MODELS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.viewModels = this.initializeViewModels()
        this.viewModels.setViewModelsObservers()
    }

    protected abstract fun initializeViewModels(): VIEW_MODELS
}