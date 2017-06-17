package broad

import enumeratum._

sealed abstract class CWLType(override val entryName: String) extends EnumEntry

object CWLType extends Enum[CWLType] {
  val values = findValues

  case object Null extends CWLType("null")
  case object Boolean extends CWLType("boolean")
  case object Int extends CWLType("int")
  case object Long extends CWLType("long")
  case object Float extends CWLType("float")
  case object Double extends CWLType("double")
  case object String extends CWLType("string")
  case object File extends CWLType("File")
  case object Directory extends CWLType("Directory")
}


