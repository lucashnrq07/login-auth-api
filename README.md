# Login Auth API — Spring Boot + JWT

API de autenticação e autorização construída em **Java 21 + Spring Boot**, com autenticação via **JWT**, criptografia de senha com **BCrypt**, documentação com **Swagger** e deploy realizado no **Render**.

Este projeto foi desenvolvido com o objetivo de aprender, praticar e entender na prática como funciona um fluxo completo de autenticação em uma API REST.

---

## Aprendizados do Projeto

### Spring Security na prática
- Configuração do `SecurityFilterChain`
- Controle de rotas públicas e privadas
- Filtro para interceptar requisições e validar o token

### JWT (JSON Web Token)
- Geração do token no login
- Validação automática nas requisições seguintes
- Extração do usuário a partir do token

### Camadas organizadas
- Uso correto de Controller, Service, Repository e DTO
- Aplicação do padrão de responsabilidade única
- Tratamento de erros e exceções personalizadas

### Persistência com JPA / Hibernate
- Uso de `@Entity`
- Repositórios com Spring Data JPA
- Ajuste de banco local (MySQL) e produção (PostgreSQL)

### Deploy na Nuvem (Render)
- Configuração do ambiente de produção
- Variáveis de ambiente sensíveis
- Ajuste para aceitar porta dinâmica do Render
- Erros comuns e como resolvê-los (401, CORS, Dialect, datasource)

---

## Documentação (Swagger)

A API possui documentação interativa via Swagger:

🔗 **Acesse a documentação completa:**  
https://login-auth-api-862x.onrender.com/swagger-ui/index.html  

---

## Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 4**
- **Spring Security**
- **JWT**
- **Spring Data JPA**
- **BCrypt**
- **MH2 Database**
- **Swagger (OpenAPI)**
- **Maven**

---

## Endpoints essenciais

### Registrar usuário
- **POST** /auth/register

### Login
- **POST** /auth/login
> Retorna um token JWT

### Usuário autenticado
- **GET** /user
requer envio do token:
`Authorization: Bearer SEU_TOKEN_AQUI`

---

## Testes unitários

O projeto inclui testes unitários com:

- JUnit 5
- Mockito

---

## Observações importantes

- O projeto não expõe /user sem autenticação
- O Swagger foi liberado para ambiente de produção
- O deployment exige variáveis de ambiente configuradas corretamente
