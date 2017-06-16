package broad 

import io.circe.yaml.parser
import io.circe._


package object cwl {

  //def parseYaml(in: String): 

  def convertIdsToObjects: Json => Json = {
    def innerConvert(field: String): Json => Json = 
      _.hcursor.downField(field).withFocus{
        case json if (json \\ "id").nonEmpty => 
          
          Json.obj(((json \\ "id").head.asString.get , json))
        case json => json 
      }.top.get

    innerConvert("inputs") andThen innerConvert("outputs")
  }
}
