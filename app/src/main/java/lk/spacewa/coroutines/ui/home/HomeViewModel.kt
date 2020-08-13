package lk.spacewa.coroutines.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
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
import timber.log.Timber

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
        launchDataLoad {
            pokemonSortMediator.addSource(pokemonsLiveData, Observer {
                if(isSortedAlphabetically.value!!){
                    it.let { pokemonSortMediator.value = it }
                }
            })

            pokemonSortMediator.addSource(pokemonsSortedLiveData, Observer {
                if(!isSortedAlphabetically.value!!){
                    it.let { pokemonSortMediator.value = it }
                }
            })
        }

    }

    /**
     * Keeping a reference to the state of the current sort order of the list
     */
    private val isSortedAlphabetically = MutableLiveData<Boolean>(true)

    /**
     * A mediator livedata helps switch between two room queries to sort the pokemon
     */
    val pokemonSortMediator = MediatorLiveData<List<Pokemon>>()

    /**
     * A list of pokemons updated based on the sort order from the DB using LiveData
     */
    var pokemonsLiveData : LiveData<List<Pokemon>> = dataManager.pokemonLiveData
    var pokemonsSortedLiveData : LiveData<List<Pokemon>> = dataManager.pokemonLiveDataSortedByNumber


    /**
     * function to switch sorting of data
     */
    fun switchData(){
        if(isSortedAlphabetically.value == true) {
            isSortedAlphabetically.value = false
            pokemonsSortedLiveData.value.let { pokemonSortMediator.value = it }
        } else {
            isSortedAlphabetically.value = true
            pokemonsLiveData.value.let { pokemonSortMediator.value = it }
        }
    }

    init {
        launchDataLoad {
            pokemonSortMediator.addSource(pokemonsLiveData, Observer {
                if(isSortedAlphabetically.value!!){
                   it.let { pokemonSortMediator.value = it }
               }
            })

            pokemonSortMediator.addSource(pokemonsSortedLiveData, Observer {
                if(!isSortedAlphabetically.value!!){
                    it.let { pokemonSortMediator.value = it }
                }
            })
        }

    }

}