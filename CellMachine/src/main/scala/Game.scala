/*

 ___  ___  _    _     __ __  ___  ___  _ _  _  _ _  ___
|  _>| __>| |  | |   |  \  \| . ||  _>| | || || \ || __>
| <__| _> | |_ | |_  |     ||   || <__|   || ||   || _>
`___/|___>|___||___| |_|_|_||_|_|`___/|_|_||_||_\_||___>

                Abdullah Sabaa Allil
             abdullah.sabaa.allil@vub.be
                    2021 - 2022

 */


val padding = 40
val scrwidth = 500
val scrheight = 500

val rows = 10
val cols = 10

val cellwidth = (scrwidth - 2*padding) / cols
val cellheight = (scrheight - 2*padding) / rows



object Game:
  @main def run(): Unit =
    val gameGrid : CellMachineGrid = CellMachineGrid(rows, cols)
    val guiGrid : GraphicsGrid = GraphicsGrid(gameGrid)
    val gamespeed = 50
    gameGrid.makeLevel()
    gameGrid.initializeGrid()
    guiGrid.initializesprites()


    var shouldContinue = true
    /* De gameloop*/
    while(shouldContinue)
      if(guiGrid.clickedOnRun)
        gameGrid.activateAll()

        if(guiGrid.clickedOnRestart)
          gameGrid.makeLevel()
          guiGrid.clickedOnRun = false
          guiGrid.clickedOnRestart = false

        if(Enemy.nrOfEnemies == 0) then
          gameGrid.levelUp()
          if (gameGrid.gameIsDone())
            println("YOU WON")
            Thread.sleep(1000)  // een heel kleine pauze zodat de speler ziet wat er gebeurd is
            gameGrid.removeAllCells()
            guiGrid.showGameOver()
            shouldContinue = false
          else
            Thread.sleep(1000)  // een heel kleine pauze zodat de speler ziet wat er gebeurd is
            gameGrid.makeLevel()
            guiGrid.clickedOnRun = false

      guiGrid.updateSprites()
      Thread.sleep(gamespeed)