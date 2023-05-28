package ballodi.respofluppy.presentation.fragments.game

import ballodi.respofluppy.models.FluppyBall

data class GameData(
    val ball: FluppyBall,
    val pipeController: PipeController,
    val height: Int,
    val width: Int
) {
    var deltaY: Int = 0
    var score: Int = 0
}