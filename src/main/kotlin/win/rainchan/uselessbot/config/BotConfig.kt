package win.rainchan.uselessbot.config

import net.mamoe.mirai.contact.Group
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@ConfigurationProperties("uselessbot")
@Component
class BotConfig {
    public lateinit var  groupList:List<Long>

    public lateinit var dbPath:String

    fun checkGroup(group:Group): Boolean {
        if (group.id !in groupList){
            return false
        }
        return true
    }
}
