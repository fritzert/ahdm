package pe.mef.sitfis.seguridad.adapter.config.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Base64;
import java.util.Map;

public class JwtUtils {

  public static long extractExpiration(String jwtToken) {
    try {
      // El JWT tiene formato: header.payload.signature
      String[] parts = jwtToken.split("\\.");
      if (parts.length < 2) {
        throw new IllegalArgumentException("Token inválido");
      }

      // Decodifica el payload (2da parte)
      String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));

      // Parsear a Map con tipo seguro
      ObjectMapper mapper = new ObjectMapper();
      Map<String, Object> payloadMap = mapper.readValue(
          payloadJson,
          new TypeReference<Map<String, Object>>() {
          }
      );

      // Leer el campo "exp"
      Number exp = (Number) payloadMap.get("exp");
      return exp.longValue();

    } catch (Exception e) {
      throw new RuntimeException("Error al extraer expiración del token", e);
    }
  }
}
