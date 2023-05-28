package ballodi.respofluppy.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import ballodi.respofluppy.models.FluppyBall
import ballodi.respofluppy.presentation.fragments.game.GameData
import ballodi.respofluppy.presentation.fragments.game.PipeController
import ballodi.respofluppy.presentation.utils.VoidCallback

class FluppyBallGameView @JvmOverloads constructor (
    context: Context,
    attrs: AttributeSet? = null
) : SurfaceView(context, attrs), SurfaceHolder.Callback {

    private var drawingThread: FluppyBallRenderingThread? = null

    private var _onGameFinished : VoidCallback = {}

    fun onGameFinished(onGameStop: VoidCallback) {
        _onGameFinished = onGameStop
    }

    fun getScore() = gameData.score

    fun resumeGame() {
        gameData.pipeController.reset()
        gameData.ball.revive()
        gameData.score = 0
        gameData.deltaY = 0
    }

    private val pipeController by lazy {
        PipeController(width, height, resources)
    }

    init {
        holder.addCallback(this@FluppyBallGameView)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> if (gameData.ball.isAlive) {
                gameData.deltaY = -20
            }
        }
        return true
    }

    private val gameData by lazy {
        val ball = FluppyBall().apply {
            onKill = {
                pipeController.stopPipes()
                _onGameFinished.invoke()
            }
        }

        GameData(
            ball,
            pipeController,
            height,
            width
        )
    }

    override fun surfaceCreated(p0: SurfaceHolder) {

        drawingThread = FluppyBallRenderingThread(
            holder,
            resources,
            gameData
        )
        drawingThread?.setRunning(true)
        drawingThread?.start()
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {}

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        drawingThread?.setRunning(false)
        var retry = true
        while (retry) {
            try {
                drawingThread?.join()
                retry = false
            } catch (iex: InterruptedException) {}
        }
    }
}