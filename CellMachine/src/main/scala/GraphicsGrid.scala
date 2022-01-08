import collection.JavaConverters.asJavaIterableConverter

// het guigrid (de tekenlogica en afhandeling van input)
class GraphicsGrid(grid: Grid[GameCell]) {


  var sprites = List[Sprite[GameCell]]()
  var nrOfSprites = 0
  val gui = GUI(scrwidth, scrheight, grid.rows, grid.cols, padding)
  val gridpanel = gui.getGridPanel()
  private val runbutton: RunCell = new RunCell
  private val restartbutton = new RestartCell


  // zet de venstercoordinaten om naar spelcoordinaten (x, y) naar (row, col)
  def calcGameCoord(x: Int, y: Int): (Int, Int) = {
    val col = (x - padding) / cellwidth
    val row = (y - padding) / cellheight
    (row, col)
  }

  // inputafhandeling

  var clickedOnRun = false
  var clickedOnRestart = false
  var elementGrabbed = false
  var element: GameCell =  null.asInstanceOf[GameCell]
  def mousePressCallback(x: Int, y: Int) = {
    val rowAndcal = calcGameCoord(x ,y)
    if(clickedOnRun)
      if(rowAndcal._1 == restartbutton.row && rowAndcal._2 == restartbutton.col)
        clickedOnRestart = true
    else
      if(rowAndcal._1 == runbutton.row &&
        rowAndcal._2 == runbutton.col) then
        clickedOnRun = true
      else
        if(rowAndcal._1 >= 0 && rowAndcal._1 < grid.rows
          && rowAndcal._2 >= 0 && rowAndcal._2 < grid.cols)

          if(elementGrabbed)
            if(grid.setCell(element, rowAndcal._1, rowAndcal._2))
              elementGrabbed = false

          else
            val potel = grid.getCell(rowAndcal._1, rowAndcal._2)
            if(!(potel.isEmpty) && !potel.head.isInstanceOf[Unmoveable])
              elementGrabbed = true
              element = potel.head
  }


  def addSprite(cell: GameCell): Unit = {
    val newSprite: Sprite[GameCell] = new Sprite[GameCell](cell)
    sprites = newSprite :: sprites
    nrOfSprites +=  1
  }

  def initializesprites(): Unit = {
    addSprite(runbutton)
    addSprite(restartbutton)
    grid.foreach(addSprite)
    gridpanel.addCells(sprites.asJava)
    gridpanel.setPressListener(mousePressCallback)
  }

  def showGameOver(): Unit = {
    runbutton.remove()
    restartbutton.remove()
    
    gridpanel.addCells(List(new GameWon).asJava)
  }

  def updateSprites(): Unit = {
    gridpanel.repaint()
    removeUnusedSprites()
    if(grid.nrOfCells > (nrOfSprites - 2))
      val newCells = grid.activeCells.slice(0, grid.nrOfCells - nrOfSprites + 2)
      val newSprites = newCells.map[Sprite[GameCell]]((cell : GameCell) =>
        nrOfSprites += 1
        new Sprite[GameCell](cell))
      gridpanel.addCells(newSprites.asJava)
      sprites = newSprites ::: sprites
  }

  def removeUnusedSprites(): Unit = {
    sprites = sprites.filterNot(
      (sprite: Sprite[GameCell]) =>
        if(sprite.isRemoved)
          nrOfSprites -= 1
        sprite.isRemoved)
  }

}
