package diabetes.com.synchronization.common.data.server.httpChannel

import android.content.Context
import com.android.volley.Cache
import com.android.volley.RequestQueue
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import diabetes.com.synchronization.common.data.server.CommunicationChannel
import diabetes.com.synchronization.common.data.server.ServerTransaction
import diabetes.com.synchronization.common.helpers.getInternalStorageApplicationCacheFolder
import java.io.File
import java.net.HttpURLConnection
import java.net.URL

class HttpChannel : CommunicationChannel {

    private var requestQueue: RequestQueue? = null

    override fun initializeCommunicationChannel(context: Context, serverUrl: String) {

        val httpCache = HttpCache(context)
        val network = BasicNetwork(CustomHurlStack())
        this.requestQueue = RequestQueue(httpCache.cache, network)
        this.requestQueue?.start()
    }

    override fun destroyCommunicationChannel() {
        this.requestQueue?.stop()
    }

    override fun sendMessage(serverTransaction: ServerTransaction<*, *>) {
        val httpServerTransaction: HttpServerTransaction<*, *> = (serverTransaction as HttpServerTransaction<*, *>)
        this.requestQueue?.add(httpServerTransaction.request)
    }

    override fun isConnected(): Boolean {
        return true
    }

    private inner class HttpCache(context: Context) {

        private val CACHE_SIZE: Int = 1024 * 1024
        val cache: Cache

        init {

            val internalApplicationFolder = getInternalStorageApplicationCacheFolder(context)
            val cacheFile = File(internalApplicationFolder, "volleyCache")
            this.cache = DiskBasedCache(cacheFile, CACHE_SIZE)
        }
    }

    private inner class CustomHurlStack : HurlStack() {

        override fun createConnection(url: URL?): HttpURLConnection {
            val httpsURLConnection = super.createConnection(url) as HttpURLConnection
            return httpsURLConnection
        }
    }
}