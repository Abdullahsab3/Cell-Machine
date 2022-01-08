/* Het generische grid */
class Grid[T <: GameCell](val rows: Int, val cols: Int) {
  var activeCells = List[T]()
  var nrOfCells = 0

  def foreach(proc: (cell: T) => Unit): Unit = {
    activeCells.foreach(proc)
  }

  // een nieuwe cel toevoegen zolang die binnen de meegegeven gridgrenzen valt
  def addCell(cell: T): Unit = {
    if(cell.row < rows && cell.row >= 0
      && cell.col < cols && cell.col >= 0)
      activeCells = cell :: activeCells
      nrOfCells += 1
  }

  // een lijst van cellen toevoegen
  def addCells(cells: List[T]): Unit = {
    cells.foreach((cell: T) => addCell(cell))
  }

  // een cel zoeken adhv de rij en kolom
  def getCell(row: Int, col: Int): List[T] = {
    activeCells.filter((cell: T) => cell.row == row && cell.col == col)
  }

  // cellen vinden adhv een lijst van rij en kolom tuples
  def getCell(rowandcols: List[(Int, Int)]): List[T] = {
    activeCells.filter((cell: T) =>
      !(rowandcols.filter((rowandcol: (Int, Int)) =>
        rowandcol._1 == cell.row &&
        rowandcol._2 == cell.col).isEmpty))
  }

  // de coordinaten van de cel aanpassen
  def setCell(cell: T, row: Int, col: Int): Boolean = {
    // check of er niet al een cel is die in dat vakje zit
    val newcell = getCell(row, col)
    if(newcell.isEmpty || newcell.head == cell)
      cell.row = row
      cell.col = col
      true
    else
      false
  }

  // verwijder de cellen die verwijderd dienen te worden
  def removeCells(): Unit = {
    activeCells = activeCells.filterNot(
      (cell: T) =>
        if(cell.removed)
          nrOfCells -= 1
        cell.removed)
  }

  // alle cellen van het grid verwijderen
  def removeAllCells(): Unit = {
    activeCells.foreach((cell: T) => cell.remove())
    removeCells()
  }



  // de cellen laten bewegen, de acties van de cellen activeren, en de cellen die verwijderd dienen te worden verwijderen
  def activateAll(): Unit = {
    activeCells.foreach(_.move(getCell))
    activeCells.foreach(_.cellAction(getCell))
    removeCells()
  }

}