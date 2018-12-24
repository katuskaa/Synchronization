package diabetes.com.synchronization.common.data.server

import android.content.Context

interface CommunicationChannel {

    fun initializeCommunicationChannel(context: Context, serverUrl: String)
    fun isConnected(): Boolean
    fun sendMessage(serverTransaction: ServerTransaction<*, *>)
    fun destroyCommunicationChannel()
}