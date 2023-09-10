package flappy.penguin.game.model

import flappy.penguin.game.view.GameController.iceBergs
import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.image.Image

import scala.collection.mutable.ListBuffer

class Victim(x: Int, y: Int) extends GameObject(x, y) {
  def draw(gc: GraphicsContext, blockSize: Double, victimImage: Image): Unit = {
    gc.drawImage(victimImage, x * blockSize, y * blockSize, blockSize, blockSize)
  }
}

object Victim {
  def generate(maxX: Double, maxY: Double, penguinBody: ListBuffer[GameObject], iceLocations: Set[GameObject]): Victim = {

    val possibleLocations = (0 until maxX.toInt).flatMap { i =>
      (0 until maxY.toInt).map { j =>
        new GameObject(i, j)
      }
    }.toSet -- penguinBody.toSet -- iceBergs.map(b => new GameObject(b.x, b.y)).toSet -- iceLocations

    val filteredLocations = possibleLocations.filter { loc =>
      !iceBergs.exists(iceBerg => iceBerg.x == loc.x && iceBerg.y == loc.y)
    }

    if (filteredLocations.nonEmpty) {
      val randomIndex = util.Random.nextInt(filteredLocations.size)
      val randomLocation = filteredLocations.toList(randomIndex)
      new Victim(randomLocation.x, randomLocation.y)
    } else {
      throw new IllegalStateException("Unable to find a valid location for victim.")
    }
  }
}