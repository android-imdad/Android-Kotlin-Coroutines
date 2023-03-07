package lk.spacewa.coroutines.data.local.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import lk.spacewa.coroutines.data.model.db.Starwars

/**
 * Created by Imdad on 7/27/2020.
 */

@Dao
interface StarwarsDao {

    @Query("SELECT * from starwars ORDER BY title")
    fun getStarwarsFilmsLiveData(): LiveData<List<Starwars>>

    @Query("SELECT * from starwars ORDER BY id")
    fun getStarwarsFilmsLiveDataByID(): LiveData<List<Starwars>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(starwars: List<Starwars>)

}
