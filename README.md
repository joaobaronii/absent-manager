# Controlador de faltas

## Visão Geral
É uma aplicação baseada em Java para gerenciar faltas em disciplinas acadêmicas, especificamente do curso de Ciência da computação da UFU. Possui interface gráfica usando Swing banco de dados PostgreSQL e um script Python para extrair informações de um documento PDF.

## Funcionalidades
- **Adicionar/Remover Disciplinas**: Permite adicionar novas disciplinas pelo nome e remover existentes.
- **Rastrear Faltas**: Registra aulas perdidas ou justifica faltas.
- **Extração de Carga Horária**: Obtém automaticamente a carga horária da disciplina a partir de um PDF usando um script Python.
- **Integração com Banco de Dados**: Armazena detalhes da disciplina (nome, carga horária, faltas, limite de faltas) em um banco PostgreSQL.
- **Lista Visual**: Exibe as disciplinas com contagem de faltas, limite e carga horária em uma lista rolável.


## Uso
- **Iniciar**: Execute a classe `GUI` para abrir a aplicação.
- **Adicionar Disciplina**: Clique em "Add Subject", insira o nome da disciplina, e a carga horária será obtida automaticamente.
- **Gerenciar Faltas**: Selecione uma disciplina e use os botões "Missed class" para adicionar faltas ou "Remove absent" para justificar.
- **Remover Disciplina**: Selecione uma disciplina e clique em "Remove Subject" para excluí-la.

## Estrutura do Projeto
- **GUI.java**: Interface gráfica principal.
- **Subject.java**: Classe que representa uma disciplina.
- **SubjectManager.java**: Gerencia operações de banco de dados para disciplinas.
- **DB.java**: Configuração da conexão com o PostgreSQL.
- **wlextractor.py**: Script Python para extrair carga horária de um PDF.
