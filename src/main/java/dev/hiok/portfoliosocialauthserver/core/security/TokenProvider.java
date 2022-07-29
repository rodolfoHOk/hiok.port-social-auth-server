package dev.hiok.portfoliosocialauthserver.core.security;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import dev.hiok.portfoliosocialauthserver.core.config.AppProperties;

@Service
public class TokenProvider {
  
  @Autowired
  private AppProperties appProperties;

  public String createToken(Authentication authentication) {
    UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
    
    try {
      byte[] jks = Base64.getDecoder().decode(appProperties.getAuth().getJwtBase64Jks());
      char[] jksPassword = appProperties.getAuth().getJwtJksPassword().toCharArray();
      KeyStore keyStore = KeyStore.getInstance("JKS");
      keyStore.load(new ByteArrayInputStream(jks), jksPassword);
      JWKSet jwkSet = JWKSet.load(keyStore, null);
      RSAKey rsaJwk = (RSAKey) jwkSet.getKeys().get(0);
      JWSSigner jwsSigner = new RSASSASigner(rsaJwk);

      Date now = new Date();
      Date expirationDate = new Date(now.getTime() + appProperties.getAuth().getTokenExpiration());
      JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
        .subject(Long.toString(userPrincipal.getId()))
        .issueTime(new Date())
        .expirationTime(expirationDate)
        .build();

      JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(rsaJwk.getKeyID()).build();
      
      SignedJWT signedJWT = new SignedJWT(jwsHeader, jwtClaimsSet);
      signedJWT.sign(jwsSigner);

      return signedJWT.serialize();
      
    } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException 
      | JOSEException e) {
        
      e.printStackTrace();
      return "";
    }
  }
  
}
