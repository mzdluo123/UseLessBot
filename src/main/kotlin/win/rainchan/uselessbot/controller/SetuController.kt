package win.rainchan.uselessbot.controller

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.mamoe.mirai.event.EventHandler
import net.mamoe.mirai.event.SimpleListenerHost
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.utils.ExternalResource
import net.mamoe.mirai.utils.ExternalResource.Companion.toExternalResource
import net.mamoe.mirai.utils.ExternalResource.Companion.uploadAsImage
import okhttp3.OkHttpClient
import okhttp3.Request
import org.springframework.stereotype.Controller
import win.rainchan.uselessbot.config.GroupConfig

@Controller
class SetuController(private val gConf: GroupConfig, private val client: OkHttpClient) : SimpleListenerHost() {

    @EventHandler
    suspend fun GroupMessageEvent.onMsg() {
        if (gConf.checkGroup(group)) {
            if (message.contentToString() == "涩图来") {
                val rsp = withContext(Dispatchers.IO) {
                    client.newCall(Request.Builder().get().url("https://pximg2.rainchan.win/img").build())
                        .execute()
                }
                val content = rsp.body()?.bytes()?.toExternalResource() ?: return
                group.sendMessage(content.uploadAsImage(group)+PlainText(rsp.request().url().toString()))
            }
        }
    }
}
