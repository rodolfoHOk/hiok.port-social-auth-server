# Spring Security OAuth2 Client

## Passo a passo para implementar o login social

### Cadastrar aplicação no github:

        Github / Settings / Developer settings / OAuth Apps / new OAuth App
        Gerar o client secret
        Guardar client_id e client_secret

### Cadastrar aplicação no google:

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

### Cadastrar aplicação no facebook:

        acessar: https://developers.facebook.com/apps/
          -> Criar aplicativo -> Tipo: Consumidor -> Avançar
          Informações básicas -> preencher nome -> Criar aplicativo
          Adicionar produtos ao seu aplicativo -> Login do facebook -> WEB ->
            preencher URL do site -> continuar -> avançar -> avançar -> voltar
          -> Configurações -> Básico
        Guardar o client_id e client_secret

### Configurar o spring (application.properties)

  - Configurar o banco de dados
  - Configurar o security oauth2 client
  - Configurar propriedades customizadas da aplicação

### Vincular as propriedades customizadas da aplicação a uma classe

  - AppProperties.java

### Habilitando CORS

  - CorsConfig.java

### Criar as entidades do banco de dados

  - User.java
  - AuthProvider.java

### Criar o repositório de usuário com Spring Data JPA

  - UserRepository.java

### Configuração da Segurança

#### Entendendo o fluxo de login do OAuth2 Client

  1. O fluxo de login do OAuth2 será iniciado pelo cliente front-end enviando o usuário para o endpoint http://localhost:8080/oauth2/authorize/{provider}?redirect_uri=<redirect_uri_after_login>.
  O parâmetro de caminho provider é um dos google, facebook, ou github. o redirect_uri é o URI para o qual o usuário será redirecionado assim que a autenticação com o provedor OAuth2 for bem-sucedida. Isso é diferente do redirectUri do OAuth2.

  2. Ao receber a solicitação de autorização, o cliente OAuth2 do Spring Security redirecionará o usuário para o AuthorizationUrl do provedor (provider).
  Todo o estado relacionado à solicitação de autorização é salvo usando o authorizationRequestRepository especificado na configuração de segurança (SecurityConfig).
  O usuário agora concede/nega permissão para seu aplicativo na página do provedor. Se o usuário conceder permissão para o aplicativo, o provedor redirecionará o usuário para o URL de retorno de chamada http://localhost:8080/oauth2/callback/{provider} com um código de autorização. Se o usuário negar a permissão, ele será redirecionado para o mesmo callbackUrl, mas com um error.

  3. Se o retorno de chamada OAuth2 resultar em um erro, a segurança do Spring invocará o oAuth2AuthenticationFailureHandler especificado na configuração de segurança.

  4. Se o retorno de chamada OAuth2 for bem-sucedido e contiver o código de autorização, o Spring Security trocará o authorization_code para um access_token e invocar o customOAuth2UserService especificado na configuração de segurança.

  5. O customOAuth2UserService recupera os detalhes do usuário autenticado e cria uma nova entrada no banco de dados ou atualiza a entrada existente com o mesmo e-mail.

  6. Finalmente, o oAuth2AuthenticationSuccessHandler é invocado. Ele cria um token de autenticação JWT para o usuário e envia o usuário para o redirect_uri junto com o token JWT em uma query string. 

#### Implementar um Oauth2 Authorization Request Repository Customizado

  - Configurar o Authorization Request Repository para armazenar os parâmetros state e redirect_uri em um cookie de curta duração: CustomOauth2AuthorizationRequestRepository.java

#### Implementar Mapeamentos OAuth2 User Info para Google, Facebook e Github

  - Cada provedor OAuth2 retorna uma resposta JSON diferente quando buscamos os detalhes do usuário autenticado. O Spring Security analisa a resposta na forma de um Map genérico de pares chave-valor. Mapearemos os pares chave valor para obter os dados do usuário que precisamos para cada um dos provedores.

#### Implementar um OAuth2 User Service Customizado

  - Esse método é chamado depois que um token de acesso é obtido do provedor OAuth2. 
  Nesse método, primeiro buscamos os detalhes do usuário no provedor OAuth2. Se já existe um usuário com o mesmo e-mail em nosso banco de dados, atualizamos seus dados, caso contrário, registramos um novo usuário. CustomOAuth2UserService.java

#### Implementar o OAuth2 Authentication Success Handler

  - Na autenticação bem-sucedida, o Spring invoca o método onAuthenticationSuccess() com o OAuth2AuthenticationSuccessHandler configurado no SecurityConfig. Nesse método, realizamos algumas validações, criamos um token de autenticação JWT e redirecionamos o usuário para o redirect_uri especificado pelo cliente com o token JWT adicionado na query string.

#### Implementar o OAuth2 Authentication Failure Handler

  - Em caso de erro durante a autenticação OAuth2, o Spring Security invoca o método onAuthenticationFailure() do OAuth2 authentication failure handler configurado no SecurityConfig. Obs: Ao lançar uma instância de AuthenticationException acionará o OAuth2AuthenticationFailureHandler.

#### Implementar o Security Config

  - A classe SecurityConfig a seguir é o ponto crucial de nossa implementação de segurança. Ele contém configurações para o login social OAuth2.
