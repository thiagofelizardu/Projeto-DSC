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
- Sempre gere um token novo em `/api/v1/auth`.

---

## 1) Usuários

### 1.1 Criar usuário
**POST** `/api/v1/usuarios/createUser`
```json
{ "username": "ana@email.com", "password": "123456" }
```

### 1.2 Autenticar (obter JWT)
**POST** `/api/v1/auth`
```json
{ "username": "admin@email.com", "password": "123456" }
```
Resposta: `{ "token": "..." }`

### 1.3 Buscar usuário por ID
**GET** `/api/v1/usuarios/{id}`

### 1.4 Listar usuários (paginação)
**GET** `/api/v1/usuarios?page=1&size=4`

### 1.5 Atualizar senha
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

### 2.1 Criar cliente
**POST** `/api/v1/clientes`
```json
{ "nome": "tody da silva", "cpf": "11259547019" }
```

### 2.2 Listar clientes
**GET** `/api/v1/clientes`

### 2.3 Buscar detalhes do cliente
- Se a API suportar query string:
  **GET** `/api/v1/clientes/detalhes?cpf=11259547019`
- Se exigir body:
  **POST** `/api/v1/clientes/detalhes`
  ```json
  { "cpf": "11259547019" }
  ```

---

## 3) Vagas (ADMIN)

### 3.1 Criar vaga
**POST** `/api/v1/vagas`
```json
{ "codigo": "A-08", "status": "LIVRE" }
```

### 3.2 Buscar vaga por código
**GET** `/api/v1/vagas/{codigo}`

---

## 4) Estacionamento

### 4.1 Check-in
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

### 4.2 Check-out
**GET** `/api/v1/estacionamentos/check-out/{recibo}`

---

## 5) Sequência Recomendada

1. Criar usuário → `/usuarios/createUser`
2. Autenticar → `/auth`
3. Buscar usuário por ID → `/usuarios/{id}`
4. Listar usuários → `/usuarios?page=1&size=4`
5. Atualizar senha → `/usuarios/{id}`
6. Criar cliente → `/clientes`
7. Listar clientes → `/clientes`
8. Detalhes do cliente → `/clientes/detalhes`
9. Criar vaga (ADMIN) → `/vagas`
10. Buscar vaga → `/vagas/{codigo}`
11. Check-in → `/estacionamentos/check-in`
12. Check-out → `/estacionamentos/check-out/{recibo}`

---

## 6) Dicas

- **401/403:** verifique se o token é válido e se o usuário tem a role correta.
- Sempre envie `Content-Type: application/json` quando houver body.
- Corrija parâmetros de paginação (`page=1`, não `page =1`).
