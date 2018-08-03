package models

case class GoogleOauthConfig(
                              apiUrl: String,
                              clientId: String,
                              responseType: String,
                              scope: String,
                              redirectUri: String,
                              clientSecret: String,
                              requestTimeOut: Int,
                              grantType: String,
                              tokenEndpoint: String,
                              tokenInfoEndpoint:String
                            )