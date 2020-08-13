package lk.spacewa.coroutines.data

import android.content.Context
import androidx.lifecycle.LiveData
import com.apollographql.apollo.api.Response
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import lk.spacewa.coroutines.GetPokemonsQuery
import lk.spacewa.coroutines.data.DataManager.LoggedInMode
import lk.spacewa.coroutines.data.local.db.PokemonDBRepo
import lk.spacewa.coroutines.data.local.prefs.PreferencesHelper
import lk.spacewa.coroutines.data.model.db.Pokemon
import lk.spacewa.coroutines.data.remote.repository.PokemonRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Imdad on 05/11/20.
 */
@Singleton
class AppDataManager @Inject constructor(@ApplicationContext private val mContext: Context, private val mPreferencesHelper: PreferencesHelper,private val mPokemonRepository: PokemonRepository, private val mGson: Gson,private val mAppPokemonDBRepo: PokemonDBRepo) : DataManager {


    override var currentAccessToken: String?
        get() = mPreferencesHelper.currentAccessToken
        set(accessToken) {
            mPreferencesHelper.currentAccessToken = accessToken
        }

    override var currentUserEmail: String?
        get() = mPreferencesHelper.currentUserEmail
        set(email) {
            mPreferencesHelper.currentUserEmail = email
        }

    override var currentUserId: Long?
        get() = mPreferencesHelper.currentUserId
        set(userId) {
            mPreferencesHelper.currentUserId = userId
        }

    override fun getCurrentUserLoggedInMode(): Int {
        return mPreferencesHelper.getCurrentUserLoggedInMode()
    }

    override fun setCurrentUserLoggedInMode(mode: LoggedInMode) {
        mPreferencesHelper.setCurrentUserLoggedInMode(mode)
    }

    override var currentUserName: String?
        get() = mPreferencesHelper.currentUserName
        set(userName) {
            mPreferencesHelper.currentUserName = userName
        }

    override var currentUserProfilePicUrl: String?
        get() = mPreferencesHelper.currentUserProfilePicUrl
        set(profilePicUrl) {
            mPreferencesHelper.currentUserProfilePicUrl = profilePicUrl
        }

    override var selectedLanguage: String?
        get() = mPreferencesHelper.selectedLanguage
        set(selectedLanguage) {
            mPreferencesHelper.selectedLanguage = selectedLanguage
        }

    override fun getPokemonsAsync(): Deferred<Response<GetPokemonsQuery.Data>> {
       return mPokemonRepository.getPokemonsAsync()!!
    }

    override suspend fun getPokemonsAndSave() {
        return mPokemonRepository.getPokemonsAndSave()
    }

    override suspend fun getPokemonsSync(): Response<GetPokemonsQuery.Data?> {
        return mPokemonRepository.getPokemonsSync()
    }

    override fun getPokemonsAndSaveBlocking() {
        return mPokemonRepository.getPokemonsAndSaveBlocking()
    }

    override val pokemonLiveData: LiveData<List<Pokemon>>
        get() = mAppPokemonDBRepo.pokemonLiveData

    override val pokemonLiveDataSortedByNumber: LiveData<List<Pokemon>>
        get() = mAppPokemonDBRepo.pokemonLiveDataSortedByNumber


    override suspend fun insertPokemons(pokemons: List<GetPokemonsQuery.Pokemon>) {
        mAppPokemonDBRepo.insertPokemons(pokemons)
    }


    override fun setUserAsLoggedOut() {
        updateUserInfo(
                null,
                null,
                LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT,
                null,
                null,
                null)
    }

    override fun updateUserInfo(
            accessToken: String?,
            userId: Long?,
            loggedInMode: LoggedInMode,
            userName: String?,
            email: String?,
            profilePicPath: String?) {
        currentAccessToken = accessToken
        currentUserId = userId
        setCurrentUserLoggedInMode(loggedInMode)
        currentUserName = userName
        currentUserEmail = email
        currentUserProfilePicUrl = profilePicPath
    }


    companion object {
        private const val TAG = "AppDataManager"
    }

}