package pe.mef.sitfis.seguridad.adapter.config.seguridad;

//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
//import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
//import org.springframework.security.oauth2.core.OAuth2AccessToken;
//import org.springframework.stereotype.Service;

//@Service
public class SecurityHelper {

//  private final OAuth2AuthorizedClientManager authorizedClientManager;
//
//  public SecurityHelper(OAuth2AuthorizedClientManager authorizedClientManager) {
//    this.authorizedClientManager = authorizedClientManager;
//  }
//
//  public String getAccessToken() {
//    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//    if (!(authentication instanceof OAuth2AuthenticationToken oauthToken)) {
//      return null;
//    }
//    OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
//        oauthToken.getAuthorizedClientRegistrationId(), oauthToken.getName());
//
//    return client.getAccessToken().getTokenValue();
//  }
//
//  public OAuth2AccessToken getOAuth2AccessToken() {
//    String clientRegistrationId = "sitfis-service";
//    OAuth2AuthorizeRequest authorizeRequest =
//        OAuth2AuthorizeRequest.withClientRegistrationId(clientRegistrationId)
//            // This principal value is unnecessary, but if you don't give it a value,
//            // it throws an exception.
//            .principal("dummy")
//            .build();
//    OAuth2AuthorizedClient authorizedClient =
//        this.authorizedClientManager.authorize(authorizeRequest);
//    return authorizedClient.getAccessToken();
//  }

}