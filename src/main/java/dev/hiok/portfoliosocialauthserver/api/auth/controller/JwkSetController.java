package dev.hiok.portfoliosocialauthserver.api.auth.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;

import dev.hiok.portfoliosocialauthserver.core.security.TokenProvider;

@RestController
public class JwkSetController {
  
  @Autowired
  private TokenProvider tokenProvider;

  @GetMapping("/.well-known/jwks.json")
  public Map<String, Object> keys() {
    RSAKey publicKey = tokenProvider.getPublicKey();
    JWKSet jwkSet = new JWKSet(publicKey);
    return jwkSet.toJSONObject();
  }

}
