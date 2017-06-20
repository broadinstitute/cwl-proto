package broad.cwl

import eu.timepit.refined.api.Refined
import eu.timepit.refined.string._
import eu.timepit.refined._
import shapeless.{:+:, CNil}
import io.circe.Decoder

package object model {


  object CWLVersion extends Enumeration {
    type CWLVersion = Value

    val Draft2 = Value("draft-2")
    val Draft2Dev1 = Value("draft-3.dev1")
    val Draft2Dev2 = Value("draft-3.dev2")
    val Draft2Dev3 = Value("draft-3.dev3")
    val Draft2Dev4 = Value("draft-3.dev4")
    val Draft2Dev5 = Value("draft-3.dev5")
    val Draft3 = Value("draft-3")
    val Draft4Dev1 = Value("draft-4.dev1")
    val Draft4Dev2 = Value("draft-4.dev2")
    val Draft4Dev3 = Value("draft-4.dev3")
    val Version1Dev4 = Value("v1.0.dev4")
    val Version1 = Value("v1.0")
  }
  import CWLVersion._
  implicit val cwlVersionDecoder = Decoder.enumDecoder(CWLVersion)

  type WorkflowStepInputId = String

  type WorkflowStepInputSource = String :+: Array[String] :+: CNil

  //These are supposed to be valid ECMAScript Expressions.  See http://www.commonwl.org/v1.0/Workflow.html#Expressions
  type Expression = String Refined MatchesRegex[W.`"$({.*}|{.*})"`.T] 

  type Requirement = 
    InlineJavascriptRequirement :+:
    SchemaDefRequirement :+:
    DockerRequirement :+:
    SoftwareRequirement :+:
    InitialWorkDirRequirement :+:
    EnvVarRequirement :+:
    ShellCommandRequirement :+:
    ResourceRequirement :+:
    SubworkflowFeatureRequirement :+:
    ScatterFeatureRequirement :+:
    MultipleInputFeatureRequirement :+:
    StepInputExpressionRequirement :+:
    CNil

  type MyriadInputType = 
    CWLType :+:
    InputRecordSchema :+:
    InputEnumSchema :+:
    InputArraySchema :+:
    String :+:
    Array[
      CWLType :+:
      InputRecordSchema :+:
      InputEnumSchema :+:
      InputArraySchema :+:
      String :+:
      CNil
    ] :+:
    CNil

  type MyriadOutputType = 
    CWLType :+:
    OutputRecordSchema :+:
    OutputEnumSchema :+:
    OutputArraySchema :+:
    String :+:
    Array[
      CWLType :+:
      OutputRecordSchema :+:
      OutputEnumSchema :+:
      OutputArraySchema :+:
      String :+:
      CNil
    ] :+:
    CNil

  type MyriadCommandInputType = 
    CWLType :+:
    CommandInputRecordSchema :+:
    CommandInputEnumSchema :+:
    CommandInputArraySchema :+:
    String :+:
    Array[
      CWLType  :+:
      CommandInputRecordSchema :+:
      CommandInputEnumSchema :+:
      CommandInputArraySchema :+:
      String :+:
      CNil 
      ] :+:
    CNil 

}
