# 🚗 Auto Escola API – Checkpoint 4

**Disciplina:** SOA e WebServices  
**Professor:** Carlos Eduardo Machado de Oliveira
**Entrega parcial:** 23/05/2026

## 👥 Integrantes do grupo

- João Victor Soave - RM557595
- Maria Alice Freitas Araújo - RM557516
- Pedro Henrique Mendes - RM555332
- Vinicius Bittencourt - RM558909
- Rafael Teófilo Lucena - RM555600

## 📌 Sobre o projeto

Esta API ReST gerencia o cadastro de **instrutores** e **alunos** para uma auto‑escola.  
Ela implementa operações de CRUD (criação, leitura, atualização e exclusão lógica) seguindo as regras de negócio especificadas.

## 🛠️ Tecnologias utilizadas

- Java 25+
- Spring Boot (Web, Data JPA, Validation)
- Banco de dados relacional (H2 em desenvolvimento / PostgreSQL em produção)
- Flyway (migrações de banco)
- Maven

## 📋 Funcionalidades implementadas (2ª parte)

### Usuários

- **Cadastro** – permite cadastrar um novo usuário informando Login, Senha e Perfil (`ADMIN` ou `USER`).
- **Listagem paginada** – lista os usuários ativos do sistema.
- **Busca por ID** – permite consultar os dados de um usuário específico.
- **Atualização** – permite ao administrador alterar as informações do usuário, incluindo Login e Perfil.
- **Alteração de senha** – o `ADMIN` pode alterar a senha de qualquer usuário. O `USER` pode alterar somente a própria senha, mediante validação da senha atual.
- **Exclusão lógica** – o usuário é marcado como inativo, sem remover o registro do banco de dados.

### Alunos

- **Cadastro** – permite cadastrar um aluno com os dados obrigatórios definidos pela API.
- **Listagem paginada** – lista os alunos ativos cadastrados no sistema.
- **Busca por ID** – permite consultar os dados de um aluno específico.
- **Atualização** – permite atualizar as informações disponíveis para alteração.
- **Exclusão lógica** – o aluno é marcado como inativo, mantendo seus dados no banco de dados.

### Instrutores

- **Cadastro** – permite cadastrar um instrutor com Nome, E-mail, Telefone, CNH, Especialidade e Endereço.
- **Listagem paginada** – lista os instrutores ativos cadastrados no sistema.
- **Busca por ID** – permite consultar os dados de um instrutor específico.
- **Atualização** – permite atualizar as informações disponíveis para alteração.
- **Exclusão lógica** – o instrutor é marcado como inativo, sem apagar seus dados do banco.

### Instruções

- **Agendamento** – permite cadastrar uma instrução/aula vinculando um aluno e um instrutor, além da especialidade e data/hora.
- **Cancelamento** – permite cancelar uma instrução cadastrada.

## 🔐 Autenticação

- **Login** – autentica o usuário por Login e Senha.
- **Token JWT** – após uma autenticação válida, a API retorna um token que deve ser utilizado para acessar os endpoints protegidos.
- **Controle de acesso** – as permissões são definidas de acordo com o perfil do usuário (`ADMIN` ou `USER`).

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
| DELETE | `/instrucoes` | Cancelar instrução |
| GET | `/health-check` | Verificar a integridade da API |

## ▶️ Como executar o projeto

1. **Clone o repositório**
   ```bash
   git clone https://github.com/challengelotus/checkpoint3-soa-auto-escola-api.git
