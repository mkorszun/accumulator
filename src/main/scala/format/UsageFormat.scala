package format

import spray.json.DefaultJsonProtocol

object UsageJsonFormat extends DefaultJsonProtocol {
    implicit val usageFormat = jsonFormat1(model.Usage)
}
