package pe.mef.sitfis.seguridad.adapter.inbound.api.advice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static pe.mef.sitfis.seguridad.adapter.inbound.api.util.constantes.MensajesExceptionUtil.CREDENCIALES_INCORRECTAS;
import static pe.mef.sitfis.seguridad.adapter.inbound.api.util.constantes.MensajesExceptionUtil.DETALLE_ERRORES;
import static pe.mef.sitfis.seguridad.adapter.inbound.api.util.constantes.MensajesExceptionUtil.ERROR_AUTH_EXTERNO;
import static pe.mef.sitfis.seguridad.adapter.inbound.api.util.constantes.MensajesExceptionUtil.FALTA_PARAMETRO;
import static pe.mef.sitfis.seguridad.adapter.inbound.api.util.constantes.MensajesExceptionUtil.PETICION_INCORRECTA;
import static pe.mef.sitfis.seguridad.adapter.inbound.api.util.constantes.MensajesExceptionUtil.RECURSO_NO_ENCONTRADO;
import static pe.mef.sitfis.seguridad.adapter.inbound.api.util.constantes.MensajesExceptionUtil.TRANSACCION_NO_PERMITIDA;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import pe.mef.sitfis.seguridad.adapter.inbound.api.util.response.ApiErrorResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.util.response.ErrorValidacionResponse;
import pe.mef.sitfis.seguridad.application.exception.BadRequestException;
import pe.mef.sitfis.seguridad.application.exception.BusinessException;
import pe.mef.sitfis.seguridad.application.exception.DatabaseException;
import pe.mef.sitfis.seguridad.application.exception.InvalidInputException;
import pe.mef.sitfis.seguridad.application.exception.NotFoundException;

@RestControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {

  @ResponseStatus(INTERNAL_SERVER_ERROR)
  @ExceptionHandler(BusinessException.class)
  public @ResponseBody ApiErrorResponse
  handleBusinessException(BusinessException ex, WebRequest request) {
    return createHttpErrorInfo(ex.getMessage(), INTERNAL_SERVER_ERROR, request);
  }

  @ResponseStatus(INTERNAL_SERVER_ERROR)
  @ExceptionHandler(DatabaseException.class)
  public @ResponseBody ApiErrorResponse
  handleDatabaseException(DatabaseException ex, WebRequest request) {
    StringWriter exception = new StringWriter();
    ex.printStackTrace(new PrintWriter(exception));
    return createHttpErrorInfo(ex.getMessage(), INTERNAL_SERVER_ERROR, request);
  }

  @ResponseStatus(UNAUTHORIZED)
  @ExceptionHandler(AuthenticationException.class)
  public @ResponseBody ApiErrorResponse
  handleAuthenticationException(AuthenticationException ex, WebRequest request) {
    return createHttpErrorInfo(CREDENCIALES_INCORRECTAS + ex.getMessage(), UNAUTHORIZED,
        request);
  }

  @ResponseStatus(INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  public @ResponseBody ApiErrorResponse
  handleInternalServerError(Exception ex, WebRequest request) {
    return createHttpErrorInfo(ex.getMessage(), INTERNAL_SERVER_ERROR, request);
  }

  @ResponseStatus(INTERNAL_SERVER_ERROR)
  @ExceptionHandler(DataAccessException.class)
  public @ResponseBody ApiErrorResponse
  handleSQLException(DataAccessException ex, WebRequest request) {
    String errorMessage = ex.getMostSpecificCause().getMessage();
        /*String oracleError = "Error en la base de datos";
        Pattern pattern = Pattern.compile("ORA-\\d+:.*?(?=\\n|$)");
        Matcher matcher = pattern.matcher(errorMessage);
        if (matcher.find()) {
            oracleError = matcher.group();
        }*/
    return createHttpErrorInfo(TRANSACCION_NO_PERMITIDA + errorMessage, INTERNAL_SERVER_ERROR,
        request);
  }

  @ResponseStatus(BAD_REQUEST)
  @ExceptionHandler(BadRequestException.class)
  public @ResponseBody ApiErrorResponse
  handleBadRequestExceptions(BadRequestException ex, WebRequest request) {
    return createHttpErrorInfo(PETICION_INCORRECTA + ex.getMessage(), BAD_REQUEST, request);
  }

  @ResponseStatus(NOT_FOUND)
  @ExceptionHandler(NotFoundException.class)
  public @ResponseBody ApiErrorResponse
  handleNotFoundExceptions(NotFoundException ex, WebRequest request) {
    return createHttpErrorInfo(RECURSO_NO_ENCONTRADO + ex.getMessage(), NOT_FOUND, request);
  }

  @ResponseStatus(NOT_FOUND)
  @ExceptionHandler(EntityNotFoundException.class)
  public @ResponseBody ApiErrorResponse
  handleEntityNotFound(EntityNotFoundException ex, WebRequest request) {
    return createHttpErrorInfo(ex.getMessage(), HttpStatus.NOT_FOUND, request);
  }

  @ResponseStatus(CONFLICT)
  @ExceptionHandler(EntityExistsException.class)
  public @ResponseBody ApiErrorResponse
  handleEntityExists(EntityExistsException ex, WebRequest request) {
    return createHttpErrorInfo(ex.getMessage(), HttpStatus.CONFLICT, request);
  }

  @ResponseStatus(UNPROCESSABLE_ENTITY)
  @ExceptionHandler(InvalidInputException.class)
  public @ResponseBody ApiErrorResponse
  handleInvalidInputException(InvalidInputException ex, WebRequest request) {
    return createHttpErrorInfo(ex.getMessage(), UNPROCESSABLE_ENTITY, request);
  }

  @ResponseStatus(BAD_REQUEST)
  @ExceptionHandler(MissingPathVariableException.class)
  public @ResponseBody ApiErrorResponse
  handleMissingPathVariable(MissingPathVariableException ex, WebRequest request) {
    return createHttpErrorInfo(ex.getMessage(), UNPROCESSABLE_ENTITY, request);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public @ResponseBody ApiErrorResponse
  handleTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest request) {
    String tipo =
        ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "desconocido";
    String mensaje = "El parámetro '" + ex.getName() + "' debe ser de tipo " + tipo;
    return createHttpErrorInfo(mensaje, HttpStatus.BAD_REQUEST, request);
  }

  @ResponseStatus(BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public @ResponseBody ApiErrorResponse
  handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
    List<ErrorValidacionResponse> erroresValidacion = new ArrayList<>();
    ex.getBindingResult().getFieldErrors().forEach(error ->
        erroresValidacion.add(
            new ErrorValidacionResponse(error.getField(), error.getDefaultMessage()))
    );
    var ruta = request.getDescription(false);
    return new ApiErrorResponse(BAD_REQUEST.value(), DETALLE_ERRORES, LocalDateTime.now(), ruta,
        erroresValidacion
    );
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MissingServletRequestParameterException.class)
  public @ResponseBody ApiErrorResponse
  handleMissingServletRequestParameter(
      MissingServletRequestParameterException ex, WebRequest request) {
    return createHttpErrorInfo(FALTA_PARAMETRO + ex.getParameterName(),
        HttpStatus.BAD_REQUEST, request);
  }

  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ExceptionHandler(WebClientResponseException.Unauthorized.class)
  public ResponseEntity<String> handleUnauthorizedException(
      WebClientResponseException.Unauthorized ex) {
    log.debug("Error de autenticación con el servicio externo: {}", ex.getMessage());
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ERROR_AUTH_EXTERNO);
  }

  private ApiErrorResponse createHttpErrorInfo(String mensaje, HttpStatus httpStatus,
      WebRequest request) {
    var ruta = request.getDescription(false);
    log.debug("Devolviendo estado HTTP: {} para la ruta: {}, mensaje: {}", httpStatus, ruta,
        mensaje);
    return new ApiErrorResponse(httpStatus, mensaje, ruta);
  }

}

