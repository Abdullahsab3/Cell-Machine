// cellen die gedraaid kunnen worden
trait Rotateable extends GameCell {

  def rotate(): Unit = {
    rotateDirection = Direction.fromOrdinal((rotateDirection.ordinal + {if(rotateDirection == Direction.up) then 2 else 1}) % 5)
  }
}
