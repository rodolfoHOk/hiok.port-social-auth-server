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

7. 
