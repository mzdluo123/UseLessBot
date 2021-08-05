package win.rainchan.uselessbot.controller

import net.mamoe.mirai.Bot
import net.mamoe.mirai.event.EventHandler
import net.mamoe.mirai.event.SimpleListenerHost
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.data.buildForwardMessage
import net.mamoe.mirai.utils.ExternalResource.Companion.sendAsImageTo
import net.mamoe.mirai.utils.ExternalResource.Companion.toExternalResource
import net.mamoe.mirai.utils.ExternalResource.Companion.uploadAsImage
import org.dizitart.no2.Nitrite
import org.dizitart.no2.objects.filters.ObjectFilters
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import win.rainchan.uselessbot.config.BotConfig
import win.rainchan.uselessbot.dao.StockUser
import win.rainchan.uselessbot.utils.ImgDownloader

@Controller
class StockController(private val gConf: BotConfig, private val downloader: ImgDownloader) : SimpleListenerHost() {

    private val CODE = arrayListOf<String>("000001", "000300", "000016", "000003", "000688")

    @Autowired
    lateinit var bot: Bot

    @Autowired
    lateinit var db: Nitrite

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
        if (msg == "日k") {

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
        if (msg.startsWith("基金")) {
            val code = getStockCode(msg,2)
            if (code == null){
                group.sendMessage("无效代码")
                return
            }
            val day = downloader.downloadImg("http://j4.dfcfw.com/charts/pic7/${code}.png").toExternalResource()
            day.sendAsImageTo(group)
            day.close()

        }
        if (msg.startsWith("加自选")) {
            val code = getStockCode(msg,3)
            if (code == null){
                group.sendMessage("无效代码")
                return
            }
            addStock(sender.id, code)
            group.sendMessage("成功")
        }
        if (msg.startsWith("删自选")) {
            val code = getStockCode(msg,3)
            if (code == null){
                group.sendMessage("无效代码")
                return
            }
            delStock(sender.id, code)
            group.sendMessage("成功")
        }

        if (msg == "我的基金") {
            val stocks = listStock(sender.id)
            if (stocks == null || stocks.isEmpty()) {
                group.sendMessage("你还没有任何基金")
                return
            }
            val msg = buildForwardMessage {
                stocks.forEach {
                    val img = downloader.downloadImg("http://j4.dfcfw.com/charts/pic7/${it}.png").toExternalResource()
                    add(bot, img.uploadAsImage(group))
                    img.close()
                }
            }
            group.sendMessage(msg)
        }
    }

    fun getStockCode(msg: String,start:Int): String? {
        val code = msg.substring(start).trim()
        if (code.isEmpty() || code.length != 6 || code.toIntOrNull() == null) {
            return null
        }
        return code
    }

    fun addDefault(uid: Long) {
        val repo = db.getRepository(StockUser::class.java)
        repo.insert(StockUser(uid))
    }

    fun addStock(uid: Long, code: String) {
        val repo = db.getRepository(StockUser::class.java)
        val doc = repo.find(ObjectFilters.eq("userId", uid)).firstOrDefault()
        if (doc == null) {
            addDefault(uid)
            addStock(uid, code)
            return
        }
        doc.stocks.add(code)
        repo.update(doc)
    }

    fun delStock(uid: Long, code: String) {
        val repo = db.getRepository(StockUser::class.java)
        val doc = repo.find(ObjectFilters.eq("userId", uid)).firstOrDefault() ?: return
        doc.stocks.remove(code)
        repo.update(doc)
    }

    fun listStock(uid: Long): MutableList<String>? {
        val repo = db.getRepository(StockUser::class.java)
        val doc = repo.find(ObjectFilters.eq("userId", uid)).firstOrDefault() ?: return null
        return doc.stocks
    }
}
