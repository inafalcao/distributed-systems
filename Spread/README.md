## Trabalho 1 - Grupo de Processos

Para este projeto, o requisito adicional é ter o spread configurado. 

## Cofigurando o spread

### Instalação

Após baixar o spread, e extraí-lo em um diretório desejado (/opt, por exemplo), execute: 

```sh
$ ./configure
$ make
$ make install
```
Procure pelo arquivo example.spread.conf no diretório /docs, procure por Spread_Segment e configure como a seguir:

Spread_Segment  127.0.0.255:4803 {
	localhost		127.0.0.1
}

Mova este arqquivo para o diretório raiz /spread

### Execução
1. Pelo terminal, navegue até a pasta daemon
2. Execute
```sh
$ ./spread -c /opt/spread-src-4.4.0/example.spread.conf
```

# Executando o projeto

1. Rode o spread com os passos acima
2. Execute
```sh
$ mvn tomcat7:run-war
```
