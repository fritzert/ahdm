package pe.mef.sitfis.seguridad.adapter.config.util;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

public class RSAKeyUtil {

  //Convierte claves públicas RSA
  public static RSAPublicKey buildRSAPublicKey(String n, String e) throws Exception {
    byte[] modulusBytes = Base64.getUrlDecoder().decode(n);
    byte[] exponentBytes = Base64.getUrlDecoder().decode(e);

    BigInteger modulus = new BigInteger(1, modulusBytes);
    BigInteger exponent = new BigInteger(1, exponentBytes);

    RSAPublicKeySpec spec = new RSAPublicKeySpec(modulus, exponent);
    return (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(spec);
  }
}
