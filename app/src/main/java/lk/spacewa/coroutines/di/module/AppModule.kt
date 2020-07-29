package lk.spacewa.coroutines.di.module

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import lk.spacewa.coroutines.data.AppDataManager
import lk.spacewa.coroutines.data.DataManager
import lk.spacewa.coroutines.data.local.db.AppDatabase
import lk.spacewa.coroutines.data.local.db.PokemonDBRepo
import lk.spacewa.coroutines.data.local.db.PokemonDBRepoImpl
import lk.spacewa.coroutines.data.local.prefs.PreferencesHelper
import lk.spacewa.coroutines.data.local.prefs.PreferencesHelperImpl
import lk.spacewa.coroutines.data.remote.repository.PokemonRepository
import lk.spacewa.coroutines.data.remote.repository.PokemonRepositoryImpl
import lk.spacewa.coroutines.di.DatabaseInfo
import lk.spacewa.coroutines.di.PreferenceInfo
import lk.spacewa.coroutines.utils.AppConstants
import lk.spacewa.coroutines.utils.rx.AppSchedulerProvider
import lk.spacewa.coroutines.utils.rx.SchedulerProvider
import javax.inject.Singleton

/**
 * Created by Imdad on 7/11/2020.
 */

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {


    @Provides
    @Singleton
    fun provideDataManager(appDataManager: AppDataManager): DataManager {
        return appDataManager
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
    }

    @Provides
    @DatabaseInfo
    fun provideDatabaseName(): String? {
        return AppConstants.DB_NAME
    }

    @Provides
    @PreferenceInfo
    fun providePreferenceName(): String? {
        return AppConstants.PREF_NAME
    }

    @Provides
    @Singleton
    fun providePreferencesHelper(preferencesHelperImpl: PreferencesHelperImpl): PreferencesHelper {
        return preferencesHelperImpl
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@DatabaseInfo dbName: String?,@ApplicationContext context: Context?): AppDatabase {
        return Room.databaseBuilder(context!!, AppDatabase::class.java, dbName!!).fallbackToDestructiveMigration()
                .build()
    }

    @Provides
    @Singleton
    fun providePokemonDB(pokemonDBRepoImpl: PokemonDBRepoImpl): PokemonDBRepo {
        return pokemonDBRepoImpl
    }

    @Provides
    @Singleton
    fun providePokemonRepository(pokemonRepositoryImpl: PokemonRepositoryImpl): PokemonRepository {
        return pokemonRepositoryImpl
    }


    @Provides
    fun provideSchedulerProvider(): SchedulerProvider {
        return AppSchedulerProvider()
    }


}