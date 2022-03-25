package mx.edu.utez.SIRIACI_servicio.util;

public class Validador {
    public static boolean isNombreValido(String valor) {return valor.matches("^[a-zA-Z\\xC0-\\uFFFF][a-zA-Z\\xC0-\\uFFFF-]+( [a-zA-Z\\xC0-\\uFFFF-]+)*$");}
    public static boolean isVacio(String valor) {return valor.matches("^[\\s.\\-_]*$");}
    public static boolean isCorreoValido(String correo) {
        return correo.matches("^[\\w-.]+@[\\w-.]+\\.[\\w.]+$");
    }
    public static boolean isCorreoInstitucional(String correo) {
        return correo.matches("^[\\w-.]+@utez\\.edu\\.mx$");
    }
    public static boolean isCorreoEstudiante(String correo) {return correo.matches("^i?20\\d{2}3\\w{2}\\d{3}@utez.edu.mx$");}

    public static boolean isLetraValida(char letra) {
        return letra >= 65 && letra <= 90;
    }

    public static boolean isDentroRango(String valor, int min, int max) {
        return valor.length() >= min && valor.length() <= max;
    }

    public static boolean isDentroRango(String valor, int max) {
        return valor.length() >= 1 && valor.length() <= max;
    }

    public static boolean isDentroRango(double valor, double min, double max) {
        return valor >= min && valor <= max;
    }

    public static boolean isDentroRango(double valor, double max) {
        return valor <= max;
    }

    public static boolean isUbicacionDentroUtez(double latitud, double longitud) {
        double[][] coordenadasUtez = {
                {18.848725, -99.202692},
                {18.852224, -99.202421},
                {18.853268, -99.200056},
                {18.852378, -99.199289},
                {18.851659, -99.199841},
                {18.851115, -99.199399},
                {18.850123, -99.199640},
                {18.849607, -99.199961},
                {18.849144, -99.199985},
                {18.849098, -99.200385},
                {18.848439, -99.200481}
        };
        int interseccionesNorte = 0;
        int interseccionesSur = 0;
        for (int i = 0; i < coordenadasUtez.length; i++) {
            // Obtiene un par de puntos de una recta
            double[] punto1 = coordenadasUtez[i];
            double[] punto2 = coordenadasUtez[i + 1 < coordenadasUtez.length ? i + 1 : 0];

            // Establece el rango de longitudes que abarca la recta
            double longitudMenor, longitudMayor;
            if (punto1[1] > punto2[1]) {
                longitudMayor = punto1[1];
                longitudMenor = punto2[1];
            } else {
                longitudMayor = punto2[1];
                longitudMenor = punto1[1];
            }

            // Evalúa si las coordenadas ingresadas corresponden a un punto dentro del rango
            if (longitud >= longitudMenor && longitud <= longitudMayor) {
                // Determina la latitud de la intersección
                double latitudInterseccion = ((punto2[0] - punto1[0]) / (punto2[1] - punto1[1])) * (longitud - punto1[1]) + punto1[0];

                // Evalúa si la intersección está al norte o al sur del punto
                if (latitudInterseccion > latitud) interseccionesNorte++;
                else if (latitudInterseccion < latitud) interseccionesSur++;
            }
        }

        // Determina si las coordenadas corresponden a un punto dentro de la figura a partir del número de intersecciones
        return (interseccionesNorte % 2 == 1 && interseccionesSur % 2 == 1);
    }
}
