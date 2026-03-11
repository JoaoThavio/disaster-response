#  Disaster Response API

> API REST para **coordenação de emergências e desastres** em tempo real — construída com Spring Boot, JWT e WebSocket.

Imagine uma enchente acontecendo agora. Bombeiros precisam registrar ocorrências, a central precisa monitorar tudo ao vivo, e o sistema precisa garantir que nenhuma etapa seja pulada. É exatamente isso que essa API resolve.

---

##  Demo ao vivo

| Recurso | Link |
|---|---|
| 📖 Documentação Swagger | [disaster-response-production.up.railway.app/swagger-ui/index.html](https://disaster-response-production.up.railway.app/swagger-ui/index.html) |
| 🔗 Base URL | `https://disaster-response-production.up.railway.app` |

---

## Funcionalidades

-  **Autenticação JWT** — registro e login com senha criptografada (BCrypt)
-  **Máquina de estados** — ocorrência só pode evoluir em ordem: `ATIVA → EM_ATENDIMENTO → ENCERRADA`
-  **WebSocket em tempo real** — central de monitoramento recebe notificações ao vivo sem precisar atualizar a página
-  **Validações robustas** — Enums impedem valores inválidos, gravidade aceita apenas 1 a 5
-  **Documentação automática** — Swagger/OpenAPI integrado
-  **PostgreSQL na nuvem** — banco de dados em produção via Railway

---

## Tecnologias

| Tecnologia | Uso |
|---|---|
| Java 21 | Linguagem principal |
| Spring Boot 3.5 | Framework base |
| Spring Security + JWT | Autenticação e autorização |
| Spring Data JPA + Hibernate | Persistência de dados |
| Spring WebSocket + STOMP | Comunicação em tempo real |
| PostgreSQL | Banco de dados relacional |
| Lombok | Redução de boilerplate |
| Swagger / SpringDoc | Documentação automática |
| Railway | Deploy em nuvem |

---

## Arquitetura

O projeto segue os princípios de **Domain-Driven Design (DDD)**, separando responsabilidades por domínio:

```
src/main/java/com/github/joao/disaster_response/
├── api/
│   ├── controller/        # Endpoints REST
│   └── exception/         # Tratamento global de erros
├── domain/
│   ├── model/             # Entidades e Enums
│   ├── repository/        # Interfaces de acesso a dados
│   └── service/           # Regras de negócio
└── infrastructure/
    └── security/          # JWT, filtros e configurações
```

---

## Endpoints

### Autenticação

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/auth/registrar` | Registra novo usuário e retorna token JWT |
| `POST` | `/auth/login` | Autentica usuário e retorna token JWT |

### Ocorrências

| Método | Endpoint | Descrição | Auth |
|---|---|---|---|
| `POST` | `/ocorrencias` | Registra nova ocorrência | ✅ |
| `GET` | `/ocorrencias` | Lista todas as ocorrências | ✅ |
| `GET` | `/ocorrencias/{id}` | Busca ocorrência por ID | ✅ |
| `PUT` | `/ocorrencias/{id}/status` | Atualiza status (máquina de estados) | ✅ |

---

## Como rodar localmente

### Pré-requisitos

- Java 21
- PostgreSQL
- Maven

### Configuração

1. Clone o repositório:
```bash
git clone https://github.com/JoaoThavio/disaster-response.git
cd disaster-response
```

2. Configure o banco no `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/disaster_db
spring.datasource.username=postgres
spring.datasource.password=postgres
```

3. Rode o projeto:
```bash
./mvnw spring-boot:run
```

4. Acesse a documentação:
```
http://localhost:8080/swagger-ui/index.html
```

---

## Autenticação

Todos os endpoints de ocorrências requerem token JWT no header:

```http
Authorization: Bearer <seu_token>
```

**Exemplo de fluxo:**

```bash
# 1. Registrar
POST /auth/registrar
{
  "email": "joao@gmail.com",
  "senha": "123456",
  "role": "ADMIN"
}

# 2. Usar o token retornado nas próximas requisições
Authorization: Bearer eyJhbGci...
```

---

##  WebSocket — Monitoramento em tempo real

Conecte ao WebSocket para receber notificações ao vivo sempre que uma ocorrência for criada ou atualizada:

```javascript
const socket = new SockJS('https://disaster-response-production.up.railway.app/ws');
const client = Stomp.over(socket);

client.connect({}, () => {
  client.subscribe('/topic/ocorrencias', (msg) => {
    const ocorrencia = JSON.parse(msg.body);
    console.log('Nova atualização:', ocorrencia);
  });
});
```

---

##  Máquina de estados

Uma ocorrência segue um fluxo rígido e controlado:

```
ATIVA ──→ EM_ATENDIMENTO ──→ ENCERRADA
```

Tentativas de transição inválida retornam `400 Bad Request` com mensagem explicativa. Por exemplo, não é possível ir de `ATIVA` direto para `ENCERRADA`.

---

##  Tipos de ocorrência

| Tipo | Descrição |
|---|---|
| `ENCHENTE` | Alagamentos e cheias |
| `INCENDIO` | Incêndios em estruturas ou vegetação |
| `DESABAMENTO` | Colapso de edificações |
| `ACIDENTE` | Acidentes em geral |

**Gravidade:** escala de `1` (leve) a `5` (catastrófico)

---

##  Autor

Feito por **João Thavio** — dev apaixonado por construir coisas que importam.

- 💼 [LinkedIn](www.linkedin.com/in/joao-othavio)
- 🐙 [GitHub](https://github.com/JoaoThavio)
