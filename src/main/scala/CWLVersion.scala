package broad.cwl.model

import enumeratum._

sealed abstract class CWLVersion(override val entryName: String) extends EnumEntry

object CWLVersion extends Enum[CWLVersion] {
  val values = findValues

  case object Draft2 extends CWLVersion("draft-2")
  case object Draft2Dev1 extends CWLVersion("draft-3.dev1")
  case object Draft2Dev2 extends CWLVersion("draft-3.dev2")
  case object Draft2Dev3 extends CWLVersion("draft-3.dev3")
  case object Draft2Dev4 extends CWLVersion("draft-3.dev4")
  case object Draft2Dev5 extends CWLVersion("draft-3.dev5")
  case object Draft3 extends CWLVersion("draft-3")
  case object Draft4Dev1 extends CWLVersion("draft-4.dev1")
  case object Draft4Dev2 extends CWLVersion("draft-4.dev2")
  case object Draft4Dev3 extends CWLVersion("draft-4.dev3")
  case object Version1Dev4 extends CWLVersion("v1.0.dev4")
  case object Version1 extends CWLVersion("v1.0")
}


