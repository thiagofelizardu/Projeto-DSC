# Park API

API para gerenciamento de estacionamento, com suporte a usuários, clientes, vagas e controle de check-in/check-out.

# Rode o docker para usaaar o postgres se necessario 

## No caminho `Projeto-DSC\docker do banco`

```
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

## 0) Pré-requisitos
- Aplicação rodando em `http://localhost:9090`.
- Sempre gere um token novo em `/api/v1/auth` obs: por padrão a duração do token esta em 30min.

---

## 1) Usuários

### 1.1 Criar usuário (**público**)
**POST** `/api/v1/usuarios/createUser`
```json
{ "username": "ana@email.com", "password": "123456" }
```

### 1.2 Autenticar (obter JWT) (**público**)  
Tem acesso às portas de acordo com a role do usuário autenticado (ADMIN ou CLIENT).

**POST** `/api/v1/auth`
```json
{ "username": "admin@email.com", "password": "123456" }
```
Resposta: `{ "token": "..." }`

### 1.3 Buscar usuário por ID (**ADMIN**)
**GET** `/api/v1/usuarios/{id}`

### 1.4 Listar usuários (paginação) (**ADMIN**)
**GET** `/api/v1/usuarios?page=1&size=4`

### 1.5 Atualizar senha (**CLIENT** pode alterar apenas a própria senha)
**PATCH** `/api/v1/usuarios/{id}`
```json
{
  "senhaAtual": "123456",
  "novaSenha": "1234567891",
  "confirmaSenha": "1234567891"
}
```

---

## 2) Clientes

### 2.1 Criar cliente (**ADMIN**)
**POST** `/api/v1/clientes`
```json
{ "nome": "tody da silva", "cpf": "11259547019" }
```

### 2.2 Listar clientes (**ADMIN**)
**GET** `/api/v1/clientes`

### 2.3 Buscar detalhes do cliente  
- **ADMIN**: pode buscar detalhes de qualquer cliente.  
- **CLIENT**: pode buscar apenas os próprios dados (pelo CPF).  

- Se a API suportar query string:
  **GET** `/api/v1/clientes/detalhes?cpf=11259547019`
- Se exigir body:
  **POST** `/api/v1/clientes/detalhes`
  ```json
  { "cpf": "11259547019" }
  ```

---

## 3) Vagas

### 3.1 Criar vaga (**ADMIN**)
**POST** `/api/v1/vagas`
```json
{ "codigo": "A-08", "status": "LIVRE" }
```

### 3.2 Buscar vaga por código (**CLIENT** e **ADMIN**)
**GET** `/api/v1/vagas/{codigo}`

---

## 4) Estacionamento

### 4.1 Check-in (**CLIENT**)
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
Resposta: retorna um **recibo** (ex.: `20250929-193541`).

### 4.2 Check-out (**CLIENT**)
**GET** `/api/v1/estacionamentos/check-out/{recibo}`

---

## 5) Sequência Recomendada

1. Criar usuário → `/usuarios/createUser` (**público**)  
2. Autenticar → `/auth` (**público**)  
3. Buscar usuário por ID → `/usuarios/{id}` (**ADMIN**)  
4. Listar usuários → `/usuarios?page=1&size=4` (**ADMIN**)  
5. Atualizar senha → `/usuarios/{id}` (**CLIENT**)  
6. Criar cliente → `/clientes` (**ADMIN**)  
7. Listar clientes → `/clientes` (**ADMIN**)  
8. Detalhes do cliente → `/clientes/detalhes` (**ADMIN** qualquer cliente / **CLIENT** apenas próprio CPF)  
9. Criar vaga → `/vagas` (**ADMIN**)  
10. Buscar vaga → `/vagas/{codigo}` (**CLIENT** e **ADMIN**)  
11. Check-in → `/estacionamentos/check-in` (**CLIENT**)  
12. Check-out → `/estacionamentos/check-out/{recibo}` (**CLIENT**)  

---

## 6) Dicas

- **401/403:** verifique se o token é válido e se o usuário tem a role correta.
- Sempre envie `Content-Type: application/json` quando houver body.
- Corrija parâmetros de paginação (`page=1`, não `page =1`).
