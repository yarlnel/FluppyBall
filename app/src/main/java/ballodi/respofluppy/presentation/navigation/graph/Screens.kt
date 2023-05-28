package ballodi.respofluppy.presentation.navigation.graph

import com.github.terrakok.cicerone.androidx.FragmentScreen
import ballodi.respofluppy.presentation.fragments.game.GameFragment

object Screens {

    fun Game() = FragmentScreen {
        GameFragment()
    }
}
