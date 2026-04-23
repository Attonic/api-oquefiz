# oquefiz

API REST para registro de dailies de colaboradores. Permite que equipes registrem o que fizeram no dia, dificuldades encontradas, obstáculos e próximos passos, tudo com autenticação JWT e controle de acesso por perfil.

---

## Stack

| Camada | Tecnologia |
|---|---|
| Linguagem | Java 21 |
| Framework | Spring Boot 4.0.5 |
| Segurança | Spring Security + JWT (Auth0 java-jwt 4.5.1) |
| Persistência | Spring Data JPA + PostgreSQL |
| Mapeamento | MapStruct 1.6.3 |
| Boilerplate | Lombok |
| Documentação | SpringDoc OpenAPI 3 / Swagger UI |
| Testes | JUnit 5 + Mockito + H2 (em memória) |

---

## Pré-requisitos

- Java 21+
- Maven 3.9+
- PostgreSQL (local ou via Docker)

---

## Configuração

A aplicação lê as seguintes variáveis de ambiente:

| Variável | Padrão                                     | Descrição |
|---|--------------------------------------------|---|
| `DB_URL` | `jdbc:postgresql://localhost:5432/oquefiz` | URL de conexão com o banco |
| `DB_USER` | `postgres`                                 | Usuário do banco |
| `DB_PASSWORD` | `postgres`                                 | Senha do banco |
| `JWT_SECRET` | -                                          | Chave secreta para assinatura do JWT **(obrigatória)** |

Exemplo de exportação:

```bash
export DB_URL=jdbc:postgresql://localhost:5432/oquefiz
export DB_USER=postgres
export DB_PASSWORD=postgres
export JWT_SECRET=minha-chave-secreta-longa
```

---

## Executando

```bash
# Clonar
git clone https://github.com/seu-usuario/oquefiz.git
cd oquefiz

# Rodar
./mvnw spring-boot:run
```

A aplicação sobe em `http://localhost:8080`.

### Swagger UI

```
http://localhost:8080/swagger-ui.html
```

---

## Endpoints

### Autenticação — `/api/auth` (público)

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/api/auth/register` | Cria novo usuário e retorna JWT |
| `POST` | `/api/auth/login` | Autentica e retorna JWT |

**Exemplo de registro:**

```json
{
  "userName": "joao.silva",
  "email": "joao@empresa.com",
  "password": "minhasenha",
  "role": "EMPLOYEE"
}
```

Perfis disponíveis: `ADMIN`, `MANAGER`, `EMPLOYEE`.

**Exemplo de login:**

```json
{
  "Email": "joao@empresa.com",
  "password": "minhasenha"
}
```

**Resposta (ambos):**

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "user": {
    "userId": "...",
    "userName": "joao.silva",
    "email": "joao@empresa.com",
    "role": "EMPLOYEE"
  }
}
```

---

### Colaboradores — `/api/employees` (requer ADMIN)

| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/api/employees` | Lista todos os colaboradores (paginado) |
| `GET` | `/api/employees/ativos` | Lista colaboradores com usuário ativo (paginado) |
| `GET` | `/api/employees/{id}` | Busca colaborador por ID |
| `POST` | `/api/employees` | Cadastra novo colaborador |
| `PUT` | `/api/employees/{id}` | Atualiza dados de um colaborador |

**Exemplo de body (POST/PUT):**

```json
{
  "name": "João Silva",
  "email": "joao@empresa.com",
  "jobTitle": "Dev Backend",
  "birthDate": "1995-05-20",
  "userId": "uuid-do-usuario-opcional"
}
```

---

### Dailies — `/api/dailies` (requer ADMIN)

| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/api/dailies` | Lista todas as dailies (paginado) |
| `GET` | `/api/dailies/{id}` | Busca daily por ID |
| `POST` | `/api/dailies` | Registra uma nova daily |

**Exemplo de body (POST):**

```json
{
  "whatIDid": "Implementei o endpoint de autenticação",
  "difficult": "Configuração do filtro JWT",
  "obstacle": "Falta de documentação da lib",
  "nextSteps": "Escrever testes unitários",
  "dateRegister": "2025-04-23",
  "employeeId": "uuid-do-colaborador"
}
```

---

### Usuários — `/api/user` (requer ADMIN)

| Método | Rota | Descrição |
|---|---|---|
| `GET` | `/api/user` | Lista todos os usuários (paginado) |
| `GET` | `/api/user/{id}` | Busca usuário por ID |

---

## Autenticação

A API usa JWT Bearer Token. Após o login, inclua o token em todas as requisições protegidas:

```
Authorization: Bearer <token>
```

Rotas públicas (sem token necessário):

```
/api/auth/**
/swagger-ui/**
/v3/api-docs/**
```

---

## Testes

```bash
./mvnw test
```

Os testes usam banco H2 em memória — sem necessidade de PostgreSQL rodando.

Cobertura implementada:

- **Unitários** (`@ExtendWith(MockitoExtension.class)`) — lógica dos serviços isolada com Mockito
- **Integração web** (`@WebMvcTest`) — rotas HTTP, status codes e Spring Security
- **Repositório** (`@DataJpaTest`) — queries JPA derivadas contra H2

---

## Estrutura do projeto

```
src/main/java/io/github/
├── exception/                    # Exceções e GlobalExceptionHandler
│   └── handler/
└── oquefiz/
    ├── OquefizApplication.java
    ├── config/
    │   ├── OpenApiConfig.java
    │   └── security/             # JWT filter, TokenService, SecurityConfig
    ├── controller/               # Auth, User, Employee, Daily
    ├── dto/
    │   ├── Request/              # DTOs de entrada (records com validação)
    │   └── Response/             # DTOs de saída
    ├── mapper/                   # MapStruct
    ├── model/                    # Entidades JPA + enum UserRole
    ├── repository/               # Interfaces Spring Data JPA
    └── service/                  # Interfaces + implementações
        └── impl/
```

---

## Licença

Distribuído sob os termos do arquivo [LICENSE](LICENSE).
