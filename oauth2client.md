# Spring Security OAuth2 Client

## Passo a passo para implementar o login social

1. Cadastrar aplicação no github:

        Github / Settings / Developer settings / OAuth Apps / new OAuth App
        Gerar o client secret
        Guardar client_id e client_secret

2. Cadastrar aplicação no google:

        acessar:
          https://console.developers.google.com/apis/credentials
          -> criar projeto -> preencher nome -> criar
          -> configurar tela de consentimento -> user type: externo -> criar
            Informações do app -> preencher nome, selecionar email suporte,
              preencher email desenvolvedor -> salvar e continuar
            Escopos -> salvar e continuar
            Usuários de teste -> salvar e continuar
            Resumo -> voltar para painel
          -> Credentials -> criar credenciais -> ID do cliente OAuth
            Selecionar tipo e preencher o nome,
            adicionar uri origens javascript (ex: http://localhost),
            adicionar URIs de redirecionamento autorizados
            (ex: http://localhost:3000/auth/callback/google),
            -> Criar
        Guardar o client_id e client_secret

3. Cadastrar aplicação no facebook:

        acessar: https://developers.facebook.com/apps/
          -> Criar aplicativo -> Tipo: Consumidor -> Avançar
          Informações básicas -> preencher nome -> Criar aplicativo
          Adicionar produtos ao seu aplicativo -> Login do facebook -> WEB ->
            preencher URL do site -> continuar -> avançar -> avançar -> voltar
          -> Configurações -> Básico
        Guardar o client_id e client_secret

4. Configurar o spring (application.properties)

  - Configurar o banco de dados
  - Configurar o security oauth2 client
  - Configurar propriedades customizadas da aplicação

5. Vincular as propriedades customizadas da aplicação a uma classe

  - AppProperties.java

6. Habilitando CORS

  - CorsConfig.java

7. Criar as entidades do banco de dados

  - User.java
  - AuthProvider.java

8. Criar o repositório de usuário com Spring Data JPA

  - UserRepository.java

9. Configuração da Segurança

  1. Entendendo o fluxo de login do OAuth2 Client

    - O fluxo de login do OAuth2 será iniciado pelo cliente front-end enviando o usuário para o endpoint http://localhost:8080/oauth2/authorize/{provider}?redirect_uri=<redirect_uri_after_login>.
    O parâmetro de caminho provider é um dos google, facebook, ou github. o redirect_uri é o URI para o qual o usuário será redirecionado assim que a autenticação com o provedor OAuth2 for bem-sucedida. Isso é diferente do redirectUri do OAuth2.

    - Ao receber a solicitação de autorização, o cliente OAuth2 do Spring Security redirecionará o usuário para o AuthorizationUrl do provedor (provider).
    Todo o estado relacionado à solicitação de autorização é salvo usando o authorizationRequestRepository especificado na configuração de segurança (SecurityConfig).
    O usuário agora concede/nega permissão para seu aplicativo na página do provedor. Se o usuário conceder permissão para o aplicativo, o provedor redirecionará o usuário para o URL de retorno de chamada http://localhost:8080/oauth2/callback/{provider} com um código de autorização. Se o usuário negar a permissão, ele será redirecionado para o mesmo callbackUrl, mas com um error.

    - Se o retorno de chamada OAuth2 resultar em um erro, a segurança do Spring invocará o oAuth2AuthenticationFailureHandler especificado na configuração de segurança.

    - Se o retorno de chamada OAuth2 for bem-sucedido e contiver o código de autorização, o Spring Security trocará o authorization_code para um access_token e invocar o customOAuth2UserService especificado na configuração de segurança.

    - O customOAuth2UserService recupera os detalhes do usuário autenticado e cria uma nova entrada no banco de dados ou atualiza a entrada existente com o mesmo e-mail.

    - Finalmente, o oAuth2AuthenticationSuccessHandler é invocado. Ele cria um token de autenticação JWT para o usuário e envia o usuário para o redirect_uri junto com o token JWT em uma query string. 

  2. Oauth2 Authorization Request Repository Customizado

  3. OAuth2 User Service Customizado

  4. Mapeando OAuth2 User Info para Google, Facebook e Github

  5. OAuth2 Authentication Success Handler

  6. OAuth2 Authentication Failure Handler

  7. Security Config
   
