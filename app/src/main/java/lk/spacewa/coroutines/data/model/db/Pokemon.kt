package lk.spacewa.coroutines.data.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Imdad on 7/27/2020.
 */
@Entity(tableName = "pokemons")
data class Pokemon (
        @PrimaryKey @ColumnInfo(name = "id") val pokemonId: String,
        val name: String,
        val imageUrl: String = "",
        val number: String
) {
    override fun toString() = name
}