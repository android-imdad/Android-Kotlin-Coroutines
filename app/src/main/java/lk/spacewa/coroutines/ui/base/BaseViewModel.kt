package lk.spacewa.coroutines.ui.base

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import lk.spacewa.coroutines.data.DataManager
import lk.spacewa.coroutines.utils.rx.SchedulerProvider
import timber.log.Timber

/**
 * Created by Imdad on 05/11/20.
 */
abstract class BaseViewModel(val dataManager: DataManager?,
                                val schedulerProvider: SchedulerProvider?) : ViewModel() {

    val spinner: LiveData<Boolean>
        get() = _spinner

    private val _spinner = MutableLiveData<Boolean>(false)

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()


    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    fun setIsLoading(isLoading : Boolean) {
        _spinner.value = isLoading
    }


    fun launchDataLoad(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                _spinner.value = true
                block() //The parameter
            } catch (error: Throwable) {
                Timber.e(error.message)
            } finally {
                _spinner.value = false
            }
        }
    }

    fun <T> loadDataFor(source: ConflatedBroadcastChannel<T>, block: suspend (T) -> Unit) {
        source.asFlow().mapLatest{
            block(it)
        }.onCompletion {
            _spinner.value = false;
        }.catch { throwable -> Timber.e(throwable.message)
        }.launchIn(viewModelScope)
    }

}