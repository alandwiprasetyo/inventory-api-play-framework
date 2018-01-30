package controllers

import java.io.{BufferedWriter, FileWriter}
import java.text.SimpleDateFormat
import java.util.Calendar
import javax.inject._

import com.github.tototoshi.csv._
import models.entities.view.SellingReport
import models.repos.view.SellingReportRepository
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.json.{Json, Writes}
import play.api.mvc._
import util.DatabaseImplicits

@Singleton
class SellingReportController @Inject()(sellingsDAO: SellingReportRepository, dbExecuter: DatabaseImplicits) extends Controller {

  import dbExecuter.executeOperation

  implicit val productWrites: Writes[SellingReport] = new Writes[SellingReport] {
    def writes(sellings: SellingReport) = Json.obj(
      "id" -> sellings.id,
      "quantity" -> sellings.quantity,
      "selling_price" -> sellings.sellingPrice,
      "purchase_price" -> sellings.purchasePrice,
      "total" -> sellings.total,
      "profit" -> sellings.profit,
      "product_name" -> sellings.productName,
      "product_sku" -> sellings.productSku
    )
  }


  def index: Action[AnyContent] = Action.async { implicit request =>
    sellingsDAO.findAll().map { posts =>
      Ok(Json.toJson(posts))
    }
  }

  def export: Action[AnyContent] = Action.async {
    val cal = Calendar.getInstance()
    val sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val name = String.format("csv/report-%s.csv", sdf.format(cal.getTime()).toString())
    val outputFile = new BufferedWriter(new FileWriter(name))
    val csvWriter = new CSVWriter(outputFile)
    csvWriter.writeAll(List(List("SKU", "Nama Barang", "Jumlah", "Harga Jual", "Total", "Harga Beli", "Laba")))

    sellingsDAO.findAll().map { sellingReports =>
      for (sellingReport <- sellingReports) {
        csvWriter.writeAll(List(List(sellingReport.productSku.toString, sellingReport.productName.toString, sellingReport.quantity.toString, sellingReport.sellingPrice.toString, sellingReport.quantity * sellingReport.sellingPrice, sellingReport.purchasePrice.toString, sellingReport.profit.toString)))
      }
      Ok.sendFile(new java.io.File(name))
    }
  }
}