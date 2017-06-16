import $ivy.`io.circe::circe-yaml:0.6.1`

import io.circe.yaml.parser
import io.circe._


val yaml = """cwlVersion: v1.0
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

val yaml2 = """cwlVersion: v1.0
class: Workflow
inputs:
  inp: File
  ex: string

outputs:
  classout:
    type: File
    outputSource: compile/classfile
"""

val yaml3 = """
cwlVersion: v1.0
class: Workflow
inputs:
  inp: File
  ex: string

outputs:
  - id: classout
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

val env = """
cwlVersion: v1.0
class: CommandLineTool
baseCommand: env
requirements:
  EnvVarRequirement:
    envDef:
      HELLO: $(inputs.message)
inputs:
  message: string
outputs: []
"""

val id = """
class: CommandLineTool
doc: "Sort lines using the `sort` command"
cwlVersion: v1.0

inputs:
  - id: reverse
    type: boolean
    inputBinding:
      position: 1
      prefix: "--reverse"
  - id: input
    type: File
    inputBinding:
      position: 2

outputs:
  - id: output
    type: File
    outputBinding:
      glob: output.txt

baseCommand: sort
stdout: output.txt
"""


//val json: Either[ParsingFailure, Json] = parser.parse(yaml)
//val json2: Either[ParsingFailure, Json] = parser.parse(yaml2)
def convertIdsToObjects: Json => Json = {
  def innerConvert(field: String): Json => Json = 
    _.hcursor.downField(field).withFocus{
      case json if (json \\ "id").nonEmpty => 
        
        Json.obj(((json \\ "id").head.asString.get , json))
      case json => json 
    }.top.get

  innerConvert("inputs") andThen innerConvert("outputs")
}
println(
//convertIdsToObjects(parser.parse(yaml3).right.get)
//convertIdsToObjects(parser.parse(id).right.get)
convertIdsToObjects(parser.parse(yaml2).right.get)
)
def print(in: String) = parser.parse(in).foreach{i => println(i.noSpaces)}
//print(id)

//json.foreach{j => println(j.noSpaces)}
