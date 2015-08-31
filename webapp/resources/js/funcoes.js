/**
 * Created by Lucas on 08/10/2014.
 */

function removerItem(tipo, idFormulario, propriedade, valor) {
    acaoItem("Remover"+tipo, idFormulario , propriedade, valor);
}

function adicionarItem(tipo, idFormulario) {
    var formulario = document.getElementById(idFormulario);
    submeteAcao("Adicionar"+tipo, formulario);
}

function acaoItem(acao, idFormulario, propriedade, valor) {
    var formulario = document.getElementById(idFormulario);
    submeteAcao(acao+"&"+propriedade+"="+valor, formulario);
}

function submeteAcao(acao, formulario) {
    var acaoAnterior = formulario.action;
    formulario.action += "?acao=" + acao;
    formulario.submit();
}

function mostraEsconde(idAlvo) {
    if (document.getElementById(idAlvo).style.display == 'none') {
        mostra(idAlvo);
    } else {
        esconde(idAlvo);
    }
}

function mostraEscondeValor(valorAtual, valoresMostra, idAlvo) {
    if (valoresMostra.lastIndexOf(valorAtual) > -1) {
        mostraSeExistir(idAlvo);
    } else {
        escondeSeExistir(idAlvo);
    }
}

function mostraSeExistir(idAlvo) {
    if (document.getElementById(idAlvo) != null) {
        mostra(idAlvo);
    }
}

function escondeSeExistir(idAlvo) {
    if (document.getElementById(idAlvo) != null) {
        esconde(idAlvo);
    }
}

function mostra(idAlvo) {
    mostraTipo(idAlvo, '');
}

function esconde(idAlvo) {
    document.getElementById(idAlvo).style.display = 'none';
}

function mostraTipo(idAlvo, tipo) {
    document.getElementById(idAlvo).style.display = tipo;
}

function mudaImagemCampoBoolean(imagem, campoBoolean, link, idPiecDisciplina) {
    if (campoBoolean == true) {
        if (imagem.src.contains('excluir')) {
            imagem.src = imagem.src.replace('excluir', 'aceito');
        } else if (imagem.src.contains('atencao')) {
            imagem.src = imagem.src.replace('atencao', 'aceito');
        }
    } else {
        if (imagem.src.contains('aceito')) {
            imagem.src = imagem.src.replace('aceito', 'excluir');
        } else if (imagem.src.contains('atencao')) {
            imagem.src = imagem.src.replace('atencao', 'excluir');
        }
    }
}

