/* De gevraagde ondraaibare cel in de projectbeschrijvng */
case class UnrotateablyMovingCell(initrow: Int, initcol: Int) extends GameCell(initrow, initcol), Unrotateable, Moveable {

  override def collisionAction(hitCel: GameCell): Boolean = {
    // als deze cel tegen een rotatecell botst, dan stopt deze cel met bewegen
    if(!hitCel.isInstanceOf[Rotatecell])
      super.collisionAction(hitCel)
    else
      false
  }
}