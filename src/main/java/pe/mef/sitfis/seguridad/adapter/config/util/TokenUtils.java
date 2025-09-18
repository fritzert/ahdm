package pe.mef.sitfis.seguridad.adapter.config.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class TokenUtils {

  private static final String ACCESS_TOKEN = "ACCESS_TOKEN";

  public static String extractTokenFromCookie() {
    RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
    if (requestAttributes instanceof ServletRequestAttributes servletRequestAttributes) {
      HttpServletRequest request = servletRequestAttributes.getRequest();
      if (request.getCookies() != null) {
        for (Cookie cookie : request.getCookies()) {
          if (ACCESS_TOKEN.equals(cookie.getName())) {
            return cookie.getValue();
          }
        }
      }
    }
    return null;
  }

//    public static boolean deleteTokenCookie(HttpServletResponse response) {
//        try {
//            Cookie expiredCookie = new Cookie(ACCESS_TOKEN, null);
//            expiredCookie.setPath("/");
//            expiredCookie.setMaxAge(0);
//            expiredCookie.setHttpOnly(true);
//            expiredCookie.setSecure(true);
//            response.addCookie(expiredCookie);
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }

  public static void deleteTokenCookie() {
    ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    if (attrs != null) {
      HttpServletResponse response = attrs.getResponse();
      if (response != null) {
        Cookie expiredCookie = new Cookie("ACCESS_TOKEN", null);
        expiredCookie.setPath("/");
        expiredCookie.setHttpOnly(true);
        expiredCookie.setSecure(true);
        expiredCookie.setMaxAge(0);
        response.addCookie(expiredCookie);
      }
    }
  }

  public static long obtenerSegundosRestantes(String token, RSAPublicKey publicKey) {
    Claims claims = Jwts.parserBuilder()
        .setSigningKey(publicKey)
        .build()
        .parseClaimsJws(token) // valida firma y estructura
        .getBody();

    long expMillis = claims.getExpiration().getTime();
    long ahora = System.currentTimeMillis();
    long diffMillis = expMillis - ahora;

    return Math.max(diffMillis / 1000, 0); // evita valores negativos
  }

  public static String extraerKid(String token) {
    String[] parts = token.split("\\.");
    if (parts.length != 3) {
      throw new IllegalArgumentException("Token JWT mal formado");
    }

    String headerJson = new String(Base64.getUrlDecoder().decode(parts[0]));

    Pattern pattern = Pattern.compile("\"kid\"\\s*:\\s*\"([^\"]+)\"");
    Matcher matcher = pattern.matcher(headerJson);

    if (matcher.find()) {
      return matcher.group(1);
    }

    throw new IllegalArgumentException("No se encontró 'kid' en el header del JWT");
  }

  public static String generarListaNegraKey(String token) {
    String cleaned = token.trim().replace("Bearer", "").trim();
    return "blacklist:" + DigestUtils.sha256Hex(cleaned);
  }

}
