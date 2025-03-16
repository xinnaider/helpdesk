# Helpdesk

Este projeto é um sistema de Helpdesk simples desenvolvido com Spring Boot, com foco no cadastro de usuários e tickets. O sistema integra dois microserviços, onde o serviço de tickets utiliza os dados dos usuários.

## Visão Geral

- **Cadastro de Usuário**: Gerencia o cadastro e as informações dos usuários.
- **Tickets**: Gerencia a criação e o acompanhamento dos chamados, integrando informações dos usuários.

## Tecnologias e Ferramentas

- Spring Boot
- Swagger: Documentação e teste das APIs.
- Docker: Containerização completa dos serviços.
- Flyway: Gerenciamento de migrations do banco de dados.
- Testcontainers: Testes integrados com containers.
- Eureka: Service discovery para a integração dos microserviços.
- Makefile: Facilita a execução dos serviços com Docker.

## Como Executar o Projeto

### Pré-requisitos

- Git
- Docker
- Make (para utilizar o comando `make up`)

### Passos para Testar

1. Clonar o repositório:
   ```bash
   git clone https://github.com/xinnaider/helpdesk.git
   cd helpdesk
   ```

2. Subir os serviços:
   Certifique-se de que o Docker está em execução e, em seguida, execute:
   ```bash
   make up
   ```
   Esse comando iniciará todos os containers necessários para a aplicação.

## Documentação da API

Após subir os serviços, a documentação interativa da API pode ser acessada via Swagger em:
```
http://localhost:[porta]/swagger-ui.html
```
(Substitua `[porta]` pela porta configurada para o seu serviço)
