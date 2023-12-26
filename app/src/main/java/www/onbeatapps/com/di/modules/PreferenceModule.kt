package www.onbeatapps.com.di.modules

import www.onbeatapps.com.utils.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import www.onbeatapps.com.di.PreferenceInfo

/**
 * Created by PKB on 03-05-2021.
 * pkb@gmail.com
 */

@Module
@InstallIn(SingletonComponent::class)
object PreferenceModule {

    @Provides
    @PreferenceInfo
    fun providePreferenceName() = AppConstants.PREF_NAME
}