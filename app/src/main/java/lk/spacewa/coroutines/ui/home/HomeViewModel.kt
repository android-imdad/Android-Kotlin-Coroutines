package lk.spacewa.coroutines.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import dagger.hilt.android.scopes.ActivityScoped
import lk.spacewa.coroutines.ui.base.BaseViewModel
import lk.spacewa.coroutines.data.DataManager
import lk.spacewa.coroutines.data.model.db.Starwars
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
        launchDataLoad {
            starwarsSortMediator.addSource(starwarsLiveData, Observer {
                if(isSortedAlphabetically.value!!){
                    it.let { starwarsSortMediator.value = it }
                }
            })

            starwarsSortMediator.addSource(starwarsSortedLiveData, Observer {
                if(!isSortedAlphabetically.value!!){
                    it.let { starwarsSortMediator.value = it }
                }
            })
        }

    }

    /**
     * Keeping a reference to the state of the current sort order of the list
     */
    private val isSortedAlphabetically = MutableLiveData<Boolean>(true)

    /**
     * A mediator livedata helps switch between two room queries to sort the starwars films
     */
    val starwarsSortMediator = MediatorLiveData<List<Starwars>>()

    /**
     * A list of starwars movies updated based on the sort order from the DB using LiveData
     */
    var starwarsLiveData : LiveData<List<Starwars>> = dataManager.starwarsLiveData
    var starwarsSortedLiveData : LiveData<List<Starwars>> = dataManager.starwarsLiveDataSortedByID


    /**
     * function to switch sorting of data
     */
    fun switchData(){
        if(isSortedAlphabetically.value == true) {
            isSortedAlphabetically.value = false
            starwarsSortedLiveData.value.let { starwarsSortMediator.value = it }
        } else {
            isSortedAlphabetically.value = true
            starwarsLiveData.value.let { starwarsSortMediator.value = it }
        }
    }

    init {
        launchDataLoad {
            starwarsSortMediator.addSource(starwarsLiveData, Observer {
                if(isSortedAlphabetically.value!!){
                   it.let { starwarsSortMediator.value = it }
               }
            })

            starwarsSortMediator.addSource(starwarsSortedLiveData, Observer {
                if(!isSortedAlphabetically.value!!){
                    it.let { starwarsSortMediator.value = it }
                }
            })
        }

    }

}