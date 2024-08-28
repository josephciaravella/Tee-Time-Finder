package joseph.ciaravella.TeeTimeFinder.utilities;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SecureDigestAlgorithm;

import java.util.Base64;
import java.util.Date;

@Component
public class TokenProvider {
  @Value("${secretKey}")
  private String secretKey;

  @PostConstruct
  protected void init() {
    secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
  }

  public String generateToken(String username) { //This should be emails passed in
    Date now = new Date();
    Date expiry = new Date(now.getTime() + (3600000 * 12)); //Set to 12 hours of validity

    return Jwts.builder()
      .subject(username)
      .issuedAt(now)
      .expiration(expiry)
    //   .signWith(secretKey, )
      .signWith(SignatureAlgorithm.HS256, secretKey) //??
    //   .signWith()
      .compact();
  }
}

