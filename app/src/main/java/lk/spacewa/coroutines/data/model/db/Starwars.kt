package lk.spacewa.coroutines.data.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Imdad on 7/27/2020.
 */
@Entity(tableName = "starwars")
data class Starwars (
        @PrimaryKey @ColumnInfo(name = "id") val id: String,
        val title: String,
        val director: String = "",
) {
    override fun toString() = title
}