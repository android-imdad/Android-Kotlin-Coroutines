package lk.spacewa.coroutines.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import lk.spacewa.coroutines.data.local.db.dao.PokemonDao
import lk.spacewa.coroutines.data.model.db.Pokemon

/**
 * Created by Imdad on 7/27/2020.
 */
@Database(entities = [Pokemon::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao?
}