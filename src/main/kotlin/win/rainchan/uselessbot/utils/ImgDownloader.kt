package win.rainchan.uselessbot.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.stereotype.Component
import java.lang.Exception

class DownloadException(private val msg:String):Exception(msg)

@Component
class ImgDownloader(private val client:OkHttpClient) {

    suspend fun downloadImg(url: String): ByteArray {
        val rsp = withContext(Dispatchers.IO) {
            client.newCall(Request.Builder().get().url(url).build())
                .execute()
        }
        return rsp.body()?.bytes() ?: throw DownloadException(rsp.message())
    }
}
