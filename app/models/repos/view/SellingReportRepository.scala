package models.repos.view

import java.sql.Timestamp

import com.byteslounge.slickrepo.meta.Keyed
import com.byteslounge.slickrepo.repository.Repository
import com.google.inject.Inject
import models.entities.view.SellingReport
import play.api.db.slick.DatabaseConfigProvider
import slick.ast.BaseTypedType
import slick.jdbc.JdbcProfile
import slick.sql.SqlProfile.ColumnOption.SqlType

import scala.concurrent.Future

class SellingReportRepository @Inject()(dbConfigProvider: DatabaseConfigProvider) extends Repository[SellingReport, Int](dbConfigProvider.get[JdbcProfile].profile) {

  import driver.api._

  val pkType = implicitly[BaseTypedType[Int]]
  val tableQuery = TableQuery[SellingReports]
  type TableType = SellingReports

  class SellingReports(tag: slick.lifted.Tag) extends Table[SellingReport](tag, "selling_reports") with Keyed[Int] {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def quantity = column[Int]("quantity")

    def sellingPrice = column[Int]("selling_price")

    def purchasePrice = column[Int]("purchase_price")

    def total = column[Int]("total")

    def profit = column[Int]("profit")

    def productName = column[String]("product_name")

    def productSku = column[String]("product_sku")

    def * = (id.?, quantity, sellingPrice, purchasePrice, productName, total, profit, productSku) <> ((SellingReport.apply _).tupled, SellingReport.unapply)
  }


}