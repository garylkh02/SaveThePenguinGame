package flappy.penguin.game.view

import flappy.penguin.game.MainApp
import scalafx.scene.control.Button
import scalafxml.core.macros.sfxml


@sfxml
class Story2Controller(private val prev: Button,
                       private val next: Button){

  def backStory1(): Unit = {
    MainApp.showStory()
  }

  def showDiff(): Unit = {
    MainApp.chooseDifficulty()
  }

}
