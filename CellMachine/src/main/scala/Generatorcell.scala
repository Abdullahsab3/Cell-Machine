/* een generatorcel zal cellen die voor deze cel zich bevinden genereren
 indien er na deze cel een cel aanwezig is, dan gaat deze cel proberen om die cel te laten bewegen
 om plaats te maken voor de nieuwe cel */
class Generatorcell(initrow: Int, initcol: Int, initdir: Direction = Direction.right, addCell: (cell: GameCell) => Unit) 
  extends GameCell(initrow, initcol, initRotateDir = initdir), Moveable, Rotateable {
  // de coordinaten van de te genereren cel zoeken afh van de richting van de generatorcel
  private def toBeGeneratedCoords : (Int, Int) =
    (row - this.rotateDirection.nextRow, 
      col - this.rotateDirection.nextCol)

  def copy(newRow:  Int, newCol:  Int): GameCell = {
    val n = new Generatorcell(newRow, newCol, addCell = addCell)
    n
  }

  private def getNewPos():(Int, Int) = {
    (row + this.rotateDirection.nextRow, 
      col + this.rotateDirection.nextCol)
  }

  override def cellAction(getCells: List[(Int, Int)] => List[GameCell]): Unit = {
    var toBeGeneratedCell = getCells(List[(Int, Int)](toBeGeneratedCoords))
    if(!toBeGeneratedCell.isEmpty)
      // de positie waarin de nieuwe cel gaat terechtkomen
      val newpos = getNewPos()
      def cellInNewPos = getCells(List[(Int, Int)](newpos))
      // als die positie leeg is, dan mag je de erin steken
      if(cellInNewPos.isEmpty)
        val newCell = toBeGeneratedCell.head.copy(newpos._1, newpos._2)
        addCell(newCell)
      // als de positie niet leeg was, probeer om de cel die daarin zit te doen bewegen
      else
        cellInNewPos.head.moving = true
        if (cellInNewPos.head.direction == Direction.unspecified)
          cellInNewPos.head.direction = this.rotateDirection
        if(cellInNewPos.head.move((r: Int, c: Int) => getCells(List[(Int, Int)]((r, c)))))
          // als de positie na het moven wel vrij is gekomen, voeg de cel toe
          // anders kan je geen nieuwe cellen toevoegen
          if(cellInNewPos.isEmpty)
            val newCell = toBeGeneratedCell.head.copy(newpos._1, newpos._2)
            addCell(newCell)
  }

}
