// een enumeration voor de richtingen
// elke richting houdt bij wat "de volgende positie" is volgens die richting
// de richtingen zijn "geordend", dus de opvolger van elke richting is de richting die je zou krijgen als je die richting 90° zou roteren
enum Direction(val nextRow: Int, val nextCol: Int):

  case unspecified extends Direction(0, -1)
  case right extends Direction(0, 1)
  case down extends Direction(1, 0)
  case left extends Direction(0, -1)
  case up extends Direction(-1, 0)

