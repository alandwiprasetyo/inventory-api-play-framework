package models.repos.table

import java.sql.Timestamp

import com.byteslounge.slickrepo.meta.Keyed
import com.byteslounge.slickrepo.repository.Repository
import com.google.inject.Inject
import models.entities.table.Stock
import play.api.db.slick.DatabaseConfigProvider
import slick.ast.BaseTypedType
import slick.jdbc.JdbcProfile
import slick.sql.SqlProfile.ColumnOption.SqlType

class StockRepository @Inject()(dbConfigProvider: DatabaseConfigProvider) extends Repository[Stock, Int](dbConfigProvider.get[JdbcProfile].profile) {

  import driver.api._

  val pkType = implicitly[BaseTypedType[Int]]
  val tableQuery = TableQuery[Stocks]
  type TableType = Stocks

  class Stocks(tag: slick.lifted.Tag) extends Table[Stock](tag, "stocks") with Keyed[Int] {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def productId = column[Int]("product_id")

    def quantityHand = column[Int]("quantity_hand")

    def quantityProgress = column[Int]("quantity_progress")

    def purchasePrice = column[Int]("purchase_price")

    def sellingPrice = column[Int]("selling_price")

    def receipt = column[String]("receipt")

    def note = column[String]("note")

    //    def created = column[Option[Timestamp]]("created", SqlType("timestamp not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP"))

    def * = (id.?, productId, quantityHand, quantityProgress, purchasePrice, sellingPrice, receipt, note) <> ((Stock.apply _).tupled, Stock.unapply)

    def product = foreignKey("product_fk", productId, tableQuery)(_.id, onUpdate = ForeignKeyAction.Restrict, onDelete = ForeignKeyAction.Cascade)
  }

}