package servolne.cima.di.module.fragment

import dagger.Module
import dagger.android.ContributesAndroidInjector
import servolne.cima.presentation.fragments.game.GameFragment

@Module
interface FragmentModule {

    @ContributesAndroidInjector
    fun gameFragment(): GameFragment
}
