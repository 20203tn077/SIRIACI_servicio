package mx.edu.utez.SIRIACI_servicio.util;

import java.time.LocalDateTime;

public class Formateador {
    public static String getFecha(LocalDateTime tiempo) {
        String mes = "";
        switch(tiempo.getMonth().getValue()) {
            case 0: mes = "enero";
                break;
            case 1: mes = "febrero";
                break;
            case 2: mes = "marzo";
                break;
            case 3: mes = "abril";
                break;
            case 4: mes = "mayo";
                break;
            case 5: mes = "junio";
                break;
            case 6: mes = "julio";
                break;
            case 7: mes = "agosto";
                break;
            case 8: mes = "septiembre";
                break;
            case 9: mes = "octubre";
                break;
            case 10: mes = "noviembre";
                break;
            case 11: mes = "diciembre";
                break;
        }
        return "" + tiempo.getDayOfMonth() + " de " + mes + " de " + tiempo.getYear();
    }
    public static String getHora (LocalDateTime tiempo) {
        return "" + tiempo.getHour() + ":" + tiempo.getMinute();
    }
    public static String getFechaYHora(LocalDateTime tiempo) {
        return getFecha(tiempo) + " a la" + (tiempo.getHour() != 1 ? "s" : "") + " " + getHora(tiempo);
    }
}
