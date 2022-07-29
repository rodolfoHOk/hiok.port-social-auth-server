# JKS - JSON Web Key Set

## Keytool

> Ferramenta para gerar par de chaves para usar no token assimétrico

### Gerando um arquivo JKS com um par de chaves

  - Command line:

        keytool -genkeypair -alias myportfolio -keyalg RSA -keypass 123456 -keystore myportfolio.jks -storepass 123456 -validity 3650

### Listando as entradas de um arquivo JKS

  - Command line:
  
        keytool -list -keystore algafood.jks

### Gerando o certificado

  - Command line: 
  
        keytool -export -rfc -alias myportfolio-keystore myportfolio.jks -file myportfolio-cert.pem

### Gerando a chave pública

  - Command line: 
  
        openssl x509 -pubkey -noout -in myportfolio-cert.pem > myportfolio-pkey.pem

### Pegando a chave pública

 - Command line: 
  
        cat myportfolio-pkey.pem

### Pegando o conteúdo do JKS em base64

  - Command line:

        cat myportfolio.jks | base64
