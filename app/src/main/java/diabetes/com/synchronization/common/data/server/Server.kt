package diabetes.com.synchronization.common.data.server

import android.content.Context

abstract class Server(protected val serverUrl: String) {

    protected abstract val httpChannel : CommunicationChannel

    fun connect(context: Context) {
        httpChannel.initializeCommunicationChannel(context, this.serverUrl)
    }

    fun isConnected(): Boolean {
        return httpChannel.isConnected()
    }

    fun disconnect() {
        httpChannel.destroyCommunicationChannel()
    }

    protected fun executeTransaction(serverTransaction: ServerTransaction<*, *>) {
        serverTransaction.onLoadingStarted()
        httpChannel.sendMessage(serverTransaction)
    }

}