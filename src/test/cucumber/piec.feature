#language: pt

Funcionalidade: Fazer login
  Cenario:
    Dado abrir navegador
    E acessar endereco /login.htm
    Quando preencho campo login com o valor lamaral buscando pelo id
    E preencho campo senha com o valor teste123 buscando pelo id
    E clicar no elemento button.btn.btn-default buscando pelo css
    Entao comparar igualdade entre valor esperado 1 com atributo texto do elemento id buscando pelo id