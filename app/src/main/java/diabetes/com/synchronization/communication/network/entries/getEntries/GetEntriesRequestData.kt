package diabetes.com.synchronization.communication.network.entries.getEntries

import diabetes.com.synchronization.common.data.transaction.RequestData

open class GetEntriesRequestData : RequestData() {

    override fun getRequestBody(): RequestBody = GetEntriesRequestBody(this)

    class GetEntriesRequestBody(requestData: GetEntriesRequestData) : RequestBody()
}