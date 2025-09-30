
# Park API

API REST para gerenciamento de estacionamento, com suporte a **usuários**, **clientes**, **vagas** e **controle de check-in/check-out**.

---

## Docker para Banco de Dados (PostgreSQL)
(Recomendado), inicie o banco de dados com Docker:

```bash
cd Projeto-DSC/docker do banco
docker-compose up
```

---

## Base URL

```
http://localhost:9090
```

## Headers Padrão

```
Authorization: Bearer <token>
Content-Type: application/json
```

---

## Pré-requisitos

- A aplicação deve estar rodando em `http://localhost:9090`.
- Gere um novo token via login em `/api/v1/auth`.
- Duração padrão do token: 30 minutos.

---

## 1. Usuários

### 1.1 Criar usuário (acesso: **público**)

**POST** `/api/v1/usuarios/createUser`

```json
{
  "username": "ana@email.com",
  "password": "123456"
}
```

### 1.2 Autenticar (acesso: **público**)

**POST** `/api/v1/auth`

```json
{
  "username": "admin@email.com",
  "password": "123456"
}
```

**Resposta:**

```json
{
  "token": "..."
}
```

### 1.3 Buscar usuário por ID (acesso: **ADMIN** ou **CLIENT** — apenas o próprio ID)

**GET** `/api/v1/usuarios/{id}`

### 1.4 Listar usuários com paginação (acesso: **ADMIN**)

**GET** `/api/v1/usuarios?page=1&size=4`

### 1.5 Atualizar senha (acesso: **CLIENT** — apenas o próprio ID)

**PATCH** `/api/v1/usuarios/{id}`

```json
{
  "senhaAtual": "123456",
  "novaSenha": "1234567891",
  "confirmaSenha": "1234567891"
}
```

---

## 2. Clientes

### 2.1 Criar cliente (acesso: **CLIENT**)

**POST** `/api/v1/clientes`

```json
{
  "nome": "tody da silva",
  "cpf": "11259547019"
}
```

### 2.2 Listar todos os clientes (acesso: **ADMIN**)

**GET** `/api/v1/clientes`

### 2.3 Buscar detalhes do cliente

**GET** `/api/v1/clientes/detalhes`  
- Acesso: **CLIENT** (retorna seus próprios dados)

---

## 3. Vagas

### 3.1 Criar vaga (acesso: **ADMIN**)

**POST** `/api/v1/vagas`

```json
{
  "codigo": "A-08",
  "status": "LIVRE"
}
```

### 3.2 Buscar vaga por código (acesso: **ADMIN** e **CLIENT**)

**GET** `/api/v1/vagas/{codigo}`

---

## 4. Estacionamento

### 4.1 Check-in (acesso: **CLIENT**)

**POST** `/api/v1/estacionamentos/check-in`

```json
{
  "clienteCpf": "11259547019",
  "placa": "ASC-1234",
  "marca": "FIAT",
  "modelo": "Siena",
  "cor": "azul"
}
```

**Resposta:**

```json
{
  "recibo": "20250929-193541",
  ...
}
```

### 4.2 Check-out (acesso: **ADMIN**)

**GET** `/api/v1/estacionamentos/check-out/{recibo}`

---

## 5. Sequência Recomendada

1. Criar usuário → `/usuarios/createUser` (público)  
2. Autenticar → `/auth` (público)  
3. Criar cliente → `/clientes` (CLIENT)  
4. Ver detalhes do cliente → `/clientes/detalhes` (CLIENT)  
5. Criar vaga → `/vagas` (ADMIN)  
6. Buscar vaga por código → `/vagas/{codigo}` (ADMIN ou CLIENT)  
7. Check-in → `/estacionamentos/check-in` (CLIENT)  
8. Check-out → `/estacionamentos/check-out/{recibo}` (ADMIN)  
9. Buscar usuário por ID → `/usuarios/{id}` (ADMIN ou CLIENT)  
10. Atualizar senha → `/usuarios/{id}` (CLIENT — próprio ID)

---

## 6. Dicas

- Se ocorrer erro 401 ou 403:
  - Verifique se o token é válido.
  - Verifique se a role do usuário é compatível com a rota acessada.
- Sempre envie o header:
  ```
  Content-Type: application/json
  ```
- Evite espaços inválidos em parâmetros de URL:
  - Correto: `page=1`
  - Errado: `page =1`

---

## Exemplo de fluxo completo (CLIENT)

1. Criar usuário (POST `/api/v1/usuarios/createUser`)
2. Autenticar (POST `/api/v1/auth`) e obter token
3. Criar cliente (POST `/api/v1/clientes`)
4. Realizar check-in (POST `/api/v1/estacionamentos/check-in`)
5. Ver detalhes do cliente (GET `/api/v1/clientes/detalhes`)
6. Realizar check-out (GET `/api/v1/estacionamentos/check-out/{recibo}`)

---

## Teste com token expirado ou inválido

- Faça login e aguarde 30 minutos.
- Tente acessar qualquer rota protegida com o token anterior.
- Resultado esperado: HTTP 401 Unauthorized.

- Ou envie um token inválido manualmente:
```
Authorization: Bearer 123.invalid.token
```

---

## Casos de erro esperados

| Ação                                  | Situação                                      | Esperado          |
|---------------------------------------|-----------------------------------------------|-------------------|
| Criar usuário com email já usado      | `username` já cadastrado                      | 400 Bad Request   |
| Autenticar com senha errada           | Senha incorreta                               | 400 Bad Request   |
| Criar cliente sem token               | Não enviar o header Authorization             | 403 Forbidden     |
| Acessar rota ADMIN com token de CLIENT| Ex: `/api/v1/vagas`, `/api/v1/usuarios`       | 403 Forbidden     |
