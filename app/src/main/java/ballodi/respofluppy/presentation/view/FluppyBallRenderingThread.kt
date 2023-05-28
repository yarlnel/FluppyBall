package ballodi.respofluppy.presentation.view


import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.RectF
import android.view.SurfaceHolder
import androidx.core.graphics.scale
import ballodi.respofluppy.R
import ballodi.respofluppy.models.FluppyBall
import ballodi.respofluppy.presentation.fragments.game.GameData
import ballodi.respofluppy.presentation.fragments.game.PipeController


class FluppyBallRenderingThread(
    private val holder: SurfaceHolder,
    private val resources: Resources,
    private val gameData: GameData
) : Thread() {

    private val ball: FluppyBall = gameData.ball
    private val pipeController: PipeController = gameData.pipeController
    private val height: Int = gameData.height
    private val width: Int = gameData.width


    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        style = Paint.Style.FILL
        textSize = 200f
    }

    private val bitmapPaint = Paint(Paint.DITHER_FLAG)

    private val ballBitmap by lazy {
        BitmapFactory.decodeResource(
            resources,
            R.drawable.ball
        )
    }
    private val bottomPipeBitmap by lazy {
        BitmapFactory.decodeResource(
            resources,
            R.drawable.pipe
        )
    }

    private val reverseTransformation = Matrix().apply {
        preScale(1.0f, -1.0f)
    }

    private val topPipeBitmap by lazy {
        Bitmap.createBitmap(
            bottomPipeBitmap,
            0,
            0,
            bottomPipeBitmap.width,
            bottomPipeBitmap.height,
            reverseTransformation,
            false
        )
    }

    private val grassBitmap by lazy {
        BitmapFactory.decodeResource(
            resources,
            R.drawable.grass
        ).scale(width, height)
    }

    private var lastRenderTime = 0L
    private var startedTime = 0L
    private var isRunning = false

    fun setRunning(isRunning: Boolean) {
        this@FluppyBallRenderingThread.isRunning = isRunning
    }

    init {
        pipeController.afterPipeReset {
            if (ball.isAlive) {
                gameData.score++
            }
        }
    }

    override fun run() {
        startedTime = time()
        while (isRunning) {
            val elapsedTime = time() - lastRenderTime
            if (elapsedTime < Constants.RedrawTime) continue

            val millisFromStart = time() - startedTime
            if (millisFromStart >= 130 && millisFromStart % 2 == 0L) {
                gameData.deltaY += 3
            }

            ball.update(gameData.deltaY)
            pipeController.moveX()

            val canvas = holder.lockCanvas()
            canvas.render()
            holder.unlockCanvasAndPost(canvas)

            val birdRect = RectF(
                ball.x.toFloat(),
                ball.y.toFloat(),
                ball.x.toFloat() + ballBitmap.width,
                ball.y.toFloat() + ballBitmap.height
            )
            val topPipeRect = RectF(
                pipeController.x,
                pipeController.topY,
                pipeController.x + topPipeBitmap.width,
                pipeController.topY + topPipeBitmap.height
            )
            val bottomPipeRect = RectF(
                pipeController.x,
                pipeController.bottomY,
                pipeController.x + bottomPipeBitmap.width,
                height.toFloat()
            )

            val isStrikeTopPipe = birdRect.intersect(topPipeRect)
            val isStrikeBottomPipe = birdRect.intersect(bottomPipeRect)
            val isFall = ball.y > height

            if (isStrikeBottomPipe || isStrikeTopPipe || isFall) {
                ball.kill()
            }

            lastRenderTime = time()
        }
    }


    private fun Canvas.render() {
        drawBitmap(grassBitmap, 0f,0f, bitmapPaint)

        drawBitmap(
            topPipeBitmap,
            pipeController.x,
            pipeController.topY,
            bitmapPaint
        )

        drawBitmap(
            bottomPipeBitmap,
            pipeController.x,
            pipeController.bottomY,
            bitmapPaint
        )

        drawBitmap(
            ballBitmap,
            ball.x.toFloat(),
            ball.y.toFloat(),
            bitmapPaint
        )

        drawText(gameData.score.toString(), 20f, 220f, textPaint)
    }

    private fun time() = System.nanoTime() / 1_000_000

    private object Constants {
        const val RedrawTime = 16 // 16 ms for 60 frame rate
    }
}