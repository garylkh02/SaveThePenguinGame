package flappy.penguin.game.model

import flappy.penguin.game.view.GameController.iceBergs
import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.image.Image

import scala.collection.mutable.ListBuffer

class IceBerg(x: Int, y: Int, iceImage: Image) extends GameObject(x, y) {
  override def draw(gc: GraphicsContext, blockSize: Double): Unit = {
    gc.drawImage(iceImage, x * blockSize, y * blockSize, blockSize, blockSize)
  }
}

object IceBerg {
  def generate(maxX: Double, maxY: Double, penguinBody: ListBuffer[GameObject], victim: Victim, iceImage: Image): IceBerg = {
    var x = 0
    var y = 0

    val possibleLocations = (0 until maxX.toInt).flatMap { i =>
      (0 until maxY.toInt).map { j =>
        new GameObject(i, j)
      }
    }.toSet -- penguinBody.toSet -- iceBergs.map(b => new GameObject(b.x, b.y)).toSet - victim // Exclude food position

    val randomIndex = util.Random.nextInt(possibleLocations.size)
    val randomLocation = possibleLocations.toList(randomIndex)

    new IceBerg(randomLocation.x, randomLocation.y, iceImage)
  }
}