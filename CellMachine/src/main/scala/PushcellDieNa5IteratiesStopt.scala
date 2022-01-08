/* De gevraagde duwcel die na 5 iteraties stopt met bewegen */
case class PushcellDieNa5IteratiesStopt(initrow: Int, initcol: Int) extends GameCell(initrow, initcol), BasicPushcell, Rotateable {
  var movectr = 0
  override def move(getCell: (row: Int, col: Int) => List[GameCell]): Boolean = {
    movectr += 1
    if(movectr <= 5)
      super.move(getCell)
    else
      moving = false
      moving
  }

}