package diabetes.com.synchronization.common.base.application

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.arch.lifecycle.ProcessLifecycleOwner
import android.support.multidex.MultiDexApplication
import diabetes.com.synchronization.communication.providers.ApplicationDataProvider
import diabetes.com.synchronization.communication.providers.DataProvider

class BaseApplication : MultiDexApplication(), LifecycleObserver {

    companion object {
        lateinit var application: BaseApplication
            private set

        lateinit var applicationDataProvider: ApplicationDataProvider
            private set

    }

    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onApplicationCreated() {
        application = this
        applicationDataProvider = DataProvider(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onApplicationForegrounded() {
        applicationDataProvider.connect()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onApplicationBackgrounded() {
        applicationDataProvider.disconnect()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onApplicationDestroyed() {
    }

    fun finishApplication() {
        val launchIntent = application.packageManager.getLaunchIntentForPackage(application.packageName)
        application.startActivity(launchIntent)
        android.os.Process.killProcess(android.os.Process.myPid())
    }
}