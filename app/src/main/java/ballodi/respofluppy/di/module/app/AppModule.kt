package ballodi.respofluppy.di.module.app

import ballodi.respofluppy.di.module.activity.ActivityModule
import ballodi.respofluppy.di.module.fragment.FragmentModule
import ballodi.respofluppy.di.module.navigation.NavigationModule
import dagger.Module

@Module(includes = [
    ActivityModule::class,
    FragmentModule::class,
    NavigationModule::class
])
interface AppModule
