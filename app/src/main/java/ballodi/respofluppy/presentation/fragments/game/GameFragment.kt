package ballodi.respofluppy.presentation.fragments.game

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ballodi.respofluppy.R
import ballodi.respofluppy.databinding.FragmentGameBinding
import ballodi.respofluppy.presentation.common.backpress.BackPressedStrategyOwner
import ballodi.respofluppy.presentation.common.fragment.BaseFragment
import ballodi.respofluppy.presentation.utils.onclick
import kotlin.coroutines.CoroutineContext

class GameFragment : BaseFragment<FragmentGameBinding>(
    FragmentGameBinding::inflate
), CoroutineScope, BackPressedStrategyOwner {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            btnResume onclick {
                dialogContainer.isGone = true
                gameView.resumeGame()
            }

            gameView.onGameFinished {
                launch(Dispatchers.Main) {
                    val scoreTitle = getString(R.string.score_template, gameView.getScore())
                    txtScore.text = scoreTitle
                    dialogContainer.isVisible = true
                }
            }
        }
    }

    private val job = Job()
    override val coroutineContext: CoroutineContext = job + Dispatchers.Main

    override fun onStop() {
        super.onStop()
        job.complete()
    }

    override fun handleBackPress() {
        ultimateOnBackPressed()
    }
}
