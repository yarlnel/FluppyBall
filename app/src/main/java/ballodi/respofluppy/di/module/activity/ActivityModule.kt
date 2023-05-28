package ballodi.respofluppy.di.module.activity

import ballodi.respofluppy.presentation.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityModule {

    @ContributesAndroidInjector
    fun mainActivity() : MainActivity
}
