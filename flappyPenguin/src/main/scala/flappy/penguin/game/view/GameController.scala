package flappy.penguin.game.view

import flappy.penguin.game.MainApp
import flappy.penguin.game.MainApp.stage
import scalafx.Includes._
import scalafx.animation.AnimationTimer
import scalafx.scene.canvas.Canvas
import scalafx.scene.input.{KeyCode, KeyEvent}
import scalafx.scene.paint.Color
import flappy.penguin.game.model.{IceBerg, Difficulty, Direction, Victim, GameObject, Penguin}
import scalafx.application.JFXApp
import scalafx.scene.control.Button
import scalafx.scene.Scene
import scalafx.scene.image.Image
import scalafx.scene.text.{Font, FontWeight, Text}

import scala.collection.mutable.ListBuffer

object GameController {

  lazy val penImage: Image = new Image(getClass.getResourceAsStream("/images/penguin.png"))
  lazy val victimImage: Image = new Image(getClass.getResourceAsStream("/images/fish.png"))
  lazy val iceImage: Image = new Image(getClass.getResourceAsStream("/images/ice.png"))
  lazy val backgroundImage: Image = new Image(getClass.getResourceAsStream("/images/bg.jpg"))
  lazy val gameOverImage: Image = new Image(getClass.getResourceAsStream("/images/gameover.png"))

  val windowWidth: Double = 1000
  val windowHeight: Double = 800
  var iceBergCount: Int = 0
  var iceBergs: ListBuffer[IceBerg] = ListBuffer.empty
  var score: Int = 0

  val canvas: Canvas = new Canvas(windowWidth, windowHeight)
  val gc = canvas.graphicsContext2D

  var penguin: Penguin = _
  var victim: Victim = _

  val blockSize: Double = 50
  var direction: Direction = Direction.Right

  val easyParameters: Double = 150000000
  val mediumParameters: Double = 110000000
  val hardParameters: Double = 90000000

  var currentDifficulty: Difficulty.Diffi = Difficulty.Easy
  var updateDelay = easyParameters
  var lastUpdateTime: Long = 0

  private var gameLoop: AnimationTimer = _

  var gameOver: Boolean = false

  def handleKey(keyCode: KeyCode): Unit = {
    keyCode.getName match {
      case "Up" => if (direction != Direction.Down) direction = Direction.Up
      case "Down" => if (direction != Direction.Up) direction = Direction.Down
      case "Left" => if (direction != Direction.Right) direction = Direction.Left
      case "Right" => if (direction != Direction.Left) direction = Direction.Right
      case "Escape" => gameLoop.stop()
      case _ =>
    }
  }

  def startGame(): Unit = {
    stage = new JFXApp.PrimaryStage {
      title = "Flappy Penguin"
      scene = new Scene(windowWidth, windowHeight) {
        fill = Color.LightSkyBlue
        content = List(canvas, endGame())

        canvas.onKeyPressed = (event: KeyEvent) => handleKey(event.getCode)
        canvas.requestFocus()
        gameLoop = AnimationTimer { _ => update() }
        gameLoop.start()

        victim = Victim.generate(windowWidth / blockSize, windowHeight / blockSize, penguin.body, iceBergs.map(b => new GameObject(b.x, b.y)).toSet)
      }
    }
  }


  private def endGame(): Button = {
    val endGameButton = new Button("Quit Game")
    endGameButton.layoutX = windowWidth - 100
    endGameButton.layoutY = windowHeight - 40
    endGameButton.onAction = () => {
      MainApp.stage.close()
    }
    endGameButton
  }

  def update(): Unit = {
    if (System.nanoTime - lastUpdateTime > updateDelay) {
      lastUpdateTime = System.nanoTime

      if (!gameOver) {
        penguin.move(direction)
        checkCollision()
      }

      render()
    }
  }

  def checkCollision(): Unit = {
    val head = penguin.body.head

    if (head.x < 0 || head.x >= windowWidth / blockSize ||
      head.y < 0 || head.y >= windowHeight / blockSize ||
      penguin.body.tail.exists(segment => segment.x == head.x && segment.y == head.y)) {
      gameOver = true
    }

    if (head.x == victim.x && head.y == victim.y) {
      penguin.grow()
      victim = Victim.generate(windowWidth / blockSize, windowHeight / blockSize, penguin.body, iceBergs.map(b => new GameObject(b.x, b.y)).toSet)
      score += 1
      addIceBerg(victim)

    }

    if (iceBergs.exists(iceBerg => head.x == iceBerg.x && head.y == iceBerg.y)) {
      gameOver = true
    }
  }

  def render(): Unit = {
    gc.drawImage(backgroundImage, 0, 0, windowWidth, windowHeight)

    if (!gameOver) {
      penguin.draw(gc, blockSize)
      victim.draw(gc, blockSize, victimImage)
      iceBergs.foreach(_.draw(gc, blockSize))
      val fontSize: Double = 18
      val font: Font = Font.font("Bradley Hand", FontWeight.Bold, fontSize)
      gc.setFont(font)

      updateScore()
      gc.fillText(s"Icebergs: $iceBergCount", 10, 20)
    } else {
      val fontSize: Double = 35
      val font: Font = Font.font("BM YEONSUNG OTF", FontWeight.Normal, fontSize)
      val imageWidth: Double = gameOverImage.width.get()
      val imageHeight: Double = gameOverImage.height.get()
      val imageX: Double = (windowWidth - imageWidth) / 2
      val imageY: Double = (windowHeight - imageHeight) / 2

      gc.drawImage(gameOverImage, imageX, imageY)

      val gameOverText = "Game Over ~"
      val congratsText = s"Hooray!"
      val noEatenText = s"You have saved $score penguins!"
      val gameOverTextWidth: Double = new Text(gameOverText).getLayoutBounds.getWidth
      val congratsTextWidth: Double = new Text(congratsText).getLayoutBounds.getWidth
      val noEatenTextWidth: Double = new Text(noEatenText).getLayoutBounds.getWidth

      val gameOverX: Double = (windowWidth - gameOverTextWidth) / 2 + 70
      val congratsX: Double = (windowWidth - congratsTextWidth) / 2 + 83
      val noEatenX: Double = (windowWidth - noEatenTextWidth) / 2 + 10

      val totalTextHeight: Double = fontSize * 3

      val centerY: Double = (windowHeight - totalTextHeight) / 2 - 50

      gc.setFont(font)
      gc.fillText(gameOverText, gameOverX, centerY)
      gc.fillText(congratsText, congratsX, centerY + fontSize + fontSize)
      gc.fillText(noEatenText, noEatenX, centerY + fontSize + fontSize + fontSize + fontSize)

    }

  }

  def updateScore(): Unit = {
    gc.fillText(s"Penguin saved: $score", 10, 40)
  }

  def addIceBerg(victim: Victim): Unit = {
    val newIceBerg = IceBerg.generate(windowWidth / blockSize, windowHeight / blockSize, penguin.body, victim, iceImage)
    iceBergs += newIceBerg
    iceBergCount += 1
  }

  val initialPenguinBody = ListBuffer(new GameObject(1, 1))
  penguin = new Penguin(initialPenguinBody, penImage)
  victim = Victim.generate(windowWidth / blockSize, windowHeight / blockSize, penguin.body, iceBergs.map(b => new GameObject(b.x, b.y)).toSet)
  addIceBerg(victim)
}


