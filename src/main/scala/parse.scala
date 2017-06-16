package broad
import io.circe.syntax._
import io.circe.shapes._
import io.circe.generic.auto._
import shapeless.{:+:, CNil}

case class Workflow(
  cwlVersion: String,
  `class`: String,
  inputs: Map[String, InputParameter] :+: Map[String, String] :+: Array[InputParameter] :+: CNil//,
  //outputs: Map[String, WorkflowOutputParameter] :+: Map[String, String] :+: Array[WorkflowOutputParameter] :+: CNil,
  //steps: Array[WorkflowStep]
  )

/*
//Enumerations don't work ... :(
object CWLType extends Enumeration {
  type CWLType = Value
  val string = Value("string")
}
import CWLType._
*/
//case class CWLType()
sealed trait CWLType
case object string extends CWLType
case object `null` extends CWLType

case class InputParameter(
  id: String,
  label: Option[String],
  secondaryFiles: Option[Either[String, Array[String]]],
  format: Option[Either[String, Array[String]]],
  streamable: Option[Boolean],
  doc: Option[Either[String, Array[String]]],
  inputBinding: Option[CommandLineBinding],
  default: String, //can be of type "Any" which... sucks.
  `type`: Option[CWLType :+: InputRecordSchema :+: InputEnumSchema :+: InputArraySchema :+: String :+: Array[CWLType] :+: Array[InputRecordSchema] :+: Array[InputEnumSchema] :+: Array[InputArraySchema] :+: Array[String] :+: CNil]
  )

case class InputRecordSchema()
case class InputEnumSchema()
case class InputArraySchema()


case class CommandLineBinding(
  loadContents: Option[Boolean],
  position: Option[Int],
  prefix: Option[String],
  separate: Option[String],
  itemSeparator: Option[String],
  valueFrom: Option[String], // could be "Expression" to be evaluated
  shellQuote: Option[Boolean]
  )

case class WorkflowOutputParameter(id: String, `type`: String, inputBinding: InputBinding)

case class Expression()

case class InputBinding(position: Int, prefix: String)

case class WorkflowStep()

object X extends App {
  //Workflow(null, null).asJson
  Workflow(null, null, null).asJson
}


/*
syntax="proto3";

message Workflow {
  string cwlVersion = 1;
  string class = 2;
  map<string, InputParameter> inputs = 4;
  map<string, OutputParameter> outputs = 5;
  map<string, WorkflowStep> steps = 6;
  string id = 3;

  repeated Requirement requirements  = 7;

}

message Requirement {
  string class = 1;
  repeated string expressionLib = 2;
  repeated Schema types = 3;
}

message Schema {
  string type = 1;
  repeated InputRecordField fields = 2;
  string label = 3;
}

message InputRecordField {
  string name = 1;
  string type = 2;
}

message DockerRequirement {}

message InputParameter {
  string id = 1;
  string type = 2;
  InputBinding inputBinding = 3;
}

message InputBinding {
  uint32 position = 1;
  string prefix = 2;
}

message OutputParameter {
  string id = 1;
}

message ClassOut {
  string type = 1;
  string outputSource = 2;
}

message WorkflowStep {
  string run = 1;
  repeated string out = 2;
  In in = 3;
}

message In {
  map<string, string> inMap = 1;
}
*/
