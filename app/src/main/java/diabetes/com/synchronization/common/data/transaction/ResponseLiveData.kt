package diabetes.com.synchronization.common.data.transaction

class ResponseLiveData<out T>(val resourceStatus: ResourceStatus, val responseData: T?, val errorCode: Int?) {

    val wasRequestSuccessful: Boolean
        get() = this.resourceStatus == ResourceStatus.SUCCESS

    constructor() : this(ResourceStatus.LOADING, null, null)
    constructor(responseData: T?) : this(ResourceStatus.SUCCESS, responseData, null)
    constructor(errorCode: Int?) : this(ResourceStatus.ERROR, null, errorCode)
}

enum class ResourceStatus {
    SUCCESS, ERROR, LOADING
}