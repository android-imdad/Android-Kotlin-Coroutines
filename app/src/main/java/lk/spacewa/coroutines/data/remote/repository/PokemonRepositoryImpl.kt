package lk.spacewa.coroutines.data.remote.repository

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.toDeferred
import kotlinx.coroutines.Deferred
import lk.spacewa.coroutines.GetPokemonsQuery
import lk.spacewa.coroutines.utils.rx.SchedulerProvider
import javax.inject.Inject

/**
 * Created by Imdad on 7/11/2020.
 */
class PokemonRepositoryImpl @Inject constructor(private val mApolloClient: ApolloClient,private val mRxSchedulers: SchedulerProvider) : PokemonRepository {


    override fun getPokemonsAsync(): Deferred<Response<GetPokemonsQuery.Data>> {
        val getPokemonsQuery = GetPokemonsQuery.builder().build()
        return mApolloClient.query(getPokemonsQuery).toDeferred()
    }

}