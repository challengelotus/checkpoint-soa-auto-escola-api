# 🚗 Auto Escola API – Checkpoint 3

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

## 📋 Funcionalidades implementadas (1ª parte)

### Instrutores
- **Cadastro** – campos obrigatórios: Nome, E‑mail, Telefone, CNH, Especialidade (Motos/Carros/Vans/Caminhões), Endereço completo (logradouro, número, complemento, bairro, cidade, UF, CEP) – apenas número e complemento são opcionais.
- **Listagem paginada** – 10 registros por página, ordenada por nome crescente. Exibe: Nome, E‑mail, CNH, Especialidade.
- **Atualização** – permite alterar Nome, Telefone e Endereço. Não permite alterar E‑mail, CNH e Especialidade.
- **Exclusão lógica** – o instrutor é marcado como `inativo` no sistema, seus dados não são apagados.

### Alunos
- **Cadastro** – campos obrigatórios: Nome, E‑mail, Telefone, CPF, Endereço completo (logradouro, número, complemento, bairro, cidade, UF, CEP) – apenas número e complemento são opcionais.
- **Listagem paginada** – 10 registros por página, ordenada por nome crescente. Exibe: Nome, E‑mail, CPF.
- **Atualização** – permite alterar Nome, Telefone e Endereço. Não permite alterar E‑mail e CPF.
- **Exclusão** – remove o registro do banco de dados (ou lógica, conforme evolução futura).

## 🔁 Endpoints principais (exemplo)

| Método | Endpoint                     | Descrição                         |
|--------|------------------------------|-----------------------------------|
| POST   | `/api/instrutores`           | Cadastrar instrutor               |
| GET    | `/api/instrutores`           | Listar instrutores (paginado)     |
| PUT    | `/api/instrutores/{id}`      | Atualizar dados do instrutor      |
| DELETE | `/api/instrutores/{id}`      | Excluir (inativar) instrutor      |
| POST   | `/api/alunos`                | Cadastrar aluno                   |
| GET    | `/api/alunos`                | Listar alunos (paginado)          |
| PUT    | `/api/alunos/{id}`           | Atualizar dados do aluno          |
| DELETE | `/api/alunos/{id}`           | Excluir aluno                     |

## ▶️ Como executar o projeto

1. **Clone o repositório**
   ```bash
   git clone https://github.com/challengelotus/auto-escola-api.git
