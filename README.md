# Testes de API - Fake Store API

## GET /products
**Endpoint:** `https://fakestoreapi.com/products`
**Objetivo:** garantir que o endpoint mantém o contrato, integridade dos dados e comportamentos esperados

### Resposta esperada
Status `200` com um array contendo 20 objetos de produto, seguindo o schema observado:

- `id`: Integer
- `title`: String
- `price`: Float
- `description`: String
- `category`: String
- `image`: String (URI)
- `rating`: Object
  - `rate`: Float
  - `count`: Integer

---

## Cenários de Teste
| ID | Cenário | Validações | Status |
|----|---------|-----------|--------|
| C01 | Validar status e estrutura básica | • Status `200`<br>• Response é um array<br>• Array não vazio (sempre 20 produtos) | PASS |
| C02 | Validar schema dos produtos | Para cada item do array:<br>• `id`: Integer<br>• `title`: String<br>• `price`: Float<br>• `description`: String<br>• `category`: String<br>• `image`: String (URI)<br>• `rating.rate`: Float<br>• `rating.count`: Integer | PASS |
| C03 | Validar unicidade de IDs | Extrair todos os `id` e garantir que não há duplicatas na listagem | PASS |
| C04 | Validar headers da resposta | • `Content-Type: application/json` (A) <br>• Verificar vazamento de dados sensíveis nos headers (B)<br>•  | <br>A- PASS<br> <br>B- FAIL +  Documentado no Relatório<br>|
| C05 | Validar parâmetro `limit` | • Retorno deve ser igual ao valor de `limit` enviado<br>• Limites válidos: `1` a `20`(A) <br> <br>• Limites inválidos: `0`, `-1`, `abc` (B) | A - PASS + B - PASS + Documentado no Relatório<br>|
| C06 | Validar ordenação (`sort=asc`/`desc`) | Validar se a resposta está ordenada por `id` conforme o parâmetro enviado | PASS |

---

## GET /products/{id}
**Endpoint:** `https://fakestoreapi.com/products`

### Resposta esperada
Status `200` com objeto de id equivalente ao solicitado contendo no body o produto encontrado, seguindo o schema observado:

- `id`: Integer
- `title`: String
- `price`: Float
- `description`: String
- `category`: String
- `image`: String (URI)
- `rating`: Object
  - `rate`: Float
  - `count`: Integer

---
## POST /products
**Endpoint:** `https://fakestoreapi.com/products`

### Resposta esperada
Status `201` contendo no body o produto equivalente ao criado, seguindo o schema observado:

- `id`: Integer
- `title`: String
- `price`: Float
- `description`: String
- `category`: String
- `image`: String (URI)
- `rating`: Object
  - `rate`: Float
  - `count`: Integer

## Cenários de Teste
| ID | Cenário | Validações | Status |
|----|---------|-----------|--------|
| C12 | Produto criado com sucesso  | • Status `200`<br>• Response body com objeto produto igual ao enviado<br>| PASS |
| C13 | Schema da resposta está correto(campos obrigatórios e tipos corretos) |schema:<br>• `id`: Integer<br>• `title`: String<br>• `price`: Float<br>• `description`: String<br>• `category`: String<br>• `image`: String (URI)<br> | PASS |
| C14 | Body do request com payload parcial/vazio|• Status `200` <br> • Response body com id<br> <br> • Response body com objeto produto igual ao enviado<br>| PASS |
| C15 |Body do request com JSON mal formado | • 400 - Bad request <br>• Retorna HTML de erro | - |
| C16 |Body do request com tipos de dados incorretos  | <br>• Entrada:`2147483648`,`99999999999`,`-2147483648`<br><br>• 200 - OK  <br> | - |
| C17 |Body do request testando limite dos campos do payload  | • Status `200` <br> • Response body com id<br> <br> • Response body com objeto produto igual ao enviado<br>| - |
| C18 |Body do request com SQL injection  | • Status `200` <br> • Response body com id<br> <br> • Response body com objeto produto igual ao enviado<br>| - |


---

## Achados do Relatório de Testes

### Schema do response não documentado corretamente

O schema documentado para `GET /products` e `GET /products/{id}` **não inclui** o campo `rating`, mas a resposta real sempre retorna esse objeto aninhado (`rating.rate`, `rating.count`).

**Impacto:** Pode causar quebra de contrato para consumidores que fazem validação estrita de schema (schema validation) baseada apenas na documentação oficial.

| | Documentado | Retorno real |
|---|---|---|
| GET /products | *(sem campo `rating`)* | Inclui `rating: { rate, count }` |
| GET /products/{id} | *(sem campo `rating`)* | Inclui `rating: { rate, count }` |

---

### Parâmetro `limit` sem validação de entrada

A API **não trata valores inválidos de `limit` com status code apropriado (`400 Bad Request`)**. Todos os cenários abaixo retornam `200 OK`, mesmo com valores fora do domínio esperado:

| Valor enviado | Status Code | Itens retornados | Comportamento observado |
|---|---|---|---|
| `limit=0` | 200 | 20 | Parâmetro ignorado — retorna todos os produtos |
| `limit=-1` | 200 | 19 | Valor negativo aplicado literalmente (comportamento tipo `slice(0, -1)`) |
| `limit=abc` | 200 | 20 | Parâmetro não numérico ignorado — retorna valor padrão |

---

### Vazamento de informação via header X-Powered-By

O teste do cenário 04 está falhando porque o endpoint retorna header `X-Powered-By: Express`, expondo publicamente a tecnologia utilizada no backend (Express). Essa prática é desencorajada por boas práticas de segurança (OWASP), pois facilita a um atacante direcionar ataques específicos para vulnerabilidades conhecidas da stack identificada.

![Teste do header falhando devido a exposição relatada no header](image.png)

| | Documentado | Retorno real |
|---|---|---|
| GET /products | *(sem exposição de tecnologia)* | Header `X-Powered-By: Express` presente |

### GET / products/{id} retorna status code 200 para IDs inválidos e inexistentes
O teste do cenário CO10 e C011 para `GET /products/{id}` retorna `HTTP 200 OK` quando recebe um ID inválido. 

Para uma requisição com identificador inexistente ou inválido, o comportamento esperado seria retornar um status code indicando que o recurso não foi encontrado ou que a requisição é inválida, como `404 Not Found`(inexistente = `50`) ou `400 Bad Request`( inválido = `abc`, `-1`, ` 1`), dependendo da regra definida pela API.

O retorno atual com `200 OK` gera ambiguidade, pois indica sucesso na operação mesmo quando nenhum produto foi localizado, dificultando o tratamento correto do cenário pelo consumidor da API.