package win.rainchan.uselessbot.dao

import org.dizitart.no2.IndexType
import org.dizitart.no2.objects.Id
import org.dizitart.no2.objects.Index
import org.dizitart.no2.objects.Indices
import java.io.Serializable


@Indices(Index("userId", type = IndexType.Unique))
data class StockUser(
    @Id
    var userId: Long = 0,
    val stocks: MutableList<String> = arrayListOf()
) : Serializable {
}
