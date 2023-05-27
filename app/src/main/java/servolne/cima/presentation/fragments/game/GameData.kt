package servolne.cima.presentation.fragments.game

import servolne.cima.models.FluppyBall

data class GameData(
    val ball: FluppyBall,
    val pipeController: PipeController,
    val height: Int,
    val width: Int
) {
    var deltaY: Int = 0
    var score: Int = 0
}