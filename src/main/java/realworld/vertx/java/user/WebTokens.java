package realworld.vertx.java.user;

import io.jsonwebtoken.*;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * @author Samer Kanjo
 * @since 0.2.0 4/13/18 5:57 PM
 */
class WebTokens {

  private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

  private static final String HMAC_KEY_BASE64 = "vbwtMup-Ij9GTSYUBGEQZlEzcg59ktD4Lp-d8Lejwxw";
  private static final byte[] HMAC_KEY_BYTES = Base64.getUrlDecoder().decode(HMAC_KEY_BASE64);

  private static final int TOKEN_TTL_HOURS = 24;
  private static final int HMAC_KEY_BYTE_SIZE = 32;
  private static final String ISSUER = "realworld-vertx";

  static String create(String subject) {
    final Instant iat = Instant.now();
    final Instant exp = iat.plus(Duration.ofHours(TOKEN_TTL_HOURS));

    final Key signingKey = new SecretKeySpec(HMAC_KEY_BYTES, SIGNATURE_ALGORITHM.getJcaName());
    final String token = Jwts.builder()
      .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
      .setId(UUID.randomUUID().toString())
      .setIssuedAt(Date.from(iat))
      .setNotBefore(Date.from(iat))
      .setExpiration(Date.from(exp))
      .setSubject(subject)
      .setIssuer(ISSUER)
      .signWith(SIGNATURE_ALGORITHM, signingKey)
      .compact();

    return token;
  }

  static Jws<Claims> parse(String token) {
    final Key signingKey = new SecretKeySpec(HMAC_KEY_BYTES, SIGNATURE_ALGORITHM.getJcaName());

    final Jws<Claims> claims = Jwts.parser()
      .requireIssuer(ISSUER)
      .setSigningKey(signingKey)
      .parseClaimsJws(token);

    return claims;
  }

  static String generateHmacKey() {
    final SecureRandom random = new SecureRandom();
    final byte[] key = new byte[HMAC_KEY_BYTE_SIZE];
    random.nextBytes(key);

    final Base64.Encoder enc = Base64.getUrlEncoder().withoutPadding();
    return enc.encodeToString(key);
  }

}
