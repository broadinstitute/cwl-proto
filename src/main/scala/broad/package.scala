package broad.cwl

import eu.timepit.refined.api.Refined
import eu.timepit.refined.string._
import eu.timepit.refined._
import shapeless.{:+:, CNil}

package object model {

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
