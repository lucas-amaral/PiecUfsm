#language: pt

Funcionalidade: Ações executadas por um aluno do curso de Ciência da Computação no registro de seu
  plano individual de estudos complementares.

  Contexto: Fazer login no sistema como um aluno
    Dado acesso o endereco /login.htm
    Quando preencho o campo login com o valor lamaral buscando pelo id
    E preencho o campo senha com o valor teste123 buscando pelo id
    E clico no elemento button.btn.btn-default buscando pelo css
    Entao verifico se atributo nome tag do elemento id buscando pelo nome não está nulo

  @aceitacao
  Cenario: Adicionar umas disciplina ao plano com sucesso.
    Dado seleciono a opcao ELC1051 - Computação Gráfica Avançada (60h) no campo idDisciplinaAdicionar buscando pelo id
    Quando preencho o campo piecDisciplinaAdicionar.cursoOfertante com o valor Ciência da Computação buscando pelo id
    E preencho o campo piecDisciplinaAdicionar.semestreAnoRealizacao com o valor II/2011 buscando pelo id
    E clico no elemento adicionarPiecDisciplina buscando pelo id
    Entao comparo a igualdade entre o valor esperado Sucesso! com atributo texto do elemento h4 buscando pelo css

  @rejeicao
  Cenario: Não permitir adicionar disciplina ao plano quando a mesma já está incluida.
    Dado seleciono a opcao ELC1051 - Computação Gráfica Avançada (60h) no campo idDisciplinaAdicionar buscando pelo id
    Quando preencho o campo piecDisciplinaAdicionar.cursoOfertante com o valor Ciência da Computação buscando pelo id
    E preencho o campo piecDisciplinaAdicionar.semestreAnoRealizacao com o valor II/2011 buscando pelo id
    E clico no elemento adicionarPiecDisciplina buscando pelo id
    Entao comparo a igualdade entre o valor esperado Disciplina já inserida no plano. com atributo texto do elemento piec.errors buscando pelo id

  @aceitacao
  Cenario: Adicionar uma disciplina de outra instituição não cadastrada previamente no sistema.
    Dado seleciono a opcao Adicionar outra disciplina no campo idDisciplinaAdicionar buscando pelo id
    Quando preencho o campo piecDisciplinaAdicionar.cursoOfertante com o valor Sistemas de Informação buscando pelo id
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

  @aceitacao
  Cenario: Incorporar um plano já aprovado ao PIEC
    Dado clico no elemento Sugestões buscando pelo texto do link
    Quando clico no elemento button.btn.btn-default buscando pelo css
    Entao comparo a igualdade entre o valor esperado PIEC - Online com atributo titulo da pagina do elemento null buscando pelo null
    
  @aceitacao
  Cenario: Aprovar um PIEC após solicitação do aluno
    Dado clico no elemento Sair buscando pelo texto do link
    Quando preencho o campo login com o valor pbzorila buscando pelo id
    E preencho o campo senha com o valor 123 buscando pelo id
    E clico no elemento button.btn.btn-default buscando pelo css
    E clico no elemento Sugestões buscando pelo texto do link
    E clico no elemento button.btn.btn-default buscando pelo css
    E clico no elemento Piec buscando pelo texto do link
    E clico no elemento solicitar buscando pelo id
    E clico no elemento Sair buscando pelo texto do link
    E preencho o campo login com o valor colegiado buscando pelo id
    E preencho o campo senha com o valor colegiado buscando pelo id
    E clico no elemento button.btn.btn-default buscando pelo css
    E clico no elemento a.tooltip-class > img buscando pelo css
    E preencho o campo parecerRelator com o valor Ok! buscando pelo id
    E preencho o campo parecerColegiado com o valor Aceito. buscando pelo id
    E clico no elemento aprovar buscando pelo id
    Entao comparo a igualdade entre o valor esperado Sucesso! com atributo texto do elemento h4 buscando pelo css

  @rejeicao
  Delineacao do Cenario: 1 - Não permitir inserir no PIEC disciplina que não faça parte do curso, sem o preenchimento do campo relevância da integralização
     2 - Não permitir cadastrar nova instituição com sigla já cadastrada
     3 - Não permitir cadastrar nova disciplina sem sua respectiva sigla
     4 - Não permitir cadastrar nova disciplina sem sua respectiva nome
     5 - Não permitir inserir disciplina com uma sigla já cadastrada
    Dado seleciono a opcao Adicionar outra disciplina no campo idDisciplinaAdicionar buscando pelo id
    Quando preencho o campo novaDisciplina.codigo com o valor <codigoDis> buscando pelo id
    E preencho o campo novaDisciplina.nome com o valor <nomeNovaDisciplina> buscando pelo id
    E preencho o campo piecDisciplinaAdicionar.relevanciaIntegralizacao com o valor <relevancia> buscando pelo id
    E seleciono a opcao Adicionar outra instituição no campo novaDisciplina.idInstituicao buscando pelo id
    E seleciono a opcao 78 horas no campo novaDisciplina.cargaHoraria buscando pelo id
    E preencho o campo novaInstituicao.nome com o valor Universidade Teste buscando pelo id
    E preencho o campo novaInstituicao.sigla com o valor <siglaInst> buscando pelo id
    E preencho o campo piecDisciplinaAdicionar.arquivoPlanoEnsino com o valor C:\\Users\\Lucas\\Desktop\\putty.exe buscando pelo id
    E clico no elemento adicionarPiecDisciplina buscando pelo id
    Entao comparo a igualdade entre o valor esperado <msgErro> com atributo texto do elemento piec.errors buscando pelo id

  Exemplos:
    | codigoDis | nomeNovaDisciplina | relevancia | siglaInst | msgErro                                        |
    | TESTE95   | Teste informática  |            | USC       | Preencha o campo relevância da integralização. |
    | TESTE95   | Teste informática  | Teste 1    | UFSM      | Sigla já cadastrada em outra instituição.      |
    |           | Teste informática  | Teste 2    | UFT       | Preencha o campo código.                       |
    | TESTE98   |                    | Teste 3    | UFS       | Preencha o campo nome.                         |
    | ELC1051   | Computação Gráfica | Teste 4    | UFAR      | Código já cadastrado em outra disciplina. Por favor, caso a mesma não conste na lista de possibilidades, entre em contato com a coordenação do curso.|
