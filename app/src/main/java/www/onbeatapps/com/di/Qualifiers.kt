package www.onbeatapps.com.di

import javax.inject.Qualifier

/**
 * Created by PKB on 03-05-2021.
 * pkb@gmail.com
 */

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class PreferenceInfo

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DatabaseInfo