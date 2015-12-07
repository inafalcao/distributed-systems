## Trabalho 1 - Grupo de Processos

Para este projeto, o requisito adicional é ter o spread configurado. 

## Cofigurando o spread

Após baixar o spread, e extraí-lo em um diretório desejado (/opt, por exemplo), execute: 

```sh
$ ./configure
$ make
$ make install
```

1. Procure pelo arquivo example.spread.conf no diretório /docs, edite-o e mova para o diretório /daemon
2. Pelo terminal, navegue até a pasta daemon,
3. Execute
```sh
$ ./spread -c /opt/spread-src-4.4.0/example.spread.conf
```

# Executando o projeto

1. Rode o spread com os passos acima
2. Execute
```sh
$ mvn tomcat7:run-war
```
