package win.rainchan.uselessbot.controller

import net.mamoe.mirai.Bot
import net.mamoe.mirai.contact.Contact
import net.mamoe.mirai.event.EventHandler
import net.mamoe.mirai.event.SimpleListenerHost
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.data.ForwardMessageBuilder
import net.mamoe.mirai.message.data.ForwardMessageDsl
import net.mamoe.mirai.message.data.buildForwardMessage
import net.mamoe.mirai.utils.ExternalResource.Companion.sendAsImageTo
import net.mamoe.mirai.utils.ExternalResource.Companion.toExternalResource
import net.mamoe.mirai.utils.ExternalResource.Companion.uploadAsImage
import net.mamoe.mirai.utils.withUse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import win.rainchan.uselessbot.config.GroupConfig
import win.rainchan.uselessbot.utils.ImgDownloader

@Controller
class StockController(private val gConf: GroupConfig, private val downloader: ImgDownloader) : SimpleListenerHost() {

    private val CODE = arrayListOf<String>("000001","000300","000016","000003","000688")
    @Autowired
    lateinit var bot: Bot

    @EventHandler
    suspend fun GroupMessageEvent.onMsg() {

        if (!gConf.checkGroup(group)) {
            return
        }
        val msg = message.contentToString()
        if (msg == "大盘") {
            group.sendMessage("请稍后....")
            val msgChain = buildForwardMessage {
                for (i in CODE) {
                    val img =
                        downloader.downloadImg("http://webquotepic.eastmoney.com/GetPic.aspx?nid=1.${i}&imageType=r")
                            .toExternalResource()
                    this.add(bot, img.uploadAsImage(group))
                    img.close()
                }

            }
            group.sendMessage(msgChain)
        }
        if (msg== "日k"){

            group.sendMessage("请稍后....")
            val msgChain = buildForwardMessage {
                for (i in CODE) {
                    val img =
                        downloader.downloadImg("http://webquoteklinepic.eastmoney.com/GetPic.aspx?nid=1.${i}&unitWidth=-6&ef=&formula=RSI&type=&imageType=KXL")
                            .toExternalResource()
                    this.add(bot, img.uploadAsImage(group))
                    img.close()
                }

            }
            group.sendMessage(msgChain)

        }
        if (msg.startsWith("基金")){
            val code = msg.substring(2)
            if (code.isEmpty()){
                group.sendMessage("请提供基金代码")
                return
            }
            val day = downloader.downloadImg("http://j4.dfcfw.com/charts/pic7/${code}.png").toExternalResource()
            day.sendAsImageTo(group)
            day.close()

        }
    }
}
