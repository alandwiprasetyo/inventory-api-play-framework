import java.time.Clock

import com.google.inject.{AbstractModule, Provides}
import models.repos.view.SellingReportRepository
import org.specs2.execute.Results
import org.specs2.matcher.Matchers
import org.specs2.mock.Mockito
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.test.{FakeRequest, PlaySpecification}

/**
  * Created by alandwiprasetyo on 1/30/18.
  */
class SellingReportSpec extends PlaySpecification with Results with Matchers with Mockito {
  sequential

  val daoMock = mock[SellingReportRepository]

  val application = new GuiceApplicationBuilder().overrides(new AbstractModule {
    override def configure() = {
      bind(classOf[Clock]).toInstance(Clock.systemDefaultZone)
    }

    @Provides
    def sellingReportDAO: SellingReportRepository = daoMock
  }).build

  "Routes" should {
    "send 404 on a bad request" in {
      route(application, FakeRequest(GET, "/sellings/reportsss")).map(status(_)) shouldEqual Some(NOT_FOUND)
    }
  }
}