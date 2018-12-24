package diabetes.com.synchronization.common.base.activities

import android.os.Bundle
import diabetes.com.synchronization.common.base.views.BaseViewModels

abstract class BaseViewModelActivity<VIEW_MODELS : BaseViewModels> : BaseUIElementsActivity() {

    protected lateinit var viewModels: VIEW_MODELS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.viewModels = this.initializeViewModels()
        this.viewModels.setViewModelsObservers()
    }

    protected abstract fun initializeViewModels(): VIEW_MODELS
}