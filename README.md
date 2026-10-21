# JDBC DAO

Projeto de estudos focado no acesso a dados em Java utilizando JDBC puro, contendo exemplos e um projeto JDBC no padrão
DAO, tendo como base o curso do professor Nélio Alves.

## Conteúdo do Repositório

* **Exemplos JDBC Puro**
* **Projeto Demo DAO JDBC**
* **Evolução Arquitetural:**
    * Refatoração aplicada para Ports and Adapter.
    * Interfaces genéricas de persistência.
    * DAO Factory gerenciando e reutilizando instâncias de conexão.

## Estrutura e Conceitos

1. **Domínio:** Classes POJO puras representing o modelo (ex: Seller, Department).
2. **Ports / Interfaces:** Contratos genéricos que definem as operações de persistência independentes de tecnologia.
3. **Adapters / JDBC Implementations:** Lógica concreta de manipulação SQL, tratamento de exceções e mapeamento de
   conjuntos de resultados (ResultSet).
4. **Factory:** Centralização do fornecimento de instâncias dos DAOs/Ports.

## Subir o DB com Docker

Para subir o banco de dados do projeto, execute o comando do Docker Compose para inicializar o container:

```bash
docker compose up -d
```

> [!NOTE]
> Por fins didáticos, o projeto não utiliza um arquivo `.env` para a gestão de variáveis de ambiente. As credenciais do
banco de dados e as configurações de conexão foram mantidas em *hard coded*, declaradas diretamente nos arquivos
`docker-compose.yml` e `src/main/resources/db.properties`.

## População do Banco de Dados

Para a criação das tabelas e população inicial dos dados de demonstração (*seed*), execute o script SQL contido no
arquivo `src/main/resources/seed-db.sql` diretamente no seu gerenciador ou cliente de banco de dados (ex: DBeaver).

## Execução

Basta atualizar as dependências com *Maven* e executar `src/main/java/org/example/application/Program.java`.
