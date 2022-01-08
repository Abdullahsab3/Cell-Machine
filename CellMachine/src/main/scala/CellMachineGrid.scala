// een uitbreiding op het generische grid om het spelgrid te initialiseren en om grenscellen in te stellen
class CellMachineGrid(rows: Int, cols: Int) extends Grid [GameCell](rows, cols){

  val bordercells = List.fill[ImmobileCell](rows * cols - 4)(new ImmobileCell(0, 0))

  // zal het spelgrid initialiseren door de grensvakjes te vullen met immobile cellen
  def initializeGrid() = {
    for(col <- 0 to cols - 1)
      val element = bordercells(col)
      addCell(element)
      element.setCoord(0, col)
      val eldown = bordercells(((rows * cols - 4) - 1) - col)
      addCell(eldown)
      eldown.setCoord(rows - 1, col)

    for(row <- 1 to rows - 2)
      val element = bordercells(row + cols - 1)
      addCell(element)
      element.setCoord(row, 0)
      val elementright = bordercells(((rows * cols - 4) - 1) - (cols - 1) - row)
      addCell(elementright)
      elementright.setCoord(row, cols - 1)
  }

  // De cellen uit het vorige level verwijderen indien er nog cellen zijn
  def removeOldCells(): Unit = {
    (activeCells diff bordercells).foreach(
    (cell: GameCell) => cell.remove())
    removeCells()
  }

  var currentLevel = 0
  def levelUp():Unit = {
    currentLevel += 1
  }

  // een array van lambdas die de de configuratie van de levels bevatten
  val levelContents : Array[() => List[GameCell]] = Array[() => List[GameCell]](
    () => List(PushCell(2, 2), Enemy(5, 5)),

    () =>
      val s = Slidecell(4, 5)
      s.rotateDirection = Direction.down
      List(PushCell(2, 2), Enemy(4, 3), Enemy(5, 3), s, Rotatecell(7, 7)),

    () => List(Rotatecell(1, 1), Generatorcell(1, 2, Direction.right, addCell), Enemy(4, 1), Enemy(4, 2), Enemy(4, 7), Enemy(4, 8)),

    () => List(PushedCell(5, 5), Generatorcell(1, 1, Direction.right, addCell), Enemy(3, 6), Enemy(3, 7), Enemy(3, 8)),

    () => List(Generatorcell(1, 2, Direction.right, addCell), PushCell(4, 4),  Enemy(8, 7), Enemy(1, 7), Rotatecell(5, 5)),

    () => List(PushcellDieNa5IteratiesStopt(1, 1), Enemy(8, 8)),

    () => {
      val p = PushCell(1, 2)
      p.rotateDirection = Direction.left
      List(Rotatecell(1, 1), Generatorcell(3, 8, Direction.right, addCell), p, Enemy(4, 5), Enemy(7, 8))
    },

    () =>
      val p = PushCell(2, 1)
      val p2 = PushCell(4, 6)
      val p3 = PushCell(5, 5)
      val p4 = PushCell(8, 6)
      val s = Slidecell(4, 5)
      s.rotateDirection = Direction.up
      p.rotateDirection = Direction.right
      p2.rotateDirection = Direction.left
      p3.rotateDirection = Direction.left
      p4.rotateDirection = Direction.left
      List(Rotatecell(1, 1), UnrotateablyMovingCell(1, 2), Enemy(1, 3), Enemy(2, 3),
        Enemy(1, 5), Enemy(1, 7), Enemy(2, 5), Enemy(2, 7),
        p, s, UnrotateablyMovingCell(8, 8),
        UnrotateablyMovingCell(7, 1),
        p2, p3, p4, ImmobileCell(6, 1),  ImmobileCell(6, 2), ImmobileCell(6, 4), ImmobileCell(6, 6), ImmobileCell(6, 8)),

    () => {
          val p2 = PushCell(1, 2)
          val p3 = PushCell(1, 3)
          p2.rotateDirection = Direction.up
          p3.rotateDirection = Direction.up
          List(Enemy(3, 8), Enemy(4, 8), Enemy(5, 8), ImmobileCell(6, 8), ImmobileCell(2, 8), Rotatecell(4, 5),ImmobileCell(2, 2),
            p2, p3, PushCell(1, 5))
  }
  )

  // een nieuw level aanmaken
  def makeLevel(): Unit = {
    removeOldCells()
    addCells(levelContents(currentLevel)())
  }

  // het spel is gedaan wanneer er geen levels meer ijn
  def gameIsDone(): Boolean = {
    currentLevel >= levelContents.length
  }

}
