import com.trueaccord.scalapb.json.{JsonFormat, Parser}

object R {

  val json = """{"cwlVersion":"v1.0","class":"Workflow","inputs":{"inp":"File","ex":"string"},"outputs":{"classout":{"type":"File","outputSource":"compile/classfile"}}}"""

  val json2 = """{"cwlVersion":"v1.0","class":"Workflow","inputs":{"inp":"File","ex":"string"},"outputs":{"classout":{"type":"File","outputSource":"compile/classfile"}},"steps":{"untar":{"run":"tar-param.cwl","in":{"tarfile":"inp","extractfile":"ex"},"out":["example_out"]},"compile":{"run":"arguments.cwl","in":{"src":"untar/example_out"},"out":["classfile"]}}}"""

  val json3 = """{"class":"CommandLineTool","doc":"Sort lines using the `sort` command","cwlVersion":"v1.0","inputs":[{"id":"reverse","type":"boolean","inputBinding":{"position":1,"prefix":"--reverse"}},{"id":"input","type":"File","inputBinding":{"position":2}}],"outputs":[{"id":"output","type":"File","outputBinding":{"glob":"output.txt"}}],"baseCommand":"sort","stdout":"output.txt"}"""


  //val fr :FormatRegistry = JsonFormat.defaultRegistry.registerMessageFormat[Step]

  //val p = new Parser()

  //println(JsonFormat.fromJsonString[cwl.Cwl](json))
  def p(in: String) = println(JsonFormat.fromJsonString[cwl.Workflow](in))

  p(json2)
  p(json3)
  
  import com.trueaccord.scalapb.json.Parser

}
