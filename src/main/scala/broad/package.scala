package broad

import eu.timepit.refined.api.Refined
import eu.timepit.refined.string._
import eu.timepit.refined._
import shapeless.{:+:, CNil}

package object cwl {

  //These are supposed to be valid ECMAScript Expressions.  See http://www.commonwl.org/v1.0/Workflow.html#Expressions
  type Expression = String Refined MatchesRegex[W.`"$({.*}|{.*})"`.T] 

  type MyriadInputType = 
    CWLType :+:
    InputRecordSchema :+:
    InputEnumSchema :+:
    InputArraySchema :+:
    String :+:
    Array[CWLType] :+:
    Array[InputRecordSchema] :+:
    Array[InputEnumSchema] :+:
    Array[InputArraySchema] :+:
    Array[String] :+:
    CNil

  type MyriadOutputType = 
    CWLType :+:
    OutputRecordSchema :+:
    OutputEnumSchema :+:
    OutputArraySchema :+:
    String :+:
    Array[CWLType] :+:
    Array[OutputRecordSchema] :+:
    Array[OutputEnumSchema] :+:
    Array[OutputArraySchema] :+:
    Array[String] :+:
    CNil


}
