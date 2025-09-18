package pe.mef.sitfis.seguridad.adapter.outbound.persona.client;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.ActualizarPersonaRequest;
import pe.mef.sitfis.seguridad.adapter.inbound.api.util.response.SuccessResponse;
import pe.mef.sitfis.seguridad.adapter.outbound.persona.dto.CrearPersonaRequest;
import pe.mef.sitfis.seguridad.adapter.outbound.persona.dto.PersonaComboResponse;
import pe.mef.sitfis.seguridad.adapter.outbound.persona.dto.PersonaResponse;

@Slf4j
@Component
public class PersonaClient {

  private final WebClient webClient;

  public PersonaClient(@Value("${api.services.mesapartes.url}") String baseUrl,
      WebClient.Builder webClientBuilder) {
    this.webClient = webClientBuilder
        .baseUrl(baseUrl)
        .build();
  }

  public List<PersonaResponse> getPersonas() {
    return webClient.get()
        .uri("/personas")
        .retrieve()
        .bodyToFlux(PersonaResponse.class)
        .collectList()
        .block();
  }

  public PersonaResponse obtenerPorId(UUID id) {
    return webClient.get()
        .uri("/personas/{id}", id)
        .retrieve()
        .bodyToMono(
            new ParameterizedTypeReference<SuccessResponse<PersonaResponse>>() {
            })
        .map(SuccessResponse::data)
        .block();
  }

  public List<PersonaResponse> buscarPersonasPorParametros(String nombre, Long tipoDocumentoId,
	      String numeroDocumento) {

	    String nombreParam = (nombre != null) ? nombre.trim() : "";
	    String tipoDocParam = (tipoDocumentoId != null) ? tipoDocumentoId.toString().trim() : "";

	    // Construimos la URL manualmente solo agregando numeroDocumento si corresponde
	    StringBuilder urlBuilder = new StringBuilder(
	        String.format("/personas/por-parametros?nombre=%s&tipoDocumentoId=%s",
	            nombreParam, tipoDocParam)
	    );

	    if (numeroDocumento != null && !numeroDocumento.trim().isEmpty()) {
	        urlBuilder.append("&numeroDocumento=").append(numeroDocumento.trim());
	    }
	    
	    return webClient.get()
	            .uri(urlBuilder.toString())
	            .retrieve()
	            .bodyToMono(
	                new ParameterizedTypeReference<SuccessResponse<List<PersonaResponse>>>() {
	                })
	            .map(SuccessResponse::data)
	            .block();
	}
    
    /*public List<PersonaComboResponse> getBuscarPersonasLike(String nombreUsuPersona) {
        String nombreParam = (nombreUsuPersona != null) ? nombreUsuPersona.trim() : "";

        String url = String.format("%s/personas/buscarPersonaLike?nombre=%s",
                baseUrl, nombreParam);

        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToFlux(PersonaComboResponse.class)
                .collectList()
                .block();
    }*/

  public List<PersonaComboResponse> obtenerPersonasPorNombre(String nombreUsuPersona) {
    String nombreParam = (nombreUsuPersona != null) ? nombreUsuPersona.trim() : "";

    String url = String.format("/personas/por-nombre?nombre=%s", nombreParam);

    return webClient.get()
        .uri(url)
        .retrieve()
        .bodyToMono(
            new ParameterizedTypeReference<SuccessResponse<List<PersonaComboResponse>>>() {
            })
        .map(SuccessResponse::data)
        .block();
  }

  public List<PersonaComboResponse> obtenerPersonasPorIdsConNombre(List<UUID> idpersona) {
    String idParam = idpersona.stream()
        .map(UUID::toString)
        .collect(Collectors.joining(","));

    String url = String.format("/personas/por-ids-nombre?idpersona=%s", idParam);

    return webClient.get()
        .uri(url)
        .retrieve()
        .bodyToMono(
            new ParameterizedTypeReference<SuccessResponse<List<PersonaComboResponse>>>() {
            })
        .map(SuccessResponse::data)
        .block();
  }


  public List<PersonaResponse> obtenerPersonasPorIds(List<UUID> idpersona) {
    String idParam = idpersona.stream()
        .map(UUID::toString)
        .collect(Collectors.joining(","));

    String url = String.format("/personas/por-ids?idpersona=%s", idParam);

    return webClient.get()
        .uri(url)
        .retrieve()
        .bodyToMono(
            new ParameterizedTypeReference<SuccessResponse<List<PersonaResponse>>>() {
            })
        .map(SuccessResponse::data)
        .block();
  }

  public PersonaResponse crearPersona(CrearPersonaRequest request) {
    return webClient.post()
        .uri("/personas")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(request)
        .retrieve()
        .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
            clientResponse -> clientResponse
                .bodyToMono(String.class)
                .map(errorBody -> new RuntimeException("Error al crear persona: " + errorBody))
        )
        .bodyToMono(
            new ParameterizedTypeReference<SuccessResponse<PersonaResponse>>() {
            })
        .map(SuccessResponse::data)
        .block();
  }

  public PersonaResponse actualizarPersona(UUID id, ActualizarPersonaRequest request) {
    var responseBody = webClient.put()
        .uri("/personas/{id}", id)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(request)
        .retrieve()
        .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
            clientResponse -> clientResponse
                .bodyToMono(String.class)
                .map(errorBody -> new RuntimeException("Error al Actualizar persona: " + errorBody))
        )
        .bodyToMono(new ParameterizedTypeReference<SuccessResponse<PersonaResponse>>() {
        })
        .map(SuccessResponse::data)
        .block();

    log.info("Cuerpo de la respuesta: {}", responseBody);
    return responseBody;
  }

  public void eliminarPersona(UUID id) {
    webClient.delete()
        .uri("/personas/{id}", id)
        .retrieve()
        .toBodilessEntity()
        .block();
  }

}
