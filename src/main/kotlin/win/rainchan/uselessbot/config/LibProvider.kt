package win.rainchan.uselessbot.config

import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.OkHttpClient
import org.dizitart.no2.Nitrite
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import win.rainchan.uselessbot.dao.ImgDto

@Component
class LibProvider {
    @Autowired
    private lateinit var config: BotConfig

    @Bean
    fun okhttp() = OkHttpClient.Builder()
        //.cookieJar(CookieJar.NO_COOKIES)
        .build()

    @Bean
    fun db(): Nitrite = Nitrite.builder().compressed().filePath(config.dbPath).openOrCreate()

    @Bean
    fun mapper() = ObjectMapper()

    @Bean
    fun getImgList(mapper: ObjectMapper): ImgDto? {
        ClassPathResource("yuri.json").inputStream.use {
            return mapper.readValue(it, ImgDto::class.java)
        }
    }
}
