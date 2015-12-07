
## Trabalho 2 e 3 - Serviço de Arquivos + Replicação e Consistência em Sistemas de Arquivos Distribuído

Os requisitos principais podem ser conferidos no read.meraiz do projeto.

Você também deve ter o CURL instalado na sua máquina, para enviar requisições REST, ou pode enviar com qualquer outra aplicação capaz de realizar a mesma tarefa (ex: pluguin postman no chrome)

## Como executar

- Primeiro deve-se executar o spread. Mais sobre como executar o spread [aqui](../Spread)

```sh
$ ./spread -c /opt/spread-src-4.4.0/spread.conf
```

- Depois, o projeto pode ser executado com a seguinte linha de comando:

```sh
$ mvn tomcat7:run-war
```
Com isso isso, você verá que a eleição do master será executada e um Servidor será eleito.
Após a eleição, você já pode enviar comandos, via CURL:

#### Desligar um servidor
curl --request PUT http://localhost:8080/file-service/shutdown/server/0

#### Desligar um slave
curl --request PUT http://localhost:8080/file-service/shutdown/slave/0

#### Adicionar um arquivo
curl -H "Content-type: application/json" -X POST -d '{"id":100, "content": "teste"}' http://localhost:8080/file-service/add

#### Ler um arquivo
curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/file-service/read/100 

#### Remover
curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X DELETE http://localhost:8080/file-service/remove/100

#### Atualizar
 curl -H "Content-type: application/json" -X PUT -d '{"id":100, "content": "content atualizado"}' http://localhost:8080/file-service/write
