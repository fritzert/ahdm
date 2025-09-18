package pe.mef.sitfis.seguridad.adapter.config;

import static pe.mef.sitfis.seguridad.adapter.inbound.api.util.constantes.PathUtil.GRUPOS_API_NAME;
import static pe.mef.sitfis.seguridad.adapter.inbound.api.util.constantes.PathUtil.GRUPOS_ENDPOINT;
import static pe.mef.sitfis.seguridad.adapter.inbound.api.util.constantes.PathUtil.LISTA_ROL_GRUPO_API_NAME;
import static pe.mef.sitfis.seguridad.adapter.inbound.api.util.constantes.PathUtil.LISTA_ROL_GRUPO_ENDPOINT;
import static pe.mef.sitfis.seguridad.adapter.inbound.api.util.constantes.PathUtil.MENU_API_NAME;
import static pe.mef.sitfis.seguridad.adapter.inbound.api.util.constantes.PathUtil.MENU_ENDPOINT;
import static pe.mef.sitfis.seguridad.adapter.inbound.api.util.constantes.PathUtil.ROLES_API_NAME;
import static pe.mef.sitfis.seguridad.adapter.inbound.api.util.constantes.PathUtil.ROLES_ENDPOINT;
import static pe.mef.sitfis.seguridad.adapter.inbound.api.util.constantes.PathUtil.ROL_GRUPO_API_NAME;
import static pe.mef.sitfis.seguridad.adapter.inbound.api.util.constantes.PathUtil.ROL_GRUPO_ENDPOINT;
import static pe.mef.sitfis.seguridad.adapter.inbound.api.util.constantes.PathUtil.SUBMENU_API_NAME;
import static pe.mef.sitfis.seguridad.adapter.inbound.api.util.constantes.PathUtil.SUBMENU_ENDPOINT;
import static pe.mef.sitfis.seguridad.adapter.inbound.api.util.constantes.PathUtil.TAB_API_NAME;
import static pe.mef.sitfis.seguridad.adapter.inbound.api.util.constantes.PathUtil.TAB_ENDPOINT;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Value("${server.port}")
  String appPort;

  @Bean
  public OpenAPI defineOpenApi() {
    Server server = new Server();
    server.setUrl("http://localhost:" + appPort);
    server.setDescription("Servidor de desarrollo de SITFIS Seguridad");

    Contact contact = new Contact();
    contact.setName("Equipo SITFIS");
    contact.setEmail("sitfis@email.com");

    Info information = new Info()
        .title("SITFIS Seguridad API")
        .version("1.0.0")
        .description("API para la gestión de Seguridad - SITFIS")
        .contact(contact);

    return new OpenAPI().info(information).servers(List.of(server));
  }

  @Bean
  GroupedOpenApi tabApi(@Value("${springdoc.version}") String appVersion) {
    return GroupedOpenApi.builder()
        .displayName(TAB_API_NAME)
        .group(TAB_API_NAME.toLowerCase())
        .pathsToMatch(TAB_ENDPOINT + "/**")
        .addOpenApiCustomizer(openApi -> openApi.info(
            new io.swagger.v3.oas.models.info.Info().title(TAB_API_NAME + " API")
                .version(appVersion)))
        .build();
  }

  @Bean
  GroupedOpenApi menuApi(@Value("${springdoc.version}") String appVersion) {
    return GroupedOpenApi.builder()
        .displayName(MENU_API_NAME)
        .group(MENU_API_NAME.toLowerCase())
        .pathsToMatch(MENU_ENDPOINT + "/**")
        .addOpenApiCustomizer(openApi -> openApi.info(
            new io.swagger.v3.oas.models.info.Info().title(MENU_API_NAME + " API")
                .version(appVersion)))
        .build();
  }

  @Bean
  GroupedOpenApi submenuApi(@Value("${springdoc.version}") String appVersion) {
    return GroupedOpenApi.builder()
        .displayName(SUBMENU_API_NAME)
        .group(SUBMENU_API_NAME.toLowerCase())
        .pathsToMatch(SUBMENU_ENDPOINT + "/**")
        .addOpenApiCustomizer(openApi -> openApi.info(
            new io.swagger.v3.oas.models.info.Info().title(SUBMENU_API_NAME + " API")
                .version(appVersion)))
        .build();
  }

  @Bean
  GroupedOpenApi grupoApi(@Value("${springdoc.version}") String appVersion) {
    return GroupedOpenApi.builder()
        .displayName(GRUPOS_API_NAME)
        .group(GRUPOS_API_NAME.toLowerCase())
        .pathsToMatch(GRUPOS_ENDPOINT + "/**")
        .addOpenApiCustomizer(openApi -> openApi.info(
            new io.swagger.v3.oas.models.info.Info().title(GRUPOS_API_NAME + " API")
                .version(appVersion)))
        .build();
  }

  @Bean
  GroupedOpenApi rolApi(@Value("${springdoc.version}") String appVersion) {
    return GroupedOpenApi.builder()
        .displayName(ROLES_API_NAME)
        .group(ROLES_API_NAME.toLowerCase())
        .pathsToMatch(ROLES_ENDPOINT + "/**")
        .addOpenApiCustomizer(openApi -> openApi.info(
            new io.swagger.v3.oas.models.info.Info().title(ROLES_API_NAME + " API")
                .version(appVersion)))
        .build();
  }

  @Bean
  GroupedOpenApi rolGrupoApi(@Value("${springdoc.version}") String appVersion) {
    return GroupedOpenApi.builder()
        .displayName(ROL_GRUPO_API_NAME)
        .group(ROL_GRUPO_API_NAME.toLowerCase())
        .pathsToMatch(ROL_GRUPO_ENDPOINT + "/**")
        .addOpenApiCustomizer(openApi -> openApi.info(
            new io.swagger.v3.oas.models.info.Info().title(ROL_GRUPO_API_NAME + " API")
                .version(appVersion)))
        .build();
  }

  @Bean
  GroupedOpenApi listaRolGrupoApi(@Value("${springdoc.version}") String appVersion) {
    return GroupedOpenApi.builder()
        .displayName(LISTA_ROL_GRUPO_API_NAME)
        .group(LISTA_ROL_GRUPO_API_NAME.toLowerCase())
        .pathsToMatch(LISTA_ROL_GRUPO_ENDPOINT + "/**")
        .addOpenApiCustomizer(openApi -> openApi.info(
            new io.swagger.v3.oas.models.info.Info().title(LISTA_ROL_GRUPO_API_NAME + " API")
                .version(appVersion)))
        .build();
  }

}
