package models.entities.view

import java.sql.Timestamp

import com.byteslounge.slickrepo.meta.Entity

case class SellingReport(override val id: Option[Int], quantity: Int,
                         sellingPrice: Int, purchasePrice: Int, productName: String,
                         total: Int, profit: Int, productSku: String) extends Entity[SellingReport, Int] {def withId(id: Int): SellingReport = this.copy(id = Some(id))

}