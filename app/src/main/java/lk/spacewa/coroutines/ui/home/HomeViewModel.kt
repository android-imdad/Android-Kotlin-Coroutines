package lk.spacewa.coroutines.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.Response
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapLatest
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


    private val isSortedAlphabetically = MutableLiveData<Boolean>(true)
    private val pokemonChannel = ConflatedBroadcastChannel<Boolean>()


    val pokemonsUsingFlow : LiveData<List<Pokemon>> = pokemonChannel.asFlow()
            .flatMapLatest { isSortedAlphabetically ->
                if(isSortedAlphabetically){
                    dataManager.pokemonsFlow
                } else {
                    dataManager.pokemonsSortedByNumber
                }
            }.asLiveData()

    fun getPokemonInfo(){
        launchDataLoad {
            saveApolloModelsToRoom()
        }
    }

    private suspend fun saveApolloModelsToRoom(){
        val pokemonApolloData : Response<GetPokemonsQuery.Data>? = dataManager?.getPokemonsAsync()?.await()
        pokemonApolloData?.data?.pokemons()?.let { dataManager!!.insertPokemons(it) }
    }

    fun switchData(){
        if(isSortedAlphabetically.value == true) {
            isSortedAlphabetically.value = false
            pokemonChannel.offer(isSortedAlphabetically.value!!)
        } else {
            isSortedAlphabetically.value = true
            pokemonChannel.offer(isSortedAlphabetically.value!!)
        }
    }

    init {
        getPokemonInfo()
        pokemonChannel.offer(isSortedAlphabetically.value!!)
    }

}