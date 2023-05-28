package ballodi.respofluppy

import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import ballodi.respofluppy.di.DaggerAppComponent


class App : DaggerApplication()  {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this@App)

        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }

        Firebase.remoteConfig.setConfigSettingsAsync(configSettings)
    }

    override fun applicationInjector(): AndroidInjector<out  DaggerApplication> =
        DaggerAppComponent.builder()
            .application(this)
            .build()

}
