package broad.cwl

import shapeless.{:+:, CNil}
import eu.timepit.refined.string._
import eu.timepit.refined._
import eu.timepit.refined.api.Refined
import eu.timepit.refined.auto._
import io.circe.refined._

case class CommandLineTool(
  inputs: CommandInputParameter :+: Map[CommandInputParameter#Id, CommandInputParameter#`type`] :+: Map[CommandInputParameter#Id, CommandInputParameter] :+: CNil,
  outputs: Array[CommandOutputParameter] :+: Map[CommandOutputParameter#Id, CommandOutputParameter#`type`] :+: Map[CommandOutputParameter#Id, CommandOutputParameter] :+: CNil,
  `class`: String,  //TODO: must be "CommandLineTool" or ?
  id: Option[String],
  requirements: Array[Requirement],
  hints: Option[Array[String]], //TODO: Any?
  label: Option[String],
  doc: Option[String],
  cwlVersion: Option[CWLVersion],
  baseCommand: Either[String, Array[String]],
  arguments: Array[Expression :+: CommandLineBinding :+: String :+: CNil],
  stdin: Option[Either[Expression, String]],
  stderr: Option[Either[Expression, String]],
  stdout: Option[Either[Expression, String]],
  successCodes: Array[Int],
  temporaryFailCodes: Array[Int],
  permanentFailCodes: Array[Int])

case class CommandInputParameter(
  id: String,
  label: Option[String],
  secondaryFiles: Option[Array[Either[Expression, String]]],
  format: Expression :+: Array[String] :+: String :+: CNil, //only valid when type: File
  streamable: Option[Boolean],//only valid when type: File
  doc: Option[Either[String, Array[String]]],
  inputBinding: Option[CommandLineBinding],
  default: String, //TODO Any type here
  `type`: MyriadInputType

) {
  type Id = String
  type `type` = MyriadCommandInputType
}

case class CommandInputRecordSchema(
  `type`: String Refined MatchesRegex[W.`"record"`.T],
  fields: Option[Array[CommandInputRecordField]],
  label: Option[String])

case class CommandInputRecordField(
  name: String,
  `type`: MyriadInputType,
  doc: Option[String],
  inputBinding: Option[CommandLineBinding],
  label: Option[String])

case class CommandInputEnumSchema(
  symbols: Array[String],
  `type`: String Refined MatchesRegex[W.`"enum"`.T],
  label: Option[String],
  inputBinding: Option[CommandLineBinding])

case class CommandInputArraySchema(
  items: 
    CWLType :+:
    CommandInputRecordSchema :+:
    CommandInputEnumSchema :+:
    CommandInputArraySchema :+:
    String :+:
    Array[
      CWLType :+:
      CommandInputRecordSchema :+:
      CommandInputEnumSchema :+:
      CommandInputArraySchema :+:
      String :+:
      CNil
     ] :+:
    CNil,
  `type`: String Refined MatchesRegex[W.`"array"`.T],
  label: Option[String],
  inputBinding: Option[CommandLineBinding])


case class CommandOutputParameter(
  id: String,
  label: Option[String],
  secondaryFiles: Option[Expression :+: String :+: Array[Either[Expression, String]] :+: CNil],
  format: Expression :+: Array[String] :+: String :+: CNil, //only valid when type: File
  streamable: Option[Boolean],//only valid when type: File
  doc: Option[Either[String, Array[String]]],
  outputBinding: Option[CommandOutputBinding],
  `type`: MyriadOutputType) {

  type `type` = String
  type Id = String
}

