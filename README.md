# API para Gerenciamento de Usuários - Loja SneakerShop
> Status do Projeto: Concluído! :heavy_check_mark:

Documentação completa da API para Gerenciamento de Usuários da loja SneakerShop, desenvolvido com Quarkus Java e MySQL. A aplicação permite que os administradores gerenciem os usuários do sistema de forma eficiente e segura, utilizando JWT para autenticação e controle de acesso

## Objetivo

O objetivo principal desta sistema é oferecer uma solução simples e eficaz para os administradores do SneakerShop monitorarem e gerenciarem os perfis de usuários, incluindo funcionalidades como:

- Gerenciar usuários (administradores, clientes e funcionários).
- Cadastrar, visualizar, editar e excluir usuários do sistema.
- Visualizar o histórico de operações recentes
- Garantir a segurança do acesso, com autenticação segura para que apenas administradores possam gerenciar os dados dos usuários.

## Funcionalidades da API RESTful

- [x] Gerenciamento de permissões de usuário
- [x] CRUD de usuários (Criar, Ler, Atualizar e Deletar)
- [x] Histórico de Operações: exibe as últimas ações realizadas no sistema.
- [x] Controle de acesso utilizando autenticação JWT
- [x] Sistema de rotas protegidas
- [x] Criptografia de senhas

## Estruturação de Rotas

```
.
/src
  ├── /main
  │     ├── /java
  │     │     ├── /techtins/api
  │     │     │     │    └── /auth
  │     │     │     │         ├──AuthResource.java
  │     │     │     │         ├──AuthResponse.java
  │     │     │     │         ├──UserLoginRequest.java
  │     │     │     │         └──UserService.java
  │     │     │     │
  │     │     │     │    └──/security
  │     │     │     │         ├──JwtFilter.java
  │     │     │     │         ├──JwtRequired.java
  │     │     │     │         └──JwtUtils.java
  │     │     │     │    
  │     │     │     ├── UserResource.java
  │     │     │     ├── User.java
  │     │     │     └── UserHistory.java
  │     │     │     
  │     ├── /resources
  │           └── application.properties
```

## Rotas API RESTful

### @GET /users/create-admin
- Cria um usuário root para teste do sistema

### @GET /users
- Retorna uma lista de todos os usuários cadastrados.
```json
[
  {
    "id": 1,
    "nome": "Admin",
    "sobrenome": "User",
    "email": "admin@example.com",
    "senha": "$2a$10$kXuanb.cmVsE1249hcWyH.bhAJ/7QjmRK.S/33p8BaYJVfOS6nR.W",
    "funcao": "Admin",
    "dataCadastro": "2024-10-04",
    "status": "Ativo",
    "fotoUrl": null,
    "endereco": null,
    "genero": "Outro"
  }
]
```

### @POST /users
- Cria um novo usuário no sistema.

### @PUT /users/{id}
- Atualiza os dados de um usuário existente.

### @DELETE /users/{id}
- Exclui um usuário do sistema.

### @GET /users/history
- Retorna um histórico das últimas 5 operações de cadastro realizadas no sistema.
```json
[
  {
    "id": 1,
    "adminName": "Admin User",
    "adminEmail": "admin@example.com",
    "createdUserName": "CreatedUser",
    "createdUserEmail": "createduser23@gmail.com",
    "createdDate": "2024-10-04",
    "actionType": "Cadastro"
  }
]
```

## Tecnologias

- [Java](https://www.java.com/pt-BR/)
- [Quarkus](https://pt.quarkus.io/)
- [JWT (JSON Web Token)](https://jwt.io/)
- [MySQL](https://www.mysql.com/)

## Requisitos

#### Java JDK
É necessário instalar o [JDK 11](https://www.oracle.com/java/technologies/downloads/#java11?er=221886) executar o projeto corretamente. Na página oficial, siga as instruções do intalador.

- **Versão Requerida**: JDK 11 ou Superior.

Você pode verificar a versão instalada com o seguinte comando no terminal:

```bash
java -version
```

### MySQL
Baixe o [MySQL Installer](https://dev.mysql.com/downloads/installer/) para criação do banco de dados. Dentro da página oficial, siga as instruções do intalador.

Você pode verificar a versão instalada com o seguinte comando no terminal:

```bash
mysql -u root -p
```

### Maven
Faça a intalação do [Maven](https://maven.apache.org/download.cgi) para facilitar a execução do projeto. Após instalação, siga os passos:

- Extraia o arquivo baixado para um diretório de sua escolha
- Adicione o caminho do diretório `bin` do Maven nas variáveis de ambiente do sistema `(PATH)`.

Você pode verificar a versão instalada com o seguinte comando no terminal:

```bash
mvn -version
```

## Execução da Aplicação

1º No terminal, clone o repositório:

```bash
git clone https://github.com/arthurpestana/projeto-techtins-backend.git
```

2º Acesse o diretório do projeto e instale todas as dependências.

```bash
mvn clean install
```

3º Crie o banco de dados usando o arquivo disponível na pasta `/db`:

```sql
DROP DATABASE IF EXISTS projeto_techtins_db;
CREATE DATABASE IF NOT EXISTS projeto_techtins_db;

USE projeto_techtins_db;

-- Tabela para armazenar informações de usuários
CREATE TABLE IF NOT EXISTS user (
    id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    sobrenome VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    funcao ENUM('Admin', 'Cliente', 'Funcionário') DEFAULT 'Cliente',
    status ENUM('Ativo', 'Inativo') DEFAULT 'Ativo',
    dataCadastro DATE NOT NULL,
    fotoUrl VARCHAR(255),
    endereco VARCHAR(255),
    genero ENUM('Masculino', 'Feminino', 'Outro') DEFAULT 'Outro'
);

-- Tabela para armazenar histórico de criação e modificação de usuários
CREATE TABLE IF NOT EXISTS user_history (
    id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    adminName VARCHAR(100) NOT NULL,
    adminEmail VARCHAR(255) NOT NULL,
    createdUserName VARCHAR(255) NOT NULL,
    createdUserEmail VARCHAR(255) NOT NULL,
    createdDate DATE NOT NULL,
    actionType ENUM('Cadastro', 'Atualização', 'Deleção') NOT NULL
);
```

4º Acesse o arquivo `src/main/resources/application.properties` e modifique as informações referentes ao seu SGBD. Como: usuário, senha, porta e schema.

```properties
quarkus.datasource.db-kind=mysql
quarkus.datasource.username={usuário}
quarkus.datasource.password={senha}
quarkus.datasource.jdbc.url=jdbc:mysql://localhost:{porta}/{schema}
```
Por fim, execute o servidor de desenvolvimento:

```bash
mvn quarkus:dev
```

A aplicação estará disponível em [http://localhost:8080](http://localhost:8080).

## Licença

The [MIT License]() (MIT)

Copyright :copyright: 2024 - Projeto-Techtins
