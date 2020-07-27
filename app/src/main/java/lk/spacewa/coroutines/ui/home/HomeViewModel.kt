package lk.spacewa.coroutines.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import com.apollographql.apollo.api.Response
import dagger.hilt.android.scopes.ActivityScoped
import lk.spacewa.coroutines.GetPokemonsQuery
import lk.spacewa.coroutines.ui.base.BaseViewModel
import lk.spacewa.coroutines.data.DataManager
import lk.spacewa.coroutines.data.model.db.Pokemon
import lk.spacewa.coroutines.utils.SingleLiveEvent
import lk.spacewa.coroutines.utils.rx.SchedulerProvider

/**
 * Created by Imdad on 05/11/20.
 */

// ActivityScoped annotation will allow the view model to be reused by multiple fragments pertaining to the activity
@ActivityScoped
class HomeViewModel @ViewModelInject constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider) : BaseViewModel(dataManager, schedulerProvider){

    var pokemonDataEvent : SingleLiveEvent<GetPokemonsQuery.Data> = SingleLiveEvent()
    val pokemonRoomList : ArrayList<Pokemon> = arrayListOf()

    init {
        getPokemonInfo()
    }

    fun getPokemonInfo(){
        launchDataLoad {
            saveApolloModelsToRoom()
        }
    }

    private suspend fun saveApolloModelsToRoom(){
        val pokemonApolloData : Response<GetPokemonsQuery.Data>? = dataManager?.getPokemonsAsync()?.await()
        for (pokemon in pokemonApolloData?.data?.pokemons()!!) {
            pokemonRoomList.add(Pokemon(pokemon.id(), pokemon.name()!!, pokemon.image()!!, pokemon.number()!!))
        }
        dataManager!!.insertPokemons(pokemonRoomList)
    }


}