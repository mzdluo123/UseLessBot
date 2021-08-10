package win.rainchan.uselessbot.controller

import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.*
import net.mamoe.mirai.Bot
import net.mamoe.mirai.event.EventHandler
import net.mamoe.mirai.event.SimpleListenerHost
import net.mamoe.mirai.event.events.GroupMessageEvent
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.message.data.at
import net.mamoe.mirai.message.data.firstIsInstanceOrNull
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import win.rainchan.uselessbot.config.BotConfig
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.LinkedBlockingDeque

val JSON = MediaType.parse("application/json; charset=utf-8")


@Controller
class ChatController(private val config: BotConfig, private val bot: Bot) : SimpleListenerHost() {

    @Autowired
    lateinit var client: OkHttpClient

    @Autowired
    lateinit var mapper: ObjectMapper

    val memberTmp = ConcurrentHashMap<Long, LinkedBlockingDeque<String>>()

    init {
        bot.launch {
            while (isActive) {
                delay(30 * 60 * 1000)
                memberTmp.clear()
            }
        }
    }

    @EventHandler
    suspend fun GroupMessageEvent.onMsg() {

        if (!config.checkGroup(group)) {
            return
        }
        if (config.chatAPI == null) {
            return
        }
        if (message.firstIsInstanceOrNull<At>()?.target == bot.id) {
            val msg = message.firstIsInstanceOrNull<PlainText>() ?: return
            if (!memberTmp.containsKey(sender.id)) {
                memberTmp[sender.id] = LinkedBlockingDeque()
            }
            val temp = memberTmp[sender.id]!!
            while (temp.size > 10) {
                temp.pop()
            }
            temp.add(msg.content)
            val param = temp.toList()
            withContext(Dispatchers.IO) {
               val rsp =  client.newCall(Request.Builder().post(
                    RequestBody.create(JSON, mapper.writeValueAsString(param)))
                   .url(config.chatAPI!!)
                    .build()).execute()
                if (rsp.isSuccessful){
                    val body = rsp.body()?.string() ?: "咋也不知道呢"
                    group.sendMessage(sender.at() + PlainText(body))
                }
                rsp.close()
            }
        }

    }
}
