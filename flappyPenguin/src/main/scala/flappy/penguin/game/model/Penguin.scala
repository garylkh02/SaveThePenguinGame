package flappy.penguin.game.model

import scalafx.scene.canvas.GraphicsContext
import scalafx.scene.image.Image

import scala.collection.mutable.ListBuffer

class Penguin(var body: ListBuffer[GameObject], val penImage: Image) {
  def move(direction: Direction): Unit = {
    val newHead = direction match {
      case Direction.Up => new GameObject(body.head.x, body.head.y - 1)
      case Direction.Down => new GameObject(body.head.x, body.head.y + 1)
      case Direction.Left => new GameObject(body.head.x - 1, body.head.y)
      case Direction.Right => new GameObject(body.head.x + 1, body.head.y)
    }

    body.prepend(newHead)
    body.remove(body.size - 1)
  }

  def grow(): Unit = {
    val newTail = new GameObject(body.last.x, body.last.y)
    body.append(newTail)
  }


  def draw(gc: GraphicsContext, blockSize: Double): Unit = {
    gc.drawImage(penImage, body.head.x * blockSize, body.head.y * blockSize, blockSize, blockSize)

    if (body.tail.nonEmpty) {
      for (segment <- body.tail) {
        gc.drawImage(penImage, segment.x * blockSize, segment.y * blockSize, blockSize, blockSize)
      }
    }
  }
}