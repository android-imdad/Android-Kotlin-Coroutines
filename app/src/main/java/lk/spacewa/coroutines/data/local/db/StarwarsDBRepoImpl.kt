package lk.spacewa.coroutines.data.local.db


import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import lk.spacewa.coroutines.GetStarwarsQuery
import lk.spacewa.coroutines.data.model.db.Starwars
import javax.inject.Inject

/**
 * Created by Imdad on 7/27/2020.
 */
class StarwarsDBRepoImpl @Inject constructor(private val mAppDatabase: AppDatabase) : StarwarsDBRepo {

    private val defaultDispatcher : CoroutineDispatcher = Dispatchers.Default


    override val starwarsLiveData: LiveData<List<Starwars>>
        get() = mAppDatabase.starwarsDao()?.getStarwarsFilmsLiveData()!!

    override val starwarsLiveDataSortedByID: LiveData<List<Starwars>>
        get() = mAppDatabase.starwarsDao()?.getStarwarsFilmsLiveDataByID()!!

    override suspend fun insertStarwars(starwars: List<GetStarwarsQuery.Film>) {
        mAppDatabase.starwarsDao()?.insertAll(convertModels(starwars))
    }

    /**
     * Main-safe function running on Dispatchers.Default as to not block the main thread
     */
    private suspend fun convertModels(starwarsFilms : List<GetStarwarsQuery.Film>) : List<Starwars> = coroutineScope{
        withContext(defaultDispatcher) {
            val starwarsRoomList : ArrayList<Starwars> = arrayListOf()
            for (stawwarsFilm in starwarsFilms) {
                starwarsRoomList.add(Starwars(stawwarsFilm.id(), stawwarsFilm.title()!!, stawwarsFilm.director()!!))
            }
            starwarsRoomList
        }
    }



}