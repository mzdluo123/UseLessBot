package win.rainchan.uselessbot.config

import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.contact.PermissionDeniedException
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

@ConfigurationProperties("uselessbot")
@Component
class GroupConfig {
    public lateinit var  groupList:List<Long>

    fun checkGroup(group:Group): Boolean {
        if (group.id !in groupList){
            return false
        }
        return true
    }
}
