import akka.actor._
import model.Usage
import service.MeterService
import spray.routing.SimpleRoutingApp

object Main extends App with SimpleRoutingApp with MeterService {

    implicit val system = ActorSystem("my-system")

    startServer(interface = "0.0.0.0", port = System.getenv("PORT").toInt) {

        import format.UsageJsonFormat._
        import spray.httpx.SprayJsonSupport._

        path("") {
            get {
                complete("OK")
            }
        } ~
            path("meter" / JavaUUID) {
                meterUUID => pathEnd {
                    post {
                        entity(as[Usage]) {
                            usage =>
                                stream_update(meterUUID.toString, usage.value)
                                complete("OK")
                        }
                    }
                }
            }
    }
}
