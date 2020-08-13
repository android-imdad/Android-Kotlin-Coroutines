package lk.spacewa.coroutines.data.remote.repository

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.toDeferred
import com.apollographql.apollo.exception.ApolloException
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import lk.spacewa.coroutines.GetPokemonsQuery
import lk.spacewa.coroutines.data.local.db.PokemonDBRepo
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Created by Imdad on 7/11/2020.
 */
class PokemonRepositoryImpl @Inject constructor(private val mApolloClient: ApolloClient,private val mPokemonDB: PokemonDBRepo) : PokemonRepository {


    override fun getPokemonsAsync(): Deferred<Response<GetPokemonsQuery.Data>>? {
        val getPokemonsQuery = GetPokemonsQuery.builder().build()
            return mApolloClient.query(getPokemonsQuery).toDeferred()
    }

    override suspend fun getPokemonsAndSave() {
         val pokemonApolloData : Response<GetPokemonsQuery.Data>? = getPokemonsAsync()!!.await()
            mPokemonDB.insertPokemons(pokemonApolloData!!.data!!.pokemons()!!)
    }

    override suspend fun getPokemonsSync(): Response<GetPokemonsQuery.Data?> {
        return mApolloClient.query(
                GetPokemonsQuery.builder().build()
        ).execute<GetPokemonsQuery.Data?>()
    }


    /**
     * This is a thread blocking function but since it is called from the Worker and it
     * does not really matter if it is blocked since it only has one purpose and also because Apollo will throw a
     * 503 error if it is not a blocking call.
     */
    override fun getPokemonsAndSaveBlocking() {
       runBlocking {
           val pokemonApolloData : Response<GetPokemonsQuery.Data?> = getPokemonsSync()
           mPokemonDB.insertPokemons(pokemonApolloData.data!!.pokemons()!!)
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