# 🚗 Auto Escola API – Checkpoint 5

**Disciplina:** SOA e WebServices  
**Professor:** Carlos Eduardo Machado de Oliveira  

## 👥 Integrantes do grupo

- João Victor Soave - RM557595
- Maria Alice Freitas Araújo - RM557516
- Pedro Henrique Mendes - RM555332
- Vinicius Bittencourt - RM558909
- Rafael Teófilo Lucena - RM555600

## 📌 Sobre o projeto

Esta API ReST gerencia **usuários**, **alunos**, **instrutores** e o **agendamento de instruções** (aulas práticas) de uma auto‑escola.  
Ela implementa operações de CRUD (criação, leitura, atualização e exclusão lógica), autenticação com **JWT**, controle de acesso por perfil e as regras de negócio do agendamento.

## 🛠️ Tecnologias utilizadas

- Java 25
- Spring Boot 4.0.5 (Web MVC, Data JPA, Validation, Security)
- MySQL 8+
- Flyway (migrações de banco)
- JWT (`java-jwt` 4.6.0) e BCrypt para senhas
- Springdoc OpenAPI / Swagger UI
- Lombok
- Maven (wrapper `mvnw` incluso)
- JUnit 5, Mockito, AssertJ e Spring Security Test

## 📋 Funcionalidades implementadas

### Usuários

- **Cadastro** – informa Login, Senha (de 6 a 72 caracteres) e Perfil (`ADMIN` ou `USER`; se omitido, assume `USER`).
- **Listagem paginada** – lista os usuários ativos do sistema.
- **Busca por ID** – consulta os dados de um usuário ativo.
- **Atualização** – o administrador altera Login e Perfil.
- **Alteração de senha** – o `ADMIN` pode alterar a senha de qualquer usuário. O `USER` pode alterar somente a própria senha. Em ambos os casos é obrigatório informar a **senha atual**, e a nova senha deve ser diferente da anterior.
- **Exclusão lógica** – o usuário é marcado como inativo, sem remover o registro do banco. Usuário inativo não consegue mais fazer login e tem o token recusado.

> A senha (nem mesmo o hash) nunca é devolvida nas respostas da API.

### Alunos

- **Cadastro** – Nome, E-mail, Telefone, CPF, Matrícula, Tipo de carta e Endereço.
- **Listagem paginada** – lista os alunos ativos, ordenados por nome por padrão.
- **Busca por ID** – consulta os dados de um aluno ativo.
- **Atualização** – permite alterar Nome, E-mail, Telefone, Tipo de carta e Endereço (CPF e matrícula não são alterados).
- **Exclusão lógica** – o aluno é marcado como inativo, mantendo seus dados no banco.

### Instrutores

- **Cadastro** – Nome, E-mail, Telefone, CNH, Especialidade e Endereço.
- **Listagem paginada** – lista os instrutores ativos (acessível também ao perfil `USER`).
- **Busca por ID** – consulta os dados de um instrutor ativo.
- **Atualização** – permite alterar Nome, E-mail, Telefone, Especialidade e Endereço (a CNH não é alterada).
- **Exclusão lógica** – o instrutor é marcado como inativo, sem apagar seus dados.

### Instruções

- **Agendamento** – vincula um aluno a um instrutor em uma data/hora. O instrutor pode ser informado ou escolhido automaticamente (veja as regras abaixo).
- **Cancelamento por ID** – marca a instrução como inativa e libera o horário do instrutor e o limite diário do aluno.

#### Regras de negócio do agendamento

- A auto‑escola funciona de **segunda a sábado**, das **06:00 às 21:00**. A última aula pode começar às 20:00.
- O horário deve ser em **hora inteira** (ex.: `10:00`).
- O agendamento precisa ser feito com no mínimo **30 minutos de antecedência**.
- Cada aluno pode ter **no máximo uma instrução por dia**.
- O instrutor não pode ter duas instruções no mesmo horário.
- Aluno e instrutor precisam estar **ativos**.
- Se o `id_instrutor` não for informado, a **especialidade é obrigatória** e a API escolhe aleatoriamente um instrutor ativo e livre naquele horário. Se não houver nenhum, a requisição é recusada.
- Instruções **canceladas** não bloqueiam a agenda.

Exemplo de requisição (`POST /instrucoes`):

