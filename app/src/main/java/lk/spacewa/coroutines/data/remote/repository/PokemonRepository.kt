package lk.spacewa.coroutines.data.remote.repository

import com.apollographql.apollo.api.Response
import kotlinx.coroutines.Deferred
import lk.spacewa.coroutines.GetPokemonsQuery

/**
 * Created by Imdad on 7/11/2020.
 */
interface PokemonRepository {

    fun getPokemonsAsync () : Deferred<Response<GetPokemonsQuery.Data>>?

    suspend fun getPokemonsAndSave()

    suspend fun getPokemonsSync () : Response<GetPokemonsQuery.Data?>

    fun getPokemonsAndSaveBlocking()
}