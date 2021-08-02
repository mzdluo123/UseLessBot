package win.rainchan.uselessbot.controller

import net.mamoe.mirai.event.EventHandler
import net.mamoe.mirai.event.SimpleListenerHost
import net.mamoe.mirai.event.events.GroupMessageEvent
import org.springframework.stereotype.Controller
import win.rainchan.uselessbot.config.GroupConfig

@Controller
class TestController(private val conf:GroupConfig): SimpleListenerHost() {
    @EventHandler
    suspend fun GroupMessageEvent.onGroup(){

        if (group.id == 206073050L && message.contentToString() == "你好"){
            group.sendMessage("你好呀")
        }
    }


}
