# API Rest -  COPIA DO README 

http://localhost:8081/swagger-ui/index.html#/
https://athemes.com/collections/free-bootstrap-admin-templates/

https://github.com/millapupo/escolaBurlescaApi/blob/main/README.md?plain=1
proietaria do readme 

API Rest da aplicação Escola Burlesca, contendo as funcionalidades de CRUD de professoras e de alunas, além de agendamento e cancelamento de aulas experimentais.

## 🚀 Como começar

Essas instruções permitirão que você obtenha uma cópia do projeto em operação na sua máquina local para fins de desenvolvimento e teste.

### 📋 Pré-requisitos

Para testar a aplicação é preciso ter o Java SE 17 instalado.

### 🔧 Instalação

Faça o download da aplicação e abra na IDE de sua preferência, para melhor desempenho sugiro IntelliJ ou Eclipse.

## ⚙️ Executando

Vá em "src/main/java/burlesca.escola.api" e rode a aplicação em "ApiApplication".

Para testar as requisições é possível usar frameworks como Insomnia, Postman, etc ou acessar pelo Swagger no link https://app.swaggerhub.com/apis-docs/MILLAMEKARY/escola-burlesca_api/1.0.0#/

## 💻 Funcionalidades da aplicação

#### Cadastramento de professoras

A aplicação permite cadastramento de professoras, sendo obrigatório o preenchimento das seguintes informações:

    Nome
    E-mail
    Telefone
    Modalidade (Bollywood, Burlesque, Pole acro, Pole Flow, Ballet, Salsa shines, Dança do ventre, Tribal, Lyrical, Jazz, Brodway, Chair dance, Flexibilidade, Street dance e Teatro) - sendo necessário cadastrar mais de uma modalidade por professora. 

#### Cadastramento de alunas

A aplicação permite cadastramento de alunas, sendo obrigatório o preenchimento das seguintes informações:

    Nome
    E-mail
    Telefone
    Modalidade (Bollywood, Burlesque, Pole acro, Pole Flow, Ballet, Salsa shines, Dança do ventre, Tribal, Lyrical, Jazz, Brodway, Chair dance, Flexibilidade, Street dance e Teatro) - sendo possível cadastrar apenas uma modalidade por aluna.

#### Listagem de professoras e alunas

A aplicação permite a listagem de professoras e alunas cadastradas nos quais os seguintes dados serão exibidos:

    Nome
    E-mail
    Telefone
    Modalidade

A listagem é ordenada pelo nome, de maneira crescente, bem como ser paginada, trazendo 10 registros por página.

#### Atualização de professoras e alunas

A aplicação permite a atualização dos seguintes de dados das professoras e alunas:

    Nome
    Telefone
    Modalidade

#### Exclusão/Inativação de professoras e alunas

A aplicação permite a exclusão por meio da inativação das professoras ou alunas no sistema.

#### Agendamento de aulas

A aplicação permite o agendamento de aulas, na qual as seguintes informações deverão ser preenchidas:

    Aluna
    Modalidade
    Professora
    Data/Hora da aula

As seguintes regras de negócio devem ser seguidas:

As aulas deverão ser agendadas de segunda a sexta, das 10:00 às 19:00;

As aulas tem duração fixa de 1 hora;

As aulas devem ser agendadas com antecedência mínima de 2 dias;

Não permitir o agendamento de aulas com alunas inativas no sistema;

Não permitir o agendamento de aulas com professoras inativas no sistema;

Não permitir o agendamento de mais de uma aula no mesmo mês para uma mesma aluna;

A escolha da professora é obrigatória.

#### Cancelamento de aulas

A aplicação permite o cancelamento de aulas, na qual as seguintes informações deverão ser preenchidas:

    Aula
    Motivo do cancelamento 

As seguintes regras de negócio devem ser seguidas:

É obrigatório informar o motivo do cancelamento da aula, dentre as opções: aluna desistiu, professora cancelou ou outros;

Uma aula somente poderá ser cancelada com antecedência mínima de 4 horas.

## 🛠️ Construído com

* Java(TM) SE Runtime Environment
* Spring Boot 3
* Spring DATA
* Spring Security
* JSON Web Token
* MySQL
* Flyway

Desenvolvido por Milla Pupo