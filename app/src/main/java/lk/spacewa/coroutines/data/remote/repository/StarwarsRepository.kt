package lk.spacewa.coroutines.data.remote.repository

import com.apollographql.apollo.api.Response
import kotlinx.coroutines.Deferred
import lk.spacewa.coroutines.GetStarwarsQuery

/**
 * Created by Imdad on 7/11/2020.
 */
interface StarwarsRepository {

    fun getStarwarsAsync () : Deferred<Response<GetStarwarsQuery.Data>>?

    suspend fun getStarwarsAndSave()

    suspend fun getStarwarsSync () : Response<GetStarwarsQuery.Data?>

    fun getStarwarsAndSaveBlocking()
}