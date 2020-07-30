package lk.spacewa.coroutines.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.apollographql.apollo.api.Response
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapLatest
import lk.spacewa.coroutines.GetPokemonsQuery
import lk.spacewa.coroutines.ui.base.BaseViewModel
import lk.spacewa.coroutines.data.DataManager
import lk.spacewa.coroutines.data.model.db.Pokemon
import lk.spacewa.coroutines.utils.rx.SchedulerProvider

/**
 * Created by Imdad on 05/11/20.
 */

/**
 * @ActivityScoped annotation will allow the view model to be reused by multiple fragments pertaining to the activity
 * @ViewModelInject will inject dependencies without a need to create a custom ViewModelFactory
 */
@ActivityScoped
class HomeViewModel @ViewModelInject constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider) : BaseViewModel(dataManager, schedulerProvider){

    init {
        getPokemonInfo()
    }
    /**
     * Keeping a reference to the state of the current sort order of the list
     */
    private val isSortedAlphabetically = MutableLiveData<Boolean>(true)

    /**
     * A ConflatedBroadcastChannel will only hold the last value which was sent to it and it is thread-safe, which means
     * even if you write to it from multiple channels, the last sent request will be the one which persists
     */
    private val pokemonChannel = ConflatedBroadcastChannel<Boolean>()

    /**
     * A list of pokemons updated based on the sort order from the DB
     */
    val pokemonsUsingFlow : LiveData<List<Pokemon>> = pokemonChannel.asFlow()
            .flatMapLatest { isSortedAlphabetically ->
                if(isSortedAlphabetically){
                    dataManager.pokemonsFlow
                } else {
                    dataManager.pokemonsSortedByNumber
                }
            }.asLiveData()

    /**
     * Retrieves list of pokemons at the start of the app and saves it to the DB
     */
    fun getPokemonInfo(){
        //Launch data load is a high level kotlin function which enables you to reuse similar code
        launchDataLoad {
            saveApolloModelsToRoom()
        }
    }

    /**
     * Saves Apollo models to room DB
     */
    private suspend fun saveApolloModelsToRoom(){
        val pokemonApolloData : Response<GetPokemonsQuery.Data>? = dataManager?.getPokemonsAsync()?.await()
        pokemonApolloData?.data?.pokemons()?.let { dataManager!!.insertPokemons(it) }
    }


    /**
     * function to switch sorting of data
     */
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