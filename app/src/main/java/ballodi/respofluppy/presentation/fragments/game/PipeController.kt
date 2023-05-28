package ballodi.respofluppy.presentation.fragments.game

import android.content.res.Resources
import android.graphics.BitmapFactory
import ballodi.respofluppy.R
import ballodi.respofluppy.presentation.utils.VoidCallback
import kotlin.math.floor


class PipeController(
    private val height: Int,
    private val width: Int,
    resources: Resources
) {
    private var pipeHeight = 0f
    private var pipeWidth = 0f

    private var onReset: VoidCallback = {}
    fun afterPipeReset(onReset: VoidCallback) {
        this@PipeController.onReset = onReset
    }

    init {
        val pipe = BitmapFactory.decodeResource(resources, R.drawable.pipe)
        pipeHeight = pipe.height.toFloat()
        pipeWidth = pipe.width.toFloat()
    }

    private val gap = height * 0.6f
    private val deltaX = .014f * width

    var topY: Float = 0f
        private set

    var bottomY: Float = 0f
        private set

    var x: Float = 0f
        private set

    init {
        reset()
    }

    private var pipeIsStopped = false

    fun stopPipes() {
        pipeIsStopped = true
    }

    fun moveX() {
        if (pipeIsStopped) return
        x -= deltaX
        if (-x > height)
            reset()
    }

    fun reset() {
        x = width.toFloat()
        topY = computeTopY()
        bottomY = topY + pipeHeight + gap
        pipeIsStopped = false
        onReset.invoke()
    }

    private fun computeTopY() : Float =
        floor(Math.random() * (height / 2) + .3 * height - pipeHeight).toFloat()
}
