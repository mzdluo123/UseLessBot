package win.rainchan.uselessbot.controller

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import net.mamoe.mirai.Bot
import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.event.EventHandler
import net.mamoe.mirai.event.SimpleListenerHost
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.data.MessageChain
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.message.data.buildForwardMessage
import net.mamoe.mirai.utils.ExternalResource.Companion.toExternalResource
import net.mamoe.mirai.utils.ExternalResource.Companion.uploadAsImage
import okhttp3.OkHttpClient
import okhttp3.Request
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import win.rainchan.uselessbot.config.BotConfig
import win.rainchan.uselessbot.dao.ImgDto
import win.rainchan.uselessbot.utils.DownloadException
import win.rainchan.uselessbot.utils.ImgDownloader

@Controller
class SetuController(
    private val gConf: BotConfig,
    private val client: OkHttpClient,
    private val downloader: ImgDownloader,
    private val imgs: ImgDto
) : SimpleListenerHost() {
    @Autowired
    lateinit var bot: Bot

    private val logger = LogFactory.getLog(SetuController::class.java)

    @EventHandler
    suspend fun GroupMessageEvent.onMsg() {
        if (gConf.checkGroup(group)) {
            val msg = message.contentToString()
            if (msg == "涩图来") {
                group.sendMessage(getSetu(group))
                return
            }
//            if (msg == "贴贴") {
//                val img = downloader.downloadImg("https://v1.yurikoto.com/wallpaper").toExternalResource()
//                img.sendAsImageTo(group)
//                img.close()
//                return
//            }
            if (msg == "不够涩") {
                group.sendMessage("嘻嘻......马上就来")
                val msg = buildForwardMessage {
                    (0..10).map {
                        async {
                            try {
                                val chain = getSetu(group)
                                add(bot, chain)
                            } catch (e: Exception) {
                                logger.error("下载图片失败", e)
                            }
                        }
                    }.awaitAll()
                }
                group.sendMessage(msg)
            }
            if (msg == "贴贴") {
                val url = "https://pximg.rainchan.win/img?img_id=${imgs.random().id}&web=true"
                val img = downloader.downloadImg(url).toExternalResource()
                val img2 = downloader.downloadImg("https://v1.yurikoto.com/wallpaper").toExternalResource()
                val msg = buildForwardMessage {
                    add(1023718443,"贴贴",img2.uploadAsImage(group))
                    add(1023718443,"贴贴",img.uploadAsImage(group) + PlainText(url))
                }
                group.sendMessage(msg)
                img.close()
                img2.close()
            }
        }
    }

    suspend fun getSetu(group: Group): MessageChain {
        val rsp = withContext(Dispatchers.IO) {
            client.newCall(Request.Builder().get().url(" https://pximg.rainchan.win/img").build())
                .execute()
        }
        val content = rsp.body()?.bytes()?.toExternalResource() ?: throw  DownloadException(rsp.message())
        val msg = content.uploadAsImage(group) + PlainText(" ${rsp.request().url().toString()}")
        content.close()
        return msg
    }
}