```json
{
  "id_aluno": 1,
  "id_instrutor": 2,
  "especialidade": "CARROS",
  "data_hora": "15/12/2030 - 10:00"
}
```

## 🔐 Autenticação e autorização

- **Login** – `POST /login` com `login` e `senha` retorna um token JWT.
- **Token JWT** – enviado no cabeçalho `Authorization: Bearer <token>`. Expira em **30 minutos**.
- **Usuário administrador inicial** – na primeira inicialização é criado o usuário `admin` (senha definida em `admin.senha`, no `application.properties`).
- **Respostas de erro de autenticação** – token inválido, expirado ou de usuário inativo retorna `401`.

### Permissões por perfil

| Recurso | ADMIN | USER |
|---|:---:|:---:|
| `/usuarios` (cadastro, listagem, busca, atualização, exclusão) | ✅ | ❌ |
| `PATCH /usuarios/{id}/senha` | ✅ (qualquer usuário) | ✅ (apenas a própria) |
| `/alunos` | ✅ | ❌ |
| `GET /instrutores` (listagem) | ✅ | ✅ |
| Demais rotas de `/instrutores` | ✅ | ❌ |
| `/instrucoes` | ✅ | ❌ |

Rotas públicas: `POST /login` e a documentação Swagger. Todas as demais, inclusive `/health-check`, exigem token.

