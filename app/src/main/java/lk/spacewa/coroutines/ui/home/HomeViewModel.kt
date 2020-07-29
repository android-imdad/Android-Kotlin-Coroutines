package lk.spacewa.coroutines.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.Response
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
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

    init {
        getPokemonInfo()
    }

    val pokemonRoomList : ArrayList<Pokemon> = arrayListOf()

    val pokemonsUsingFlow : LiveData<List<Pokemon>> = dataManager.pokemonsFlow.asLiveData()

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