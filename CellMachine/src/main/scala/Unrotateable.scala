trait Unrotateable extends GameCell {
  rotateDirection = Direction.unspecified
  def rotate(): Unit ={}
}
