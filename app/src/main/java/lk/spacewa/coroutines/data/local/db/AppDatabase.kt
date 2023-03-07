package lk.spacewa.coroutines.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import lk.spacewa.coroutines.data.local.db.dao.StarwarsDao
import lk.spacewa.coroutines.data.model.db.Starwars

/**
 * Created by Imdad on 7/27/2020.
 */
@Database(entities = [Starwars::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun starwarsDao(): StarwarsDao?
}