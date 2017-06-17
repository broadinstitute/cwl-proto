package broad.cwl
import enumeratum.Circe._
import io.circe.syntax._
import io.circe._
import io.circe.parser._
import io.circe.shapes._
import io.circe.generic.auto._
import shapeless.{:+:, CNil}
import cats._, implicits._//, instances._

import io.circe.yaml.parser
import io.circe._

import eu.timepit.refined.string._

import eu.timepit.refined._
import eu.timepit.refined.api.Refined
import eu.timepit.refined.auto._
import io.circe.refined._

object X extends App {
  val firstTool = """cwlVersion: v1.0
class: CommandLineTool
baseCommand: echo
inputs:
  message:
    type: string
    inputBinding:
      position: 1
outputs: []
"""

  val enforceType = """cwlVersion: v1.0
class: CommandLineTool
baseCommand: echo
inputs:
  message:
    type: 
      type: rsecord
outputs: []
"""

  val firstWorkflow = """
cwlVersion: v1.0
class: Workflow
inputs:
  inp: File
  ex: string

outputs:
  classout:
    type: File
    outputSource: compile/classfile

steps:
  untar:
    run: tar-param.cwl
    in:
      tarfile: inp
      extractfile: ex
    out: [example_out]

  compile:
    run: arguments.cwl
    in:
      src: untar/example_out
    out: [classfile]
"""

//val wf = decode[Workflow](parser.parse(firstWorkflow).right.get.noSpaces)
val wf = decode[Workflow](parser.parse(enforceType).right.get.noSpaces)
 println(wf)

/*
val toolJson = parser.parse(firstTool).right.get.noSpaces
println(toolJson)
  println(decode[Workflow](toolJson))
  */
  //println(wf.right.get.inputs.select[Map[String, String]])
//  println(decode[Workflow](firstWorkflow))
  //Workflow(null, null, null).asJson
}

case class Workflow(
  cwlVersion: String,
  `class`: String,
  inputs: Map[String, InputParameter] :+: Map[String, String] :+: Array[InputParameter] :+: CNil,
  outputs: Map[String, WorkflowOutputParameter] :+: Map[String, String] :+: Array[WorkflowOutputParameter] :+: CNil,
  //steps: Array[WorkflowStep]
  )

case class InputParameter(
  id: Option[String], //not really optional but can be specified upstream 
  label: Option[String],
  secondaryFiles: Option[Expression :+: String :+: Array[Expression] :+: Array[String] :+: CNil],
  format: Option[Expression :+: String :+: Array[String] :+: CNil],
  streamable: Option[Boolean],
  doc: Option[Either[String, Array[String]]],
  inputBinding: Option[CommandLineBinding],
  default: Option[String], //can be of type "Any" which... sucks.
  `type`: Option[MyriadInputType])

case class InputRecordSchema(
  `type`: String Refined MatchesRegex[W.`"record"`.T],
  fields: Option[Array[InputRecordField]],
  label: Option[String]) 

case class InputRecordField(
  name: String,
  `type`: MyriadInputType,
  doc: Option[String],
  inputBinding: Option[CommandLineBinding],
  label: Option[String])

case class InputEnumSchema(
  symbols: Array[String],
  `type`: String Refined MatchesRegex[W.`"enum"`.T],
  label: Option[String],
  inputBinding: Option[CommandLineBinding])

case class InputArraySchema(
  items: MyriadInputType,
  `type`: String Refined MatchesRegex[W.`"array"`.T],
  label: Option[String],
  inputBinding: Option[CommandLineBinding])

case class CommandLineBinding(
  loadContents: Option[Boolean],
  position: Option[Int],
  prefix: Option[String],
  separate: Option[String],
  itemSeparator: Option[String],
  valueFrom: Option[Either[Expression,String]], // could be "Expression" to be evaluated
  shellQuote: Option[Boolean])

case class WorkflowOutputParameter(
  id: Option[String], //Really not optional but can be declared upstream
  label: Option[String],
  secondaryFiles: Option[Expression :+: String :+: Array[Expression] :+: Array[String] :+: CNil],
  format: Option[Expression :+: String :+: Array[String] :+: CNil],
  streamable: Option[Boolean],
  doc: Option[Either[String, Array[String]]],
  `type`: Option[MyriadOutputType])

case class InputBinding(position: Int, prefix: String)

case class OutputRecordSchema(
  `type`: String Refined MatchesRegex[W.`"record"`.T],
  fields: Option[Array[OutputRecordField]],
  label: Option[String]
  )

case class OutputRecordField(
  name: String,
  `type`: MyriadOutputType,
  doc: Option[String],
  outputBinding: Option[CommandOutputBinding])

case class OutputEnumSchema(
  symbols: Array[String],
  `type`: String Refined MatchesRegex[W.`"enum"`.T],
  label: Option[String],
  outputBinding: Option[CommandOutputBinding])

/** @see <a href="http://www.commonwl.org/v1.0/Workflow.html#CommandOutputBinding">CommandOutputBinding</a> */
case class CommandOutputBinding(
  glob: Option[Expression :+: String :+: Array[String] :+: CNil],
  loadContents: Option[Boolean],
  outputEval: Option[Either[Expression, String]])

case class OutputArraySchema()


case class WorkflowStep()
