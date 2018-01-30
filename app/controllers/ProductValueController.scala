package controllers

import java.io.{BufferedWriter, FileWriter}
import java.text.SimpleDateFormat
import java.util.Calendar
import javax.inject._

import com.github.tototoshi.csv.CSVWriter
import models.entities.view.ProductValue
import models.repos.view.ProductValueRepository
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.json.{Json, Writes}
import play.api.mvc._
import util.DatabaseImplicits

@Singleton
class ProductValueController @Inject()(productValueDAO: ProductValueRepository, dbExecuter: DatabaseImplicits) extends Controller {

  import dbExecuter.executeOperation

  implicit val orderWrites: Writes[ProductValue] = new Writes[ProductValue] {
    def writes(stock: ProductValue) = Json.obj(
      "id" -> stock.id,
      "name" -> stock.productName,
      "sku" -> stock.productSku,
      "available" -> stock.available,
      "avg_purchase_price" -> stock.avgPurchasePrice,
      "total" -> stock.avgPurchasePrice * stock.available
    )
  }

  def index: Action[AnyContent] = Action.async { implicit request =>
    productValueDAO.findAll().map { posts =>
      Ok(Json.toJson(posts))
    }
  }


  def export: Action[AnyContent] = Action.async {
    val cal = Calendar.getInstance()
    val sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val name = String.format("csv/report-product-value-%s.csv", sdf.format(cal.getTime))
    val outputFile = new BufferedWriter(new FileWriter(name))
    val csvWriter = new CSVWriter(outputFile)
    csvWriter.writeAll(List(List("SKU", "Nama Barang", "Jumlah", "Rata-Rata Harga Beli", "Total")))

    productValueDAO.findAll().map { productValues =>
      for (productValue <- productValues) {
        csvWriter.writeAll(List(List(productValue.productSku, productValue.productName, productValue.available, productValue.avgPurchasePrice, productValue.available * productValue.avgPurchasePrice)))
      }
      Ok.sendFile(new java.io.File(name))
    }
  }
}
