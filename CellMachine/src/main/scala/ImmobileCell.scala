// een cel die niet beweegt en niet geroteerd kan worden.
case class ImmobileCell(initrow: Int, initcol: Int) extends GameCell(initrow, initcol), Unmoveable, Unrotateable