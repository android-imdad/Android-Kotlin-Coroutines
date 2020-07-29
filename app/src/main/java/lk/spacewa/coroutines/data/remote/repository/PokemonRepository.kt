package lk.spacewa.coroutines.data.remote.repository

import com.apollographql.apollo.api.Response
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.Deferred
import lk.spacewa.coroutines.GetPokemonsQuery
import lk.spacewa.coroutines.data.model.db.Pokemon

/**
 * Created by Imdad on 7/11/2020.
 */
interface PokemonRepository {

    fun getPokemonsAsync () : Deferred<Response<GetPokemonsQuery.Data>>

}