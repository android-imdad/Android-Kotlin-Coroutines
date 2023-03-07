package lk.spacewa.coroutines.data.remote.repository

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.exception.ApolloException
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import lk.spacewa.coroutines.GetStarwarsQuery
import lk.spacewa.coroutines.data.local.db.StarwarsDBRepo
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Created by Imdad on 7/11/2020.
 */
class StarwarsRepositoryImpl @Inject constructor(
    private val mApolloClient: ApolloClient,
    private val mStarwarsDB: StarwarsDBRepo
) : StarwarsRepository {


    override fun getStarwarsAsync(): Deferred<Response<GetStarwarsQuery.Data>>? {
        val getStarwarsQuery = GetStarwarsQuery.builder().build()
        return mApolloClient.query(getStarwarsQuery).toDeferred()
    }

    override suspend fun getStarwarsAndSave() {
        val starwarsApolloData: Response<GetStarwarsQuery.Data> = getStarwarsAsync()!!.await()
        mStarwarsDB.insertStarwars(starwarsApolloData.data!!.allFilms()?.films()!!)
    }

    override suspend fun getStarwarsSync(): Response<GetStarwarsQuery.Data?> {
        return mApolloClient.query(
            GetStarwarsQuery.builder().build()
        ).execute<GetStarwarsQuery.Data?>()
    }


    /**
     * This is a thread blocking function but since it is called from the Worker and it
     * does not really matter if it is blocked since it only has one purpose and also because Apollo will throw a
     * 503 error if it is not a blocking call.
     */
    override fun getStarwarsAndSaveBlocking() {
        runBlocking {
            val starwarsApolloData: Response<GetStarwarsQuery.Data?> = getStarwarsSync()
            mStarwarsDB.insertStarwars(starwarsApolloData.data!!.allFilms()?.films()!!)
        }
    }

    /**
     * Helper function to execute Apollo calls Synchronously
     */
    private suspend fun <T> ApolloCall<T>.execute() = suspendCoroutine<Response<T>> { cont ->
        enqueue(object : ApolloCall.Callback<T>() {
            override fun onResponse(response: Response<T>) {
                cont.resume(response)
            }

            override fun onFailure(e: ApolloException) {
                cont.resumeWithException(e)
            }
        })
    }
}