package lk.spacewa.coroutines.di.module

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
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
import lk.spacewa.coroutines.data.local.db.StarwarsDBRepo
import lk.spacewa.coroutines.data.local.db.StarwarsDBRepoImpl
import lk.spacewa.coroutines.data.local.prefs.PreferencesHelper
import lk.spacewa.coroutines.data.local.prefs.PreferencesHelperImpl
import lk.spacewa.coroutines.data.remote.repository.StarwarsRepository
import lk.spacewa.coroutines.data.remote.repository.StarwarsRepositoryImpl
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
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
//   This block will run the first time the DB is created and
//   Work manager can be run here with the following commented lines of code but since we are already running the work manager in the application class
//   this is redundant

//                        val request = OneTimeWorkRequestBuilder<RefreshStarwarsDataWork>().build()
//                        WorkManager.getInstance(context).enqueue(request)
                    }
                })
                .build()
    }

    @Provides
    @Singleton
    fun provideStarwarsDB(starwarsDBRepoImpl: StarwarsDBRepoImpl): StarwarsDBRepo {
        return starwarsDBRepoImpl
    }

    @Provides
    @Singleton
    fun provideStarwarsRepository(starwarsRepositoryImpl: StarwarsRepositoryImpl): StarwarsRepository {
        return starwarsRepositoryImpl
    }


    @Provides
    fun provideSchedulerProvider(): SchedulerProvider {
        return AppSchedulerProvider()
    }


}