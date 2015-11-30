#### Desligar um servidor
curl --request PUT http://localhost:8080/file-service/shutdown/0

#### Adicionar um arquivo
curl -H "Content-type: application/json" -X POST -d '{"id":100, "content": "teste"}' http://localhost:8080/file-service/add 



