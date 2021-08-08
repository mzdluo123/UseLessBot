package win.rainchan.uselessbot.dao

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.util.ResourceUtils



class ImgDto : ArrayList<ImgDto.ImgDtoItem>() {
    data class ImgDtoItem(
        val bookmarkCount: Int = 0,
        val id: Int = 0,
        val pageCount: Int = 0,
        val tags: List<String> = listOf(),
        val title: String = "",
        val uploadDate: String = ""
    )


}
