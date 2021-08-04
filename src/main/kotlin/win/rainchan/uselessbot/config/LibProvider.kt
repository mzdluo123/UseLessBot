package win.rainchan.uselessbot.config

import okhttp3.OkHttpClient
import org.dizitart.no2.Nitrite
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class LibProvider {
    @Autowired
    private lateinit var config: BotConfig

    @Bean
    fun okhttp()= OkHttpClient.Builder()
        //.cookieJar(CookieJar.NO_COOKIES)
        .build()

    @Bean
    fun db(): Nitrite = Nitrite.builder().compressed().filePath(config.dbPath).openOrCreate()
}
