package models.repos.table

import java.sql.Timestamp

import com.byteslounge.slickrepo.meta.Keyed
import com.byteslounge.slickrepo.repository.Repository
import com.google.inject.Inject
import models.entities.table.Order
import play.api.db.slick.DatabaseConfigProvider
import slick.ast.BaseTypedType
import slick.jdbc.JdbcProfile
import slick.sql.SqlProfile.ColumnOption.SqlType

class OrderRepository @Inject()(dbConfigProvider: DatabaseConfigProvider) extends Repository[Order, Int](dbConfigProvider.get[JdbcProfile].profile) {

  import driver.api._

  val pkType = implicitly[BaseTypedType[Int]]
  val tableQuery = TableQuery[Orders]
  type TableType = Orders

  class Orders(tag: slick.lifted.Tag) extends Table[Order](tag, "orders") with Keyed[Int] {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def orderId = column[String]("order_id")

    def productId = column[Int]("product_id")

    def stockId = column[Int]("stock_id")

    def product = foreignKey("product_fk", productId, tableQuery)(_.id, onUpdate = ForeignKeyAction.Restrict, onDelete = ForeignKeyAction.Cascade)

    def stock = foreignKey("product_fk", stockId, tableQuery)(_.id, onUpdate = ForeignKeyAction.Restrict, onDelete = ForeignKeyAction.Cascade)

    def quantity = column[Int]("quantity")

    def total = column[Int]("total")

    def sellingPrice = column[Int]("selling_price")

    def note = column[Option[String]]("note")

    //    def created = column[Option[Timestamp]]("created", SqlType("timestamp not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP"))

    def * = (id.?, productId, stockId, quantity, sellingPrice, total, note) <> ((Order.apply _).tupled, Order.unapply)
  }

}