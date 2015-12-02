#### Desligar um servidor
curl --request PUT http://localhost:8080/file-service/shutdown/0

#### Adicionar um arquivo
curl -H "Content-type: application/json" -X POST -d '{"id":100, "content": "teste"}' http://localhost:8080/file-service/add

#### Ler um arquivo
curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8080/file-service/read/100 

#### Remover
curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X DELETE http://localhost:8080/file-service/remove/100

#### Atualizar
 curl -H "Content-type: application/json" -X PUT -d '{"id":100, "content": "content atualizado"}' http://localhost:8080/file-service/write



