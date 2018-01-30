import com.google.inject.{AbstractModule, Inject, Provides}
import java.time.Clock

import models.repos.table.ProductRepository
import play.api.{Configuration, Environment}
import util.DatabaseImplicits

import scala.concurrent.Future


/**
 * This class is a Guice module that tells Guice how to bind several
 * different types. This Guice module is created when the Play
 * application starts.

 * Play will automatically use any class called `Module` that is in
 * the root package. You can create modules in other locations by
 * adding `play.modules.enabled` settings to the `application.conf`
 * configuration file.
 */
class Module (environment: Environment,
                       configuration: Configuration) extends AbstractModule {

  override def configure() = {
    // Use the system clock as the default implementation of Clock
    bind(classOf[Clock]).toInstance(Clock.systemDefaultZone)
    bind(classOf[ProductRepository]).asEagerSingleton()
    bind(classOf[DatabaseImplicits]).asEagerSingleton()
  }

}