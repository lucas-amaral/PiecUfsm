#language: pt

Funcionalidade: Fazer login
  Cenario: Login ok
    Dado abrir navegador
    E acessar endereco /login.htm
    Quando preencho campo login com o valor lamaral buscando pelo id
    E preencho campo senha com o valor teste123 buscando pelo id
    E clicar no elemento button.btn.btn-default buscando pelo css
    Entao comparar se atributo nome tag do elemento id buscando pelo nome não está nulo

Funcionalidade: Adicionar disciplina no piec
  Cenario: Adicionar disciplina sucesso

  Cenario: Adicionar disciplina erro
