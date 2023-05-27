package servolne.cima.presentation.fragments.game

import android.os.Bundle
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import servolne.cima.R
import servolne.cima.databinding.FragmentGameBinding
import servolne.cima.presentation.common.backpress.BackPressedStrategyOwner
import servolne.cima.presentation.common.fragment.BaseFragment
import servolne.cima.presentation.utils.onclick
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
