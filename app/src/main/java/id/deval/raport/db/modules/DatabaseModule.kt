package id.deval.raport.db.modules

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.deval.raport.db.Database
import id.deval.raport.db.Session

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    fun provideDatabase(
        @ApplicationContext
        appContext : Context
    ): Database {
        return Room.databaseBuilder(appContext, Database::class.java, "raport-app.db").fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideSharedPreferences(
        @ApplicationContext
        appContext: Context
    ) : Session {
        return Session(appContext)
    }
}