package ballodi.respofluppy.models

import ballodi.respofluppy.presentation.utils.VoidCallback

class FluppyBall {
    var x = 50
    var y = 500
    var isAlive = true
        private set

    var rotation = 0.0
    var millis = 0

    fun update(dy: Int) {
        y += dy
    }

    fun setStatus(status: Boolean) {
        isAlive = status
    }

    var onKill: VoidCallback = {}
    fun kill() {
        isAlive = false
        onKill.invoke()
    }

    fun revive() {
        x = 50
        y = 500
        isAlive = true
    }
}
