package ballodi.respofluppy.di.module.fragment

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ballodi.respofluppy.presentation.fragments.game.GameFragment

@Module
interface FragmentModule {

    @ContributesAndroidInjector
    fun gameFragment(): GameFragment
}
