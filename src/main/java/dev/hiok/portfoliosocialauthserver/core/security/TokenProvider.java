package dev.hiok.portfoliosocialauthserver.core.security;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.text.ParseException;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import dev.hiok.portfoliosocialauthserver.core.config.AppProperties;
import dev.hiok.portfoliosocialauthserver.core.security.oauth2.exception.TokenProcessingException;

@Service
public class TokenProvider {
  
  @Autowired
  private AppProperties appProperties;

  public String createToken(Authentication authentication) {
    UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
    
    try {
      RSAKey privateKey = getPrivateKeyFromJKS();
      JWSSigner jwsSigner = new RSASSASigner(privateKey);

      Date now = new Date();
      Date expirationDate = new Date(now.getTime() + appProperties.getAuth().getTokenExpiration());
      List<String> authorities = userPrincipal.getAuthorities().stream()
        .map(authority -> authority.getAuthority()).collect(Collectors.toList());
      
      JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
        .subject(userPrincipal.getId().toString())
        .claim("authorities", authorities)
        .issueTime(now)
        .expirationTime(expirationDate)
        .build();

      JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(privateKey.getKeyID()).build();
      
      SignedJWT signedJWT = new SignedJWT(jwsHeader, jwtClaimsSet);
      signedJWT.sign(jwsSigner);

      return signedJWT.serialize();
      
    } catch (JOSEException e) {

      throw new TokenProcessingException("Create token error: " + e.getMessage());
    }
  }

  public String getUserIdFromToken(String token) {
    try {
      SignedJWT signedJWT = SignedJWT.parse(token);
      JWTClaimsSet jwtClaimsSet = signedJWT.getJWTClaimsSet();
      
      return jwtClaimsSet.getSubject();
    } catch (ParseException e) {

      throw new TokenProcessingException("Get user id from token error: " + e.getMessage());
    }
  }

  public boolean validateToken(String authToken) {
    try {
      RSAKey privateKey = getPrivateKeyFromJKS();
      RSAKey publicKey = privateKey.toPublicJWK();
      JWSVerifier jwsVerifier = new RSASSAVerifier(publicKey);

      SignedJWT signedJWT = SignedJWT.parse(authToken); 

      return signedJWT.verify(jwsVerifier);

    } catch (JOSEException | ParseException e) {

      throw new TokenProcessingException("Validate token error: " + e.getMessage());
    }
  }

  public RSAKey getPublicKey() {
    return getPrivateKeyFromJKS().toPublicJWK();
  }

  private RSAKey getPrivateKeyFromJKS() {
    try {
      byte[] jks = Base64.getDecoder().decode(appProperties.getAuth().getJwtBase64Jks());
      char[] jksPassword = appProperties.getAuth().getJwtJksPassword().toCharArray();
      String jksAlias = appProperties.getAuth().getJwtJksAlias();
      KeyStore keyStore = KeyStore.getInstance("JKS");
      keyStore.load(new ByteArrayInputStream(jks), jksPassword);
      return RSAKey.load(keyStore, jksAlias, jksPassword);
    } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException | JOSEException e) {
      throw new TokenProcessingException("Get Private Key error: " + e.getMessage());
    }
  }
  
}
