package win.rainchan.uselessbot.controller

import net.mamoe.mirai.event.EventHandler
import net.mamoe.mirai.event.SimpleListenerHost
import net.mamoe.mirai.event.events.GroupMessageEvent
import org.springframework.stereotype.Controller
import win.rainchan.uselessbot.config.BotConfig

@Controller
class HelpController(private val conf:BotConfig): SimpleListenerHost() {
    @EventHandler
    suspend fun GroupMessageEvent.onGroup(){
        if (conf.checkGroup(group)){
            val msg = message.contentToString()
            if ( msg== "你好"){
                group.sendMessage("你好呀")
            }
            if (msg =="/help"){
                group.sendMessage("""UseLessBot-没啥用的机器人
                    |[大盘] 查看大盘信息
                    |[日k] 查看每日k线图
                    |[基金](基金代码) 查看基金信息
                    |[涩图来] 来一张图
                    |[不够涩] 来一堆图
                    |[贴贴] 来一张贴贴图
                    |[加自选](代码)
                    |[删自选](代码)
                    |[我的基金] 查看基金信息
                    |AT我+文字可以和我聊天哦
                """.trimMargin())
            }
        }
    }


}
