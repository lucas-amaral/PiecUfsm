#language: pt

Funcionalidade: Ações executadas por um aluno do curso de Ciência da Computação no registro de seu
  plano individual de estudos complementares.

  Contexto: Fazer login no sistema como um aluno
    Dado acesso o endereco /login.htm
    Quando preencho o campo login com o valor lamaral buscando pelo id
    E preencho o campo senha com o valor teste123 buscando pelo id
    E clico no elemento button.btn.btn-default buscando pelo css
    Entao verifico se atributo nome tag do elemento id buscando pelo nome não está nulo

  Cenario: Adicionar umas disciplina ao plano com sucesso.
    Dado seleciono a opcao ELC1051 - Computação Gráfica Avançada (60h) no campo idDisciplinaAdicionar buscando pelo id
    E preencho o campo piecDisciplinaAdicionar.cursoOfertante com o valor Ciência da Computação buscando pelo id
    E preencho o campo piecDisciplinaAdicionar.semestreAnoRealizacao com o valor II/2011 buscando pelo id
    E clico no elemento adicionarPiecDisciplina buscando pelo id
    Entao comparo a igualdade entre o valor esperado Sucesso! com atributo texto do elemento h4 buscando pelo css

  Cenario: Não permitir adicionar disciplina ao plano quando a mesma já está incluida.
    Dado seleciono a opcao ELC1051 - Computação Gráfica Avançada (60h) no campo idDisciplinaAdicionar buscando pelo id
    E preencho o campo piecDisciplinaAdicionar.cursoOfertante com o valor Ciência da Computação buscando pelo id
    E preencho o campo piecDisciplinaAdicionar.semestreAnoRealizacao com o valor II/2011 buscando pelo id
    E clico no elemento adicionarPiecDisciplina buscando pelo id
    Entao comparo a igualdade entre o valor esperado Disciplina já inserida no plano. com atributo texto do elemento piec.errors buscando pelo id

  Cenario: Adicionar uma disciplina de outra instituição não cadastrada previamente no sistema.
    Dado seleciono a opcao Adicionar outra disciplina no campo idDisciplinaAdicionar buscando pelo id
    E preencho o campo piecDisciplinaAdicionar.cursoOfertante com o valor Sistemas de Informação buscando pelo id
    E preencho o campo piecDisciplinaAdicionar.semestreAnoRealizacao com o valor II/2009 buscando pelo id
    E preencho o campo novaDisciplina.codigo com o valor INF12345 buscando pelo id
    E preencho o campo novaDisciplina.nome com o valor Administração na Computação buscando pelo id
    E seleciono a opcao Adicionar outra instituição no campo novaDisciplina.idInstituicao buscando pelo id
    E seleciono a opcao 30 horas no campo novaDisciplina.cargaHoraria buscando pelo id
    E preencho o campo novaInstituicao.nome com o valor Universidade Federal de Alegrete buscando pelo id
    E preencho o campo novaInstituicao.sigla com o valor UFA buscando pelo id
    E preencho o campo piecDisciplinaAdicionar.relevanciaIntegralizacao com o valor Muito importante buscando pelo id
    E clico no elemento adicionarPiecDisciplina buscando pelo id
    Entao comparo a igualdade entre o valor esperado Insira o arquivo do plano de ensino da disciplina desejada. com atributo texto do elemento piec.errors buscando pelo id
    Mas preencho o campo piecDisciplinaAdicionar.arquivoPlanoEnsino com o valor C:\\Users\\Lucas\\Desktop\\putty.exe buscando pelo id
    E clico no elemento adicionarPiecDisciplina buscando pelo id
    Entao comparo a igualdade entre o valor esperado Sucesso! com atributo texto do elemento h4 buscando pelo css

#  Cenario: Não permitir inserir disciplina com uma sigla já cadastrada
#  Código já cadastrado em outra disciplina. Por favor, caso a mesma não conste na lista de possibilidades, entre em contato com a coordenação do curso.

#  Cenario: Não permitir cadastrar nova disciplina sem sua respectiva sigla e nome
#  ValidationUtils.rejectIfEmptyOrWhitespace(errors, "novaDisciplina.codigo", "field.required", "Preencha o campo código.");
#  ValidationUtils.rejectIfEmptyOrWhitespace(errors, "novaDisciplina.nome", "field.required", "Preencha o campo nome.");

#  Cenario: Não permitir cadastrar nova instituição com sigla já cadastrada
#  Sigla já cadastrada em outra instituição.

#  Cenario: Não permitir inserir no PIEC disciplina que não faça parte do curso, sem o preenchimento do campo relevância da integralização
#  ValidationUtils.rejectIfEmptyOrWhitespace(errors, "piecDisciplinaAdicionar.relevanciaIntegralizacao", "field.required", "Preencha o campo relevância da integralização.");

