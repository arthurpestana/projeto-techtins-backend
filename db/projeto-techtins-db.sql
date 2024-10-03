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
    genero ENUM('Masculino', 'Feminino', 'Outro')
);

-- Tabela para armazenar histórico de criação e modificação de usuários
CREATE TABLE IF NOT EXISTS user_history (
    id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    adminName VARCHAR(100) NOT NULL,  -- Nome do admin que criou/modificou o usuário
    adminEmail VARCHAR(255) NOT NULL,  -- Email do admin
    createdUserName VARCHAR(255) NOT NULL,  -- Nome do usuário que foi criado/modificado
    createdDate DATE NOT NULL,  -- Data em que o usuário foi criado/modificado
    actionType ENUM('Cadastro', 'Atualização', 'Deleção') NOT NULL  -- Tipo de ação
);
