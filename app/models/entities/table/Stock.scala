package models.entities.table

import java.sql.Timestamp
import java.util.Calendar

import com.byteslounge.slickrepo.meta.Entity

case class Stock(override val id: Option[Int], productId: Int, quantityHand: Int, quantityProgress: Int, purchasePrice: Int, sellingPrice: Int, receipt: String, note: String) extends Entity[Stock, Int] {def withId(id: Int): Stock = this.copy(id = Some(id))
}