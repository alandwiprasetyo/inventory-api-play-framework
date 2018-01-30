import java.time.Clock

import com.google.inject.{AbstractModule, Provides}
import models.entities.table.Stock
import models.repos.table.StockRepository
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.test._
import org.specs2.execute.Results
import org.specs2.matcher.Matchers
import org.specs2.mock.Mockito
import play.api.libs.json.{JsNumber, JsObject, JsString}
import slick.dbio.DBIOAction
import play.api.libs.concurrent.Execution.Implicits._
import slick.SlickException

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by alandwiprasetyo on 1/30/18.
  */
class StockSpec extends PlaySpecification with Results with Matchers with Mockito {
  sequential

  val daoMock = mock[StockRepository]

  val application = new GuiceApplicationBuilder().overrides(new AbstractModule {
    override def configure() = {
      bind(classOf[Clock]).toInstance(Clock.systemDefaultZone)
    }

    @Provides
    def stocksDAO: StockRepository = daoMock
  }).build

  "Routes" should {

    "send 404 on a bad request" in {
      route(application, FakeRequest(GET, "/stocksss")).map(status(_)) shouldEqual Some(NOT_FOUND)
    }

    "send 204 when there isn't a /stocks/1" in {
      daoMock.findOne(0).returns(DBIOAction.from(Future {
        None
      }))
      route(application, FakeRequest(GET, "/stocks/0")).map(
        status(_)) shouldEqual Some(NO_CONTENT)
    }

    "send 200 when there is a /stocks/1" in {
      daoMock.findOne(1).returns(DBIOAction.from(Future {
        Some(Stock(Some(1),1, 5, 5, 10000, 12000, "kwitansi-2313", "note-12313"))
      }))
      route(application, FakeRequest(GET, "/stocks/1")).map(
        status(_)) shouldEqual Some(OK)
    }

    "send 415 when post to create a stock without json type" in {
      route(application, FakeRequest(POST, "/stocks/add")).map(
        status(_)) shouldEqual Some(UNSUPPORTED_MEDIA_TYPE)
    }

    "send 400 when post to create a stock with empty json" in {
      route(application,
        FakeRequest(POST, "/stocks/add", FakeHeaders(("Content-type", "application/json") :: Nil), JsObject(Seq()))).map(
        status(_)) shouldEqual Some(BAD_REQUEST)
    }
    //
    "send 400 when post to create a stock with wrong json" in {
      route(application,
        FakeRequest(POST, "/stocks/add", FakeHeaders(("Content-type", "application/json") :: Nil), JsObject(Seq("wrong" -> JsString("wrong"))))).map(
        status(_)) shouldEqual Some(BAD_REQUEST)
    }

    "send 500 when post to create a stock with valid json" in {
      val (productId, quantityHand, quantityProgress, purchasePrice, sellingPrice, receipt, note) = (1, 5, 5, 10000, 12000, "kwitansi-2313", "note-12313")
      val stock = Stock(None, productId, quantityHand, quantityProgress, purchasePrice, sellingPrice, receipt, note)
      daoMock.save(stock).returns(DBIOAction.failed(new SlickException("test")))
      route(application,
        FakeRequest(POST, "/stocks/add", FakeHeaders(("Content-type", "application/json") :: Nil),
          JsObject(Seq("product_id" -> JsNumber(productId), "quantity_hand" -> JsNumber(quantityHand), "quantity_progress" -> JsNumber(quantityProgress),
            "purchase_price" -> JsNumber(purchasePrice), "selling_price" -> JsNumber(sellingPrice), "receipt" -> JsString(receipt), "note" -> JsString(note))))).map(
        status(_)) shouldEqual Some(INTERNAL_SERVER_ERROR)
    }

  }

}

