#language: pt

Funcionalidade: Fazer login
  Cenario:
    Dado abrir navegadCucumor
    E acessar endereco </login.htm>
    Quando preencho campo <login> com o valor <lamaral> buscando pelo <id>
    E preencho campo <senha> com o valor <teste123> buscando pelo <id>
    Entao comparar igualdade entre valor esperado <Sucesso!> com atributo <texto> do elemento <teste> buscando pelo <nome>