// construindo o calendário
function popdate(obj,div,tam,ddd)
{
    if (ddd) {
        day = "";
        mmonth = "";
        ano = "";
        c = 1;
        char = "";
        for (s=0;s<parseInt(ddd.length);s++)
        {
            char = ddd.substr(s,1);
            if (char == "/")
            {
                c++;
                s++;
                char = ddd.substr(s,1);
            }
            if (c==1) day    += char;
            if (c==2) mmonth += char;
            if (c==3) ano    += char;
        }
        ddd = mmonth + "/" + day + "/" + ano;
    }

    if(!ddd) {
        today = new Date();
    } else {
        today = new Date(ddd);
    }
    var date_Form = document.getElementById(obj);
    if (date_Form == null) {
        date_Form = eval (obj);
    }
    if (date_Form.value == "") {
        date_Form = new Date();
    } else {
        date_Form = new Date(date_Form.value);
    }

    var ano = today.getFullYear();
    mmonth = today.getMonth ();
    day = today.toString ().substr (8,2);

    umonth = new Array ("Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro");
    days_Feb = (!(ano % 4) ? 29 : 28);
    days = new Array (31, days_Feb, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);

    if ((mmonth < 0) || (mmonth > 11))  alert(mmonth);
    if ((mmonth - 1) == -1) {month_prior = 11; year_prior = ano - 1;} else {month_prior = mmonth - 1; year_prior = ano;}
    if ((mmonth + 1) == 12) {month_next  = 0;  year_next  = ano + 1;} else {month_next  = mmonth + 1; year_next  = ano;}
    txt  = "<table bgcolor='#F7F7F7' style='border: 2px solid #333333; border-spacing: 0;' cellpadding='3' width='"+tam+"' height='"+tam*1.1 +"'>";
    txt += "<tr bgcolor='#FFFFFF'><td colspan='7' style='text-align: center;'><table style='border-spacing: 0; border: 0;' cellpadding='0' width='100%' bgcolor='#FFFFFF'><tr>";
    txt += "<td width='20%' style='text-align: center;'><a href=javascript:popdate('"+obj+"','"+div+"','"+tam+"','"+((mmonth+1).toString() +"/01/"+(ano-1).toString())+"') class='Cabecalho_Calendario' title='Ano Anterior'><<</a></td>";
    txt += "<td width='20%' style='text-align: center;'><a href=javascript:popdate('"+obj+"','"+div+"','"+tam+"','"+( "01/" + (month_prior+1).toString() + "/" + year_prior.toString())+"') class='Cabecalho_Calendario' title='Mês Anterior'><</a></td>";
    txt += "<td width='20%' style='text-align: center;'><a href=javascript:popdate('"+obj+"','"+div+"','"+tam+"','"+( "01/" + (month_next+1).toString()  + "/" + year_next.toString())+"') class='Cabecalho_Calendario' title='Próximo Mês'>></a></td>";
    txt += "<td width='20%' style='text-align: center;'><a href=javascript:popdate('"+obj+"','"+div+"','"+tam+"','"+((mmonth+1).toString() +"/01/"+(ano+1).toString())+"') class='Cabecalho_Calendario' title='Próximo Ano'>>></a></td>";
    txt += "<td width='20%' style='text-align: right;'><a href=javascript:force_close('"+div+"') class='Cabecalho_Calendario' title='Fechar Calendário'><b>X</b></a></td></tr></table></td></tr>";
    txt += "<tr><td colspan='7' style='text-align: right;' bgcolor='#DBDBDB' class='mes'><a href=javascript:pop_year('"+obj+"','"+div+"','"+tam+"','" + (mmonth+1) + "') class='mes'>" + ano.toString() + "</a>";
    txt += " <a href=javascript:pop_month('"+obj+"','"+div+"','"+tam+"','" + ano + "') class='mes'>" + umonth[mmonth] + "</a> <div id='popd' style='position:absolute'></div></td></tr>";
    txt += "<tr bgcolor='#333333'><td width='14%' class='dia' style='text-align: center;'><b>Dom</b></td><td width='14%' class='dia' style='text-align: center;'><b>Seg</b></td><td width='14%' class='dia' style='text-align: center;'><b>Ter</b></td><td width='14%' class='dia' style='text-align: center;'><b>Qua</b></td><td width='14%' class='dia' style='text-align: center;'><b>Qui</b></td><td width='14%' class='dia' style='text-align: center;'><b>Sex<b></td><td width='14%' class='dia' style='text-align: center;'><b>Sab</b></td></tr>";
    today1 = new Date((mmonth+1).toString() +"/01/"+ano.toString());
    diainicio = today1.getDay () + 1;
    week = d = 1;
    start = false;

    for (n=1;n<= 42;n++)
    {
        if (week == 1)  txt += "<tr bgcolor='#efefff' style='text-align: center;'>";
        if (week==diainicio) {start = true;}
        if (d > days[mmonth]) {start=false;}
        if (start)
        {
            dat = new Date((mmonth+1).toString() + "/" + d + "/" + ano.toString());
            day_dat   = dat.toString().substr(0,10);
            day_today  = date_Form.toString().substr(0,10);
            year_dat  = dat.getFullYear ();
            year_today = date_Form.getFullYear ();
            colorcell = ((day_dat == day_today) && (year_dat == year_today) ? " bgcolor='#FFCC00' " : "" );
            var dCompl = '';
            if (d < 10) {
                dCompl = '0';
            }
            var mCompl = '';
            if ((mmonth + 1) < 10) {
                mCompl = '0';
            }
            txt += "<td"+colorcell+" style='text-align: center;'><a href=javascript:block('"+ dCompl + d + "/" + mCompl + (mmonth+1).toString() + "/" + ano.toString() +"','"+ obj +"','" + div +"') class='data'>"+ d.toString() + "</a></td>";
            d ++;
        }
        else
        {
            txt += "<td class='data' style='text-align: center;'> </td>";
        }
        week ++;
        if (week == 8)
        {
            week = 1; txt += "</tr>";}
    }
    txt += "</table>";
    div2 = document.getElementById(div);
    if(div2==null)
        div2 = eval (div);
    div2.innerHTML = txt;
}

// função para exibir a janela com os meses
function pop_month(obj, div, tam, ano)
{
    txt  = "<table bgcolor='#CCCCFF' border='0' width=80>";
    for (n = 0; n < 12; n++) { txt += "<tr><td style='text-align: center;'><a href=javascript:popdate('"+obj+"','"+div+"','"+tam+"','"+("01/" + (n+1).toString() + "/" + ano.toString())+"')>" + umonth[n] +"</a></td></tr>"; }
    txt += "</table>";
    popd.innerHTML = txt;
}

// função para exibir a janela com os anos
function pop_year(obj, div, tam, umonth)
{
    txt  = "<table bgcolor='#CCCCFF' border='0' width=160>";
    l = 1;
    for (n=1991; n<2012; n++)
    {  if (l == 1) txt += "<tr>";
        txt += "<td style='text-align: center;'><a href=javascript:popdate('"+obj+"','"+div+"','"+tam+"','"+(umonth.toString () +"/01/" + n) +"')>" + n + "</a></td>";
        l++;
        if (l == 4)
        {txt += "</tr>"; l = 1; }
    }
    txt += "</tr></table>";
    popd.innerHTML = txt;
}

// função para fechar o calendário
function force_close(div)
{
    div2 = document.getElementById(div);
    if(div2==null)
        div2 = eval(div);
    div2.innerHTML = '';
}

