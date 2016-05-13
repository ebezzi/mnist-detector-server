package controllers

import java.io.{DataInputStream, FileInputStream}
import javax.inject._
import org.deeplearning4j.eval.Evaluation
import org.deeplearning4j.nn.conf.MultiLayerConfiguration
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork
import org.nd4j.linalg.api.ndarray.{BaseNDArray, INDArray}
import org.nd4j.linalg.cpu.NDArray
import org.nd4j.linalg.dataset.DataSet
import org.nd4j.linalg.factory.Nd4j
import java.io.File

import play.api._
import play.api.libs.json.Json
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject() extends Controller {

  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action {
    Ok(views.html.index())
  }

  def detect() = Action { req =>

    val input = req.body.asJson.get.as[Seq[Double]].toArray.map(255-_)

    val dataDir = "conf/resources/"

    val conf = scala.io.Source.fromFile(new File(dataDir + "conf.json")).mkString

    //Load network configuration from disk:
    val confFromJson = MultiLayerConfiguration.fromJson((conf))

    //Load parameters from disk:;
    val dis = new DataInputStream(new FileInputStream(dataDir + "coefficients.bin"))
    val newParams = Nd4j.read(dis)

    //Create a MultiLayerNetwork from the saved configuration and parameters
    val savedNetwork = new MultiLayerNetwork(confFromJson)
    savedNetwork.init()
    savedNetwork.setParameters(newParams)

    val xs = new NDArray(input, Array(1, 784), 'c')

    val output = savedNetwork.output(xs)

    val v = (0 to 9).map(output.getInt(_)).toList

    val res = Json.obj("results" -> Json.arr(
      v, v
    ))

    Ok(res)


  }

}
