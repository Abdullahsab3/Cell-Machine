// een abstracte klasse voor de spelcellen
// de cellen hebben coordinaten (row, col), een beweegrichting (direction) en een rotatierichting(rotateDirection)
abstract class GameCell(initrow: Int = 0, initcol: Int = 0,
                        initDir: Direction = Direction.unspecified,
                        initRotateDir: Direction = Direction.right) {

  var row = initrow
  var col = initcol
  var direction = initDir
  var rotateDirection = initRotateDir
  var moving: Boolean = false
  var removed = false


  def move(getCell: (row2: Int, col2: Int) => List[GameCell]): Boolean
  def rotate(): Unit
  def collisionAction(hitCel: GameCell): Boolean
  def hitByACell(hitBy: GameCell): Unit
  def cellAction(getCells : (List[(Int, Int)]) => List[GameCell]): Unit = {}
  def copy(newRow: Int = row, newCol: Int = col): GameCell

  def setCoord(newRow: Int, newCol: Int): Unit = {
    row = newRow
    col = newCol
  }

  def setVals(newRow: Int, newCol: Int, newRotateDir: Direction): Unit = {
    setCoord(newRow, newCol)
    rotateDirection = newRotateDir
  }

  def remove(): Boolean = {
    removed = true
    removed
  }

}