## 🔁 Endpoints principais

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/login` | Autenticar usuário e gerar token JWT |
| POST | `/usuarios` | Cadastrar usuário |
| GET | `/usuarios` | Listar usuários |
| GET | `/usuarios/{id}` | Buscar usuário por ID |
| PUT | `/usuarios` | Atualizar dados do usuário |
| PATCH | `/usuarios/{id}/senha` | Alterar senha do usuário |
| DELETE | `/usuarios/{id}` | Desativar usuário |
| POST | `/alunos` | Cadastrar aluno |
| GET | `/alunos` | Listar alunos |
| GET | `/alunos/{id}` | Buscar aluno por ID |
| PUT | `/alunos` | Atualizar dados do aluno |
| DELETE | `/alunos/{id}` | Desativar aluno |
| POST | `/instrutores` | Cadastrar instrutor |
| GET | `/instrutores` | Listar instrutores |
| GET | `/instrutores/{id}` | Buscar instrutor por ID |
| PUT | `/instrutores` | Atualizar dados do instrutor |
| DELETE | `/instrutores/{id}` | Desativar instrutor |
| POST | `/instrucoes` | Agendar instrução |
| DELETE | `/instrucoes/{id}` | Cancelar instrução |
| GET | `/health-check` | Verificar a integridade da API |

Exemplo de alteração de senha (`PATCH /usuarios/{id}/senha`). O `id` vem da URL, não do corpo:

```json
{
  "senhaAtual": "senhaAntiga123",
  "senhaNova": "senhaNova456"
}
```

### Valores aceitos

- **Perfil:** `USER`, `ADMIN`
- **Especialidade (instrutor):** `MOTOS`, `CARROS`, `VANS`, `CAMINHOES`
- **Tipo de carta (aluno):** `CAT_A`, `CAT_B`, `CAT_C`, `CAT_D`, `CAT_E`, `CAT_AB`

### Validações dos cadastros

- **CPF** e **CNH:** exatamente 11 dígitos numéricos.
- **Matrícula:** exatamente 5 dígitos.
- **E-mail:** formato válido.
- **Endereço:** logradouro, bairro e cidade obrigatórios; UF com 2 letras maiúsculas; CEP no formato `00000-000`.
- **Senha:** de 6 a 72 caracteres.

### Padrão de respostas de erro

| Status | Quando acontece | Corpo |
|---|---|---|
| `400` | Dados inválidos (Bean Validation) | Lista de `{ "field": "...", "message": "..." }` |
| `400` | Regra de negócio violada, senha atual incorreta, senha igual à anterior, corpo ausente ou inválido | `{ "message": "..." }` |
| `401` | Login inválido ou token inválido/expirado | `{ "message": "..." }` |
| `403` | Perfil sem permissão para a operação | `{ "message": "Acesso negado" }` |
| `404` | Registro inexistente ou inativo | `{ "message": "..." }` |

## 📖 Documentação (Swagger)

Com a aplicação rodando, acesse:

- Swagger UI: <http://localhost:8085/swagger-ui.html>
- OpenAPI (YAML): <http://localhost:8085/v3/api-docs.yaml>

Para autenticar no Swagger, faça `POST /login`, copie o token e use o botão **Authorize** (esquema `bearer-key`). Há também uma cópia offline da especificação em `SwaggerOffLine.yaml`.

## ▶️ Como executar o projeto

### Pré‑requisitos

- JDK 25
- MySQL 8+ rodando em `localhost:3306`
- Não é necessário instalar o Maven (use o `mvnw` do projeto)

### Passo a passo

1. **Clone o repositório**
   ```bash
   git clone https://github.com/challengelotus/checkpoint-soa-auto-escola-api.git
   cd checkpoint-soa-auto-escola-api
   ```

2. **Crie o banco de dados**
   ```sql
   CREATE DATABASE autoescola;
   ```

3. **Confira as configurações** em `src/main/resources/application.properties`:
   ```properties
   server.port=8085
   spring.datasource.url=jdbc:mysql://localhost:3306/autoescola
   spring.datasource.username=root
   spring.datasource.password=fiap
   api.security.token.secret=12345678
   admin.senha=admin
   ```
   Ajuste usuário e senha do MySQL conforme o seu ambiente.

4. **Execute a aplicação**
   ```bash
   ./mvnw spring-boot:run
   ```
   No Windows, use `mvnw.cmd spring-boot:run`.

5. **Teste a API**
   - Faça login com o usuário inicial (`admin` / valor de `admin.senha`):
     ```bash
     curl -X POST http://localhost:8085/login \
       -H "Content-Type: application/json" \
       -d '{"login":"admin","senha":"admin"}'
     ```
   - Use o token retornado nas demais chamadas:
     ```bash
     curl http://localhost:8085/health-check \
       -H "Authorization: Bearer <token>"
     ```

As tabelas são criadas automaticamente pelo Flyway (migrações `V1` a `V9`, em `src/main/resources/db/migration`).

## 🧪 Testes automatizados

```bash
./mvnw test
```

Os testes que acessam o banco (repositórios e controllers) usam o perfil `test`, conectando ao MySQL local no banco `autoescola_test`, que é criado automaticamente na primeira execução (o usuário configurado precisa ter permissão de `CREATE`). Os demais testes rodam sem banco.

| Camada | O que é testado |
|---|---|
| Entidades | `Aluno`, `Instrutor`, `Usuario`, `Instrucao` e `Endereco`: construtores, atualização parcial, exclusão lógica e igualdade por ID |
| DTOs | Regras de Bean Validation dos cadastros e atualizações |
| Validadores de agendamento | Horário de funcionamento, hora inteira, antecedência, limite diário, conflito de instrutor, aluno/instrutor ativos |
| Serviços | `AlunoService`, `InstrutorService`, `InstrucaoService` e `UserService` (inclusive a troca de senha), com Mockito |
| Repositórios | Persistência, filtros de ativos, constraints `unique` e consultas de agenda |
| Segurança | `TokenService` (token válido, expirado, de outro emissor/segredo) e `SecurityFilter` (401 para token inválido ou usuário inativo) |
| Controllers | Status HTTP (`201`, `204`, `400`, `403`, `404`), permissões por perfil e ausência de senha nas respostas |

## 🗂️ Estrutura do projeto

```
src/main/java/br/com/fiap3espg/autoescola
├── controller      # Endpoints REST
├── service         # Regras de aplicação
├── domain          # Entidades, DTOs, repositórios e validadores
│   ├── aluno
│   ├── endereco
│   ├── instrucao   # inclui validacao/ (regras do agendamento)
│   ├── instrutor
│   └── usuario
└── infra
    ├── documentation   # Configuração do Swagger
    ├── exception       # Tratamento global de erros
    └── security        # JWT, filtro, configuração de segurança e admin inicial
```

## ⚠️ Observações

- Os valores de `api.security.token.secret`, `spring.datasource.password` e `admin.senha` do `application.properties` são para **desenvolvimento**. Altere‑os antes de qualquer uso em produção.
- Dois pedidos simultâneos de agendamento para o mesmo instrutor e horário ainda podem passar pela validação ao mesmo tempo, pois não há constraint única no banco.
