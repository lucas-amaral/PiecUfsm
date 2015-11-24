#language: pt

Funcionalidade: Ações executadas por um membro do colegiado do curso de Ciência da Computação na aprovação/regeição de
  planos individuais de estudos complementares.

  Contexto: Fazer login no sistema como um membro do colegiado
    Dado acesso o endereco /login.htm
    Quando preencho o campo login com o valor colegiado buscando pelo id
    E preencho o campo senha com o valor colegiado123 buscando pelo id
    E clico no elemento button.btn.btn-default buscando pelo css
    Entao verifico se atributo nome tag do elemento id buscando pelo nome não está nulo

  @aceitacao
  Cenario: Cadastrar nova disciplina.
    Dado acesso o endereco /cadastro-disciplina.htm
    Quando preencho o campo codigo com o valor ELC9898 buscando pelo id
    E preencho o campo nome com o valor Disciplina nova buscando pelo id
    E seleciono a opcao 60 no campo cargaHoraria buscando pelo id
    E clico no elemento ativa1 buscando pelo id
    E seleciono a opcao UFSM - Universidade Federal de Santa Maria no campo idInstituicao buscando pelo id
    E clico no elemento preAprovada1 buscando pelo id
    E clico no elemento salvar buscando pelo id
    Entao comparo a igualdade entre o valor esperado Sucesso! com atributo texto do elemento h4 buscando pelo css
    E clico no elemento input.btn.btn-danger buscando pelo css

  @rejeicao
  Cenario: Impedir registro de nova disciplina utilizando código já utilizado por outra disciplina anteriormente cadastrada
    Dado acesso o endereco /cadastro-disciplina.htm
    Quando preencho o campo codigo com o valor ELC139 buscando pelo id
    E clico no elemento salvar buscando pelo id
    Entao comparo a igualdade entre o valor esperado Código já cadastrado em outra disciplina. com atributo texto do elemento disciplina.errors buscando pelo id

  @aceitacao
  Cenario: Cadastrar nova instituição.
    Dado clico no elemento Instituições buscando pelo texto do link
    Quando clico no elemento button.btn.btn-success buscando pelo css
    E preencho o campo sigla com o valor UFABC buscando pelo id
    E preencho o campo nome com o valor Universidade Federal de ABC buscando pelo id
    E seleciono a opcao Alagoas no campo estado buscando pelo id
    E clico no elemento input.btn.btn-success buscando pelo id
    Entao comparo a igualdade entre o valor esperado Sucesso! com atributo texto do elemento h4 buscando pelo css
    E clico no elemento input.btn.btn-danger buscando pelo css

  @aceitacao
  Cenario: Remover um bloco pré-aprovado.
    Dado clico no elemento Sugestões buscando pelo texto do link
    Quando clico no elemento input.btn.btn-danger buscando pelo css
    Entao comparo a igualdade entre o valor esperado Opção removida com sucesso com atributo texto do elemento h4 buscando pelo css

  @aceitacao
  Cenario: Rejeitar uma disciplina e re-enviar PIEC para ajustes do aluno
    Dado clico no elemento button.btn.btn-default buscando pelo css
    Quando clico no elemento a.tooltip-class > img buscando pelo css
    E clico no elemento //button[@type='button'])[2] buscando pelo xpath
    E clico no elemento negarDisciplina buscando pelo id
    Entao comparo a igualdade entre o valor esperado Sucesso! com atributo texto do elemento h4 buscando pelo css
    E clico no elemento ajustes buscando pelo id
    E clico no elemento Sair buscando pelo texto do link
    E preencho o campo login com o valor thiago buscando pelo id
    E preencho o campo senha com o valor thiago buscando pelo id
    E clico no elemento button.btn.btn-default buscando pelo css