package www.onbeatapps.com.datastore

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey

import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UserDataStoreRepository @Inject constructor(
    @ApplicationContext context: Context
) {

    private val applicationContext = context.applicationContext
    private val dataStore: DataStore<Preferences>

    init {
        dataStore = applicationContext.createDataStore(
            "my_data_store"
        )
    }

    suspend fun reloadNotification(value: Boolean) {
        dataStore.edit {
            it[KEY_UPDATE_NOT] = value
        }
    }

    val updateNotification: Flow<Boolean?> = dataStore.data
        .map { preferences ->
            preferences[KEY_UPDATE_NOT]
        }

    companion object {
        private val KEY_UPDATE_NOT = preferencesKey<Boolean>("update_not")
    }
}