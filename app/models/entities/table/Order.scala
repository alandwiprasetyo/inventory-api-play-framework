package models.entities.table

import java.sql.Timestamp

import com.byteslounge.slickrepo.meta.Entity

case class Order(override val id: Option[Int], productId: Int, stockId: Int, quantity: Int, sellingPrice: Int, total: Int, note: Option[String] = None) extends Entity[Order, Int] {
  def withId(id: Int): Order = this.copy(id = Some(id))
}