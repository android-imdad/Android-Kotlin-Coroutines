package lk.spacewa.coroutines.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import lk.spacewa.coroutines.data.model.db.Pokemon

/**
 * Created by Imdad on 7/27/2020.
 */

@Dao
interface PokemonDao {
    @Query("SELECT * from pokemons ORDER BY name")
    fun getPokemons(): Flow<List<Pokemon>>

    @Query("SELECT * from pokemons ORDER BY number")
    fun getPokemonsByNumber(): Flow<List<Pokemon>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokemons: List<Pokemon>)

}
