# FórumHub API 🚀

Uma API RESTful para um fórum de discussões, construída com **Spring Boot 3**, que permite aos usuários criar, visualizar, atualizar e deletar tópicos. A API inclui um sistema de autenticação via **JWT** para garantir a segurança das rotas.

---

## Funcionalidades ✨

- **Autenticação de Usuário**: Faça login com usuário e senha para obter um token JWT.
- **Criação de Tópicos**:
    - Usuários autenticados podem cadastrar novos tópicos.
    - A API verifica se já existe um tópico com o mesmo título para evitar duplicidade.
    - Se o curso não existir, ele é criado automaticamente.
- **Gerenciamento de Tópicos**:
    - Suporte completo a **CRUD (Create, Read, Update, Delete)** para tópicos.
- **Validação de Dados**:
    - Validações de entrada (`@Valid`) para garantir a integridade dos dados.
- **Tratamento de Erros**:
    - Tratamento de exceções personalizadas para recursos duplicados e não encontrados, retornando códigos de status HTTP apropriados.

---

## Tecnologias Utilizadas 🛠️

- **Java 17**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **Spring Security**
- **JSON Web Token (JWT)**
- **Maven**
- **MySQL**
- **Flyway**: Para gerenciamento de migrações de banco de dados.

---