// função para fechar o calendário e setar a data no campo de data associado
function block(data, obj, div)
{
    force_close (div);
    obj2 = document.getElementById(obj);
    if(obj2==null)
        obj2 = eval(obj);
    obj2.value = data;
}


function popmonth_ext(obj,div,tam,ddd)
{
    var innerText;

    if (ddd)
    {
        day = "";
        mmonth = "";
        ano = "";
        c = 1;
        char = "";
        for (s=0;s<parseInt(ddd.length);s++)
        {
            char = ddd.substr(s,1);
            if (char == "/")
            {
                c++;
                s++;
                char = ddd.substr(s,1);
            }
            //if (c==1) day    += char
            if (c==1) mmonth += char;
            if (c==2) ano    += char;
        }
        ddd = mmonth + "/01/" + ano;
    }

    if(!ddd)  {
        today = new Date();
    } else {
        today = new Date(ddd);
    }
    date_Form = eval (obj);
    if (date_Form.value == "") { date_Form = new Date();} else {date_Form = new Date(date_Form.value);}

    ano = today.getFullYear();
    mmonth = today.getMonth ();
    day = "01";


    innerText =  "<table style='border:solid #333333; border-width:2; background-color:#F7F7F7; border-spacing: 0;' cellpadding='3' border='0'><tr><td><table style='width:100%;'>";
    innerText += "<tr><td colspan='3' style='text-align: right;'><a href=javascript:force_close_ext('"+div+"') class='Cabecalho_Calendario' title='Fechar Calendário'><b>X</b></a></td></tr>";
    innerText += "<tr><td><a href=javascript:popmonth_ext('"+obj+"','"+div+"','"+tam+"','"+((mmonth+1).toString() +"/"+(ano-1).toString())+"') class='Cabecalho_Calendario' title='Ano Anterior'> < </a> </td>";
    innerText += "<td style='text-align: center;'>  " + ano + " </td>";
    innerText += "<td style='text-align: right;'><a href=javascript:popmonth_ext('"+obj+"','"+div+"','"+tam+"','"+((mmonth+1).toString() +"/"+(ano+1).toString())+"') class='Cabecalho_Calendario' title='Próximo Ano'> > </a> </td>";
    innerText += "</tr></table></td></tr><tr><td><table style='width:100%;'>";
    innerText += "<tr style='background-color: #efefff;'><td style='text-align: center;'><a href=javascript:block_ext('01/" + ano.toString() +"','"+ obj +"','" + div +"') class='data'>Janeiro</a></td><td style='text-align: center;'><a href=javascript:block_ext('02/" + ano.toString() +"','"+ obj +"','" + div +"') class='data'>Fevereiro</a></td><td style='text-align: center;'><a href=javascript:block_ext('03/" + ano.toString() +"','"+ obj +"','" + div +"') class='data'>Março</a></td></tr>";
    innerText += "<tr style='background-color: #efefff;'><td style='text-align: center;'><a href=javascript:block_ext('04/" + ano.toString() +"','"+ obj +"','" + div +"') class='data'>Abril</a></td><td style='text-align: center;'><a href=javascript:block_ext('05/" + ano.toString() +"','"+ obj +"','" + div +"') class='data'>Maio</a></td><td style='text-align: center;'><a href=javascript:block_ext('06/" + ano.toString() +"','"+ obj +"','" + div +"') class='data'>Junho</a></td></tr>";
    innerText += "<tr style='background-color: #efefff;'><td style='text-align: center;'><a href=javascript:block_ext('07/" + ano.toString() +"','"+ obj +"','" + div +"') class='data'>Julho</a></td><td style='text-align: center;'><a href=javascript:block_ext('08/" + ano.toString() +"','"+ obj +"','" + div +"') class='data'>Agosto</a></td><td style='text-align: center;'><a href=javascript:block_ext('09/" + ano.toString() +"','"+ obj +"','" + div +"') class='data'>Setempbro</a></td></tr>";
    innerText += "<tr style='background-color: #efefff;'><td style='text-align: center;'><a href=javascript:block_ext('10/" + ano.toString() +"','"+ obj +"','" + div +"') class='data'>Outubro</a></td><td style='text-align: center;'><a href=javascript:block_ext('11/" + ano.toString() +"','"+ obj +"','" + div +"') class='data'>Novembro</a></td><td style='text-align: center;'><a href=javascript:block_ext('12/" + ano.toString() +"','"+ obj +"','" + div +"') class='data'>Dezembro</a></td></tr>";
    innerText += "</table></td></tr></table>";
    var div2 = document.getElementById(div);
    if (div2 == null) {
        div2 = eval (div);
    }
    div2.innerHTML = innerText;
}


function block_ext(data, obj, div)
{
    force_close (div);
    obj2 = document.getElementById(obj);
    if(obj2==null)
        obj2 = eval(obj);
    obj2.value = data;
}

function force_close_ext(div)
{ div2 = eval (div); div2.innerHTML = '';}


// **************************************************
// * Fim funcoes Calendario
// **************************************************
