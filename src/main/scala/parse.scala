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

val wf = decode[Workflow](parser.parse(enforceType).right.get.noSpaces)
 println(wf)

}

case class Workflow(
  cwlVersion: String,
  `class`: String,
  inputs: Map[InputParameter#Id, InputParameter] :+: Map[InputParameter#Id, InputParameter#`type`] :+: Array[InputParameter] :+: CNil,
  outputs: Map[WorkflowOutputParameter#Id, WorkflowOutputParameter] :+: Map[WorkflowOutputParameter#Id, WorkflowOutputParameter#`type`] :+: Array[WorkflowOutputParameter] :+: CNil,
  steps: Array[WorkflowStep]
  )

case class InputParameter(
  id: Option[String], //not really optional but can be specified upstream 
  label: Option[String],
  secondaryFiles: Option[Expression :+: String :+: Array[Either[Expression, String]] :+: CNil],
  format: Option[Expression :+: String :+: Array[String] :+: CNil],
  streamable: Option[Boolean],
  doc: Option[Either[String, Array[String]]],
  inputBinding: Option[CommandLineBinding],
  default: Option[String], //can be of type "Any" which... sucks.
  `type`: Option[MyriadInputType]) {

  type `type` = MyriadInputType
  type Id = String
}

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
  secondaryFiles: Option[Expression :+: String :+: Array[Either[Expression, String]] :+: CNil],
  format: Option[Expression :+: String :+: Array[String] :+: CNil],
  streamable: Option[Boolean],
  doc: Option[Either[String, Array[String]]],
  `type`: Option[MyriadOutputType]) {

  type `type` = MyriadOutputType
  type Id = String
}

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

case class OutputArraySchema(
  items: MyriadOutputType,
  `type`: String Refined MatchesRegex[W.`"array"`.T],
  label: Option[String],
  outputBinding: Option[CommandOutputBinding])


case class WorkflowStep(
  id: Option[String], //not actually optional but can be declared as a key for this whole object for convenience
  in: Array[WorkflowStepInput] :+: Map[WorkflowStepInputId, WorkflowStepInputSource] :+: Map[WorkflowStepInputId, WorkflowStepInput] :+: CNil,
  out: Either[Array[String], Array[WorkflowStepOutput]],
  run: String :+: CommandLineTool :+: ExpressionTool :+: Workflow :+: CNil,
  requirements: Option[Array[Requirement]],
  hints: Array[String], //TODO: should be 'Any' type
  label: Option[String],
  doc: Option[String],
  scatter: Either[String, Array[String]],
  scatterMethod: Option[ScatterMethod]
  
  )

case class InlineJavascriptRequirement(
  `class`: String Refined MatchesRegex[W.`"InlineJavascriptRequirement"`.T],
  expressionLib: Option[Array[String]])

case class SchemaDefRequirement(
  `class`: String Refined MatchesRegex[W.`"SchemaDefRequirement"`.T],
  types: Array[InputRecordSchema :+: InputEnumSchema :+: InputArraySchema :+: CNil])

//There is a large potential for regex refinements on these string types
case class DockerRequirement(
  `class`: String Refined MatchesRegex[W.`"DockerRequirement"`.T],
  dockerPull: Option[String], //TODO Refine to match a docker image regex?
  dockerLoad: Option[String],
  dockerFile: Option[String],
  dockerImport: Option[String],
  dockerImageId: Option[String],
  dockerOutputDirectory: Option[String]
  )

case class SoftwareRequirement(
  `class`: String Refined MatchesRegex[W.`"SoftwareRequirement"`.T],
  packages: Array[SoftwarePackage] :+: Map[SoftwarePackage#Package, SoftwarePackage#Specs] :+: Map[SoftwarePackage#Package, SoftwarePackage] :+: CNil
  )

case class SoftwarePackage(
  `package`: String,
  version: Option[Array[String]],
  specs: Option[Array[String]] // This could be refined to match a regex for IRI.
  ) {
  type Package = String
  type Specs = Array[String]
}

case class InitialWorkDirRequirement(
  `class`: String Refined MatchesRegex[W.`"InitialWorkDirRequirement"`.T],
  listing: 
    Array[
      File :+:
      Directory :+:
      Dirent :+:
      Expression :+:
      String :+:
      CNil
    ] :+:
    Expression :+:
    String :+:
    CNil

  )

/** 
 *  Short for "Directory Entry"
 *  @see <a href="http://www.commonwl.org/v1.0/CommandLineTool.html#Dirent">Dirent Specification</a>
 */
case class Dirent(
  entry: Either[Expression, String], 
  entryName: Option[Either[Expression, String]],
  writable: Option[Boolean]
  )

//TODO
//Figure out how to declare Any type
case class ExpressionTool()
case class WorkflowStepInput()
case class WorkflowStepOutput()
case class EnvVarRequirement(
  `class`: String Refined MatchesRegex[W.`"EnvVarRequirement"`.T],
  envDef: 
    Array[EnvironmentDef] :+:
    Map[EnvironmentDef#EnvName, EnvironmentDef#EnvValue] :+:
    Map[EnvironmentDef#EnvName, EnvironmentDef] :+:
    CNil)

case class EnvironmentDef(envName: String, envValue: Either[Expression, String]) {
  type EnvName = String
  type EnvValue = String
}

case class ShellCommandRequirement(`class`: String Refined MatchesRegex[W.`"ShellCommandRequirement"`.T])

case class ResourceRequirement(
  `class`: String Refined MatchesRegex[W.`"ResourceRequirement"`.T],
  coresMin: Long :+: Expression :+: String :+: CNil,
  coresMax: Int :+: Expression :+: String :+: CNil,
  ramMin: Long :+: Expression :+: String :+: CNil,
  ramMax: Long :+: Expression :+: String :+: CNil,
  tmpdirMin: Long :+: Expression :+: String :+: CNil,
  tmpdirMax: Long :+: Expression :+: String :+: CNil,
  outdirMin: Long :+: Expression :+: String :+: CNil,
  outdirMax: Long :+: Expression :+: String :+: CNil
  )

case class SubworkflowFeatureRequirement(
  `class`: String Refined MatchesRegex[W.`"SubworkflowFeatureRequirement"`.T],
  )

case class ScatterFeatureRequirement(
  `class`: String Refined MatchesRegex[W.`"ScatterFeatureRequirement"`.T],
  )

case class MultipleInputFeatureRequirement(
  `class`: String Refined MatchesRegex[W.`"MultipleInputFeatureRequirement"`.T],
  )

case class StepInputExpressionRequirement(
  `class`: String Refined MatchesRegex[W.`"StepInputExpressionRequirement"`.T],
  )
