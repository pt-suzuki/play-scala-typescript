import com.google.inject.AbstractModule
import com.typesafe.config.ConfigFactory
import models.GoogleOauthConfig

class Module extends AbstractModule {

  private val config = ConfigFactory.load()

  override def configure() = {
    bind(classOf[GoogleOauthConfig]).toInstance(
      GoogleOauthConfig(
        apiUrl = config.getString("google.oauth.apiUrl"),
        clientId = config.getString("google.oauth.clientId"),
        responseType = config.getString("google.oauth.responseType"),
        scope = config.getString("google.oauth.scope"),
        redirectUri = config.getString("google.oauth.redirectUri"),
        clientSecret = config.getString("google.oauth.clientSecret"),
        requestTimeOut = config.getInt("google.oauth.request.timeout"),
        grantType = config.getString("google.oauth.grant_type"),
        tokenEndpoint = config.getString("google.oauth.token_endpoint"),
        tokenInfoEndpoint =config.getString("google.oauth.token_info_endpoint")
      )
    )
  }

}
