package com.projeto.dsc.ultis;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EstacionamentoUltis {

    public static final long GRACE_MINUTES = 10L;
    public static final BigDecimal FIRST_HOUR = bd("8.00");
    public static final BigDecimal HALF_HOUR = bd("4.00");
    public static final BigDecimal DAILY_CAP = bd("45.00");
    public static final long MINUTES_PER_DAY = 24L * 60L;

    public static String gerarReivo() {
        var now = java.time.LocalDateTime.now();
        String recibo = now.toString().substring(0, 19);
        return recibo.replace("-", "").replace(":", "").replace("T", "-");
    }

    public static BigDecimal calcularCustos(Date dataEntrada, Date dataSaida) {
        long minutos = Duration.between(dataEntrada.toInstant(), dataSaida.toInstant()).toMinutes();
        if (minutos < 0) {
            throw new IllegalArgumentException("dataSaida não pode ser anterior a dataEntrada");
        }

        if (minutos <= GRACE_MINUTES) {
            return bd("0.00");
        }
        long diasCompletos = minutos / MINUTES_PER_DAY;
        long restoMinutos = minutos % MINUTES_PER_DAY;

        BigDecimal total = DAILY_CAP.multiply(BigDecimal.valueOf(diasCompletos));

        if (restoMinutos > 0) {
            BigDecimal custoResto = calcularBlocoResto(restoMinutos, FIRST_HOUR, HALF_HOUR);
            if (custoResto.compareTo(DAILY_CAP) > 0) {
                custoResto = DAILY_CAP;
            }
            total = total.add(custoResto);
        }

        return total.setScale(2, RoundingMode.HALF_UP);
    }

    private static BigDecimal calcularBlocoResto(long minutos, BigDecimal firstHour, BigDecimal halfHour) {
        if (minutos <= 60) {
            return firstHour;
        }
        long depoisDaPrimeiraHora = minutos - 60;
        long meiaHoras = (depoisDaPrimeiraHora + 29) / 30;
        return firstHour.add(halfHour.multiply(BigDecimal.valueOf(meiaHoras)));
    }

    private static BigDecimal bd(String v) {
        return new BigDecimal(v);
    }
}
