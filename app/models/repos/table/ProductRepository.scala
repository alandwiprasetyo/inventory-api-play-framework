package models.repos.table

import com.byteslounge.slickrepo.meta.Keyed
import com.byteslounge.slickrepo.repository.Repository
import com.google.inject.Inject
import models.entities.table.Product
import play.api.db.slick.DatabaseConfigProvider
import slick.ast.BaseTypedType
import slick.jdbc.JdbcProfile

class ProductRepository @Inject()(dbConfigProvider: DatabaseConfigProvider) extends Repository[Product, Int](dbConfigProvider.get[JdbcProfile].profile) {

  import driver.api._

  val pkType = implicitly[BaseTypedType[Int]]
  val tableQuery = TableQuery[Products]
  type TableType = Products

  class Products(tag: slick.lifted.Tag) extends Table[Product](tag, "products") with Keyed[Int] {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name")

    def sku = column[String]("sku")

    def size = column[String]("size")

    def color = column[String]("color")

    def * = (id.?, name, sku, size, color) <> ((Product.apply _).tupled, Product.unapply)
  }

}
