# LiterAlura - Catálogo de Livros

![Status](https://img.shields.io/badge/status-conclu%C3%ADdo-brightgreen)

## 📖 Descrição

**LiterAlura** é uma aplicação de catálogo de livros interativa via console, desenvolvida como parte do challenge de programação da Alura. A aplicação permite que os usuários busquem por livros através da API pública [Gutendex](https://gutendex.com/), visualizem os resultados e salvem seus livros e autores preferidos em um banco de dados PostgreSQL para consulta posterior.

Este projeto demonstra conceitos essenciais do desenvolvimento backend com Java e Spring, incluindo consumo de APIs, manipulação de dados JSON, persistência de dados com Spring Data JPA e criação de uma aplicação interativa de console.

## ✨ Funcionalidades

O menu principal da aplicação oferece as seguintes opções:
- **1 - Buscar livro pelo título e salvar:** Pesquisa um livro na API Gutendex e o persiste no banco de dados local.
- **2 - Listar livros registrados:** Exibe todos os livros que já foram salvos no banco.
- **3 - Listar autores registrados:** Mostra todos os autores salvos.
- **4 - Listar autores vivos em um determinado ano:** Filtra e exibe autores que estavam vivos no ano especificado.
- **5 - Listar livros em um determinado idioma:** Permite buscar livros registrados por idioma (ex: `pt`, `en`, `fr`).

## 🛠️ Tecnologias Utilizadas

As seguintes tecnologias foram utilizadas na construção do projeto:
- **Java 21**
- **Spring Boot 3**
- **Spring Data JPA**
- **Maven**
- **PostgreSQL** (Banco de Dados)
- **Gutendex API** (Fonte externa de dados sobre livros)
- **Jackson** (Para manipulação de JSON, incluído no Spring Web)

## 🚀 Como Executar o Projeto

Para executar este projeto localmente, siga os passos abaixo:

#### **Pré-requisitos**

- **Java JDK 21** ou superior instalado.
- **Maven 4** ou superior.
- Uma instância do **PostgreSQL** em execução.

#### **Configuração**

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/seu-usuario/literalura-challenge.git](https://github.com/seu-usuario/literalura-challenge.git)
    cd literalura-challenge/liter-alura
    ```

2.  **Crie o banco de dados:**
    - Crie um novo banco de dados no seu PostgreSQL (ex: `literalura_db`).

3.  **Configure as Variáveis de Ambiente:**
    - O projeto utiliza variáveis de ambiente para a conexão com o banco de dados, conforme definido no arquivo `application.properties`. Você precisa configurar as seguintes variáveis no seu sistema ou na sua IDE (Run/Debug Configurations):
        - `DB_NAME`: O nome do banco de dados que você criou (ex: `literalura_db`).
        - `DB_USER`: Seu nome de usuário do PostgreSQL.
        - `DB_PASSWORD`: Sua senha do PostgreSQL.
        - `DB_HOST` (opcional): O endereço do seu banco. Se não for definido, o padrão será `localhost:5432`.

4.  **Execute a Aplicação:**
    - Pela sua IDE (IntelliJ), basta executar a classe `LiteraluraApplication.java`.
    - Ou, pelo terminal na pasta do projeto:
      ```bash
      mvn spring-boot:run
      ```
    - Após a inicialização, o menu interativo aparecerá no console.

## ✒️ Autor

Desenvolvido por **João Roberto (joaorgd)** como parte do programa Alura Challenges.