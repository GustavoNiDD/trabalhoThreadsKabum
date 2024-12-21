# README

## Descrição do Projeto

1. Faça a importação do arquivo API Completa.postman_collection.json para o postman.
2. Crie um cliente, crie uma categoria e crie o produto.
3. Execute o arquivo chamarThreads.py para executar a simulação.


## Especificações
Este projeto demonstra três abordagens diferentes de controle de concorrência no momento de criar e atualizar pedidos em uma aplicação Spring Boot que atualiza o estoque de produtos:

1. **Sem Lock (noLock)**: Nenhum mecanismo especial além do gerenciamento padrão do banco.
2. **Lock Otimista (optimistic)**: Utiliza um campo anotado com `@Version` na entidade Produto, lançando exceção caso a versão do produto seja alterada por outra transação concorrente antes do commit.
3. **Lock Pessimista (pessimistic)**: Bloqueia o registro no banco de dados no momento da leitura, impedindo que outras transações modifiquem o mesmo registro até que o lock seja liberado.

A aplicação permite selecionar qual estratégia de bloqueio utilizar no momento de criar ou atualizar um pedido, através de um parâmetro `mode` na requisição (`noLock`, `optimistic` ou `pessimistic`).

## Pré-requisitos

- Java 17+  
- Maven  
- Python 3.x  
- Biblioteca `requests` em Python (`pip install requests`)

## Configuração do Projeto

1. **Clonar o repositório** ou ter acesso ao código fonte.
2. **Instalar dependências**:  
   Na raiz do projeto, execute:
   ```bash
   mvn clean install
