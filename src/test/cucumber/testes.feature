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
    Dado comparar se atributo nome tag do elemento id buscando pelo nome não está nulo
    Quando preencho campo idDisciplinaAdicionar com o valor ELC1051	- Computação Gráfica Avançada (60h) buscando pelo id
    E preencho campo piecDisciplinaAdicionar.cursoOfertante com o valor Ciência da Computação buscando pelo id
    E preencho campo piecDisciplinaAdicionar.semestreAnoRealizacao com o valor II/2011 buscando pelo id
    E clicar no elemento //fieldset[@id='inserir_disciplina']/table/tbody/tr[21]/td[4]/a/img buscando pelo xpath
    Entao
