import ammonite.ops._
import org.scalatest.FlatSpec
import io.circe.yaml.parser
import io.circe.Json

class ParseCwl extends FlatSpec {
  val namespace = "cwl"

  it should "parse all cWL" in {
    val p: Path = Path("/home/dan/common-workflow-language/v1.0/v1.0")

    val cwlFiles = ls! p |? (_.ext == "cwl")

    /*
    def f: String => Unit = in => println(JsonFormat.fromJsonString[cwl.Cwl](in))

    def yamlToJson: String => Json = parser.parse(_).right.get

    def idsToNested: Json => Json = identity

    def printJson: Json => String = _.noSpaces

    val pipeline = (read! _) andThen yamlToJson andThen idsToNested andThen printJson andThen f

    cwlFiles map pipeline

    
    8
    import com.trueaccord.scalapb.json.Parser
    */


  }
}
