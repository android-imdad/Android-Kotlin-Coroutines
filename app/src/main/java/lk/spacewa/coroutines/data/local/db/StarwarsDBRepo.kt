package lk.spacewa.coroutines.data.local.db

import androidx.lifecycle.LiveData
import lk.spacewa.coroutines.GetStarwarsQuery
import lk.spacewa.coroutines.data.model.db.Starwars

/**
 * Created by Imdad on 7/27/2020.
 */
interface StarwarsDBRepo {


    val starwarsLiveData : LiveData<List<Starwars>>
    val starwarsLiveDataSortedByID : LiveData<List<Starwars>>

    suspend fun insertStarwars(starwars : List<GetStarwarsQuery.Film>)
}