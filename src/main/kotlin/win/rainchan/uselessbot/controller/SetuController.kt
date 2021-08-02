package win.rainchan.uselessbot.controller

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.mamoe.mirai.event.EventHandler
import net.mamoe.mirai.event.SimpleListenerHost
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.utils.ExternalResource
import net.mamoe.mirai.utils.ExternalResource.Companion.sendAsImageTo
import net.mamoe.mirai.utils.ExternalResource.Companion.toExternalResource
import net.mamoe.mirai.utils.ExternalResource.Companion.uploadAsImage
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.stereotype.Controller
import win.rainchan.uselessbot.config.GroupConfig
import win.rainchan.uselessbot.utils.ImgDownloader

@Controller
class SetuController(private val gConf: GroupConfig, private val client: OkHttpClient,private val downloader: ImgDownloader) : SimpleListenerHost() {

    @EventHandler
    suspend fun GroupMessageEvent.onMsg() {
        if (gConf.checkGroup(group)) {
            val msg = message.contentToString()
            if (msg == "涩图来") {
                val rsp = withContext(Dispatchers.IO) {
                    client.newCall(Request.Builder().get().url("https://pximg2.rainchan.win/img").build())
                        .execute()
                }
                val content = rsp.body()?.bytes()?.toExternalResource() ?: return
                group.sendMessage(content.uploadAsImage(group)+PlainText(rsp.request().url().toString()))
                content.close()
                return
            }
            if (msg == "贴贴"){
                val img = downloader.downloadImg("https://v1.yurikoto.com/wallpaper").toExternalResource()
                img.sendAsImageTo(group)
                img.close()
            }
        }
    }
}
