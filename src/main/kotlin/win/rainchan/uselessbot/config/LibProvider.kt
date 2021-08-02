package win.rainchan.uselessbot.config

import okhttp3.CookieJar
import okhttp3.OkHttpClient
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class LibProvider {

    @Bean
    fun okhttp()= OkHttpClient.Builder()
        //.cookieJar(CookieJar.NO_COOKIES)
        .build()
}
