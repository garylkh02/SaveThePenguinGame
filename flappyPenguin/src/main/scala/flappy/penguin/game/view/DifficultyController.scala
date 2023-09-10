package flappy.penguin.game.view

import flappy.penguin.game.MainApp
import flappy.penguin.game.model.Difficulty
import scalafx.scene.control.Button
import scalafxml.core.macros.sfxml

@sfxml
class DifficultyController(private val startGame: Button,
                           private val easyButton: Button,
                           private val mediumButton: Button,
                           private val hardButton: Button,
                           private val backButton: Button,
                           private val instruction: Button){

  def launchGame(): Unit = {
    GameController.startGame()
  }

  def easy(): Unit = {
    GameController.currentDifficulty = Difficulty.Easy
    GameController.updateDelay = GameController.easyParameters
  }

  def medium(): Unit = {
    GameController.currentDifficulty = Difficulty.Medium
    GameController.updateDelay = GameController.mediumParameters
  }

  def hard(): Unit = {
    GameController.currentDifficulty = Difficulty.Hard
    GameController.updateDelay = GameController.hardParameters
  }

  def backStory2(): Unit = {
    MainApp.showStory2()
  }

  def showInstructions(): Unit = {
    MainApp.showInstuction()
  }

}
