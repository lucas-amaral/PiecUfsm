package br.ufsm.inf.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Lucas on 30/10/2014.
 */
public class FuncoesDatas {
    public static SimpleDateFormat sdf_sql = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat[] sdfs = new SimpleDateFormat[] {
            new SimpleDateFormat("yyyy"),
            new SimpleDateFormat("MM/yyyy"),
            new SimpleDateFormat("dd/MM/yyyy"),
            new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
    };

    private final static int TIPO_CALENDARIO_ANO = 0;
    private final static int TIPO_CALENDARIO_MES_ANO = 1;
    private final static int TIPO_CALENDARIO_DIA_MES_ANO = 2;
    private final static int TIPO_CALENDARIO_DIA_MES_ANO_HORA_MINUTO_SEGUNDO = 3;
    private final static int TIPO_CALENDARIO_INSTANTANEO = -2;

    public static Date[] populaIntervaloData(String intervalo) {
        Date intervalo1 = null, intervalo2 = null;
        int tipoCalendario1 = TIPO_CALENDARIO_DIA_MES_ANO, tipoCalendario2 = TIPO_CALENDARIO_DIA_MES_ANO;
        try {
            intervalo = intervalo.trim();
            if (intervalo.endsWith("/?")) {
                intervalo = intervalo.replace("?", String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
            }
            if (intervalo.contains(":")) {
                tipoCalendario1 = tipoCalendario2 = TIPO_CALENDARIO_DIA_MES_ANO_HORA_MINUTO_SEGUNDO;
            } else if (intervalo.toLowerCase().contains("agora")) {
                intervalo2 = intervalo1 = new Date();
                tipoCalendario1 = tipoCalendario2 = TIPO_CALENDARIO_INSTANTANEO;
            } else if (intervalo.toLowerCase().contains("ontem")) {
                intervalo2 = intervalo1 = getOntem(new Date());
            } else if (intervalo.contains("hoje")) {
                intervalo2 = intervalo1 = new Date();
            } else if (intervalo.toLowerCase().contains("amanhã")) {
                intervalo2 = intervalo1 = getAmanha(new Date());
            } else if (intervalo.toLowerCase().contains("mês atual")) {
                intervalo1 = getPrimeiroDiaMes(new Date());
                intervalo2 = getUltimoDiaMes(new Date());
            } else if (intervalo.toLowerCase().contains("-")) {
                String strIntervalo1 = intervalo.split("-")[0];
                String strIntervalo2 = intervalo.split("-")[1];
                tipoCalendario1 = strIntervalo1.split("/").length - 1;
                tipoCalendario2 = strIntervalo2.split("/").length - 1;
                if (intervalo.indexOf("-") == 0) {
                    intervalo2 = sdfs[tipoCalendario2].parse(strIntervalo2);
                } else if (intervalo.indexOf("-") == (intervalo.length() - 1)) {
                    intervalo1 = sdfs[tipoCalendario1].parse(strIntervalo1);
                } else if (intervalo.split("-").length == 2) {
                    try {
                        intervalo1 = sdfs[tipoCalendario1].parse(strIntervalo1);
                        intervalo2 = sdfs[tipoCalendario2].parse(strIntervalo2);
                    } catch (Exception e) { /**/ }
                }
            } else {
                tipoCalendario1 = tipoCalendario2 = intervalo.split("/").length - 1;
                intervalo1 = intervalo2 = sdfs[tipoCalendario1].parse(intervalo);
            }
            if (intervalo1.after(intervalo2)) {
                Date intervaloTroca = intervalo1;
                intervalo1 = intervalo2;
                intervalo2 = intervaloTroca;
            }
        } catch (Exception e) { /**/ }
        if (intervalo1 != null) {
            if (tipoCalendario1 == TIPO_CALENDARIO_ANO) {
                intervalo1 = getPrimeiroDiaAno(intervalo1);
            } else if (tipoCalendario1 == TIPO_CALENDARIO_MES_ANO) {
                intervalo1 = getPrimeiroDiaMes(intervalo1);
            } else if (tipoCalendario1 == TIPO_CALENDARIO_DIA_MES_ANO) {
                intervalo1 = getPrimeiraHora(intervalo1);
            }
        }
        if (intervalo2 != null) {
            if (tipoCalendario2 == TIPO_CALENDARIO_ANO) {
                intervalo2 = getUltimoDiaAno(intervalo2);
            } else if (tipoCalendario2 == TIPO_CALENDARIO_MES_ANO) {
                intervalo2 = getUltimoDiaMes(intervalo2);
            } else if (tipoCalendario2 == TIPO_CALENDARIO_DIA_MES_ANO) {
                intervalo2 = getUltimaHora(intervalo2);
            }
        }
        return new Date[]{intervalo1, intervalo2};
    }

    public static Date getOntem(Date data) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        calendar.add(Calendar.DATE, -1);
        return getUltimaHora(calendar.getTime());
    }

    public static Date getAmanha(Date data) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        calendar.add(Calendar.DATE, 1);
        return getUltimaHora(calendar.getTime());
    }

    static public Date getPrimeiroDiaAno(Date data) {
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(data);
        calendario.set(Calendar.MONTH, Calendar.JANUARY);
        return getPrimeiroDiaMes(calendario.getTime());
    }

    static public Date getUltimoDiaAno(Date data) {
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(data);
        calendario.set(Calendar.MONTH, Calendar.DECEMBER);
        return getUltimoDiaMes(calendario.getTime());
    }

    static public Date getPrimeiroDiaMes(Date data) {
        Calendar primeiro = Calendar.getInstance();
        primeiro.setTime(data);
        primeiro.set(Calendar.DAY_OF_MONTH, 1);
        return getPrimeiraHora(primeiro.getTime());
    }

    static public Date getUltimoDiaMes(Date data) {
        Calendar ultimoDia = Calendar.getInstance();
        ultimoDia.setTime(data);
        ultimoDia.set(Calendar.DAY_OF_MONTH, ultimoDia.getActualMaximum(Calendar.DAY_OF_MONTH));
        return getUltimaHora(ultimoDia.getTime());
    }

    public static Date getPrimeiraHora(Date data) {
        Calendar primeiraHora = Calendar.getInstance();
        primeiraHora.setTime(data);
        primeiraHora.set(Calendar.HOUR_OF_DAY, 0);
        return getPrimeiroMinuto(primeiraHora.getTime());
    }


    public static  Date getUltimaHora(Date data) {
        Calendar ultimaHora = Calendar.getInstance();
        ultimaHora.setTime(data);
        ultimaHora.set(Calendar.HOUR_OF_DAY, 23);
        return getUltimoMinuto(ultimaHora.getTime());
    }

    static public Date getPrimeiroMinuto(Date data) {
        Calendar primeiroMinuto = Calendar.getInstance();
        primeiroMinuto.setTime(data);
        primeiroMinuto.set(Calendar.MINUTE, 0);
        primeiroMinuto.set(Calendar.SECOND, 0);
        primeiroMinuto.set(Calendar.MILLISECOND, 0);
        return primeiroMinuto.getTime();
    }

    static public Date getUltimoMinuto(Date data) {
        Calendar ultimoMinuto = Calendar.getInstance();
        ultimoMinuto.setTime(data);
        ultimoMinuto.set(Calendar.MINUTE, 59);
        ultimoMinuto.set(Calendar.SECOND, 59);
        ultimoMinuto.set(Calendar.MILLISECOND, 999);
        return ultimoMinuto.getTime();
    }
}
