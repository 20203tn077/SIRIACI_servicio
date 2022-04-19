package mx.edu.utez.SIRIACI_servicio.util;

import java.util.Optional;

public class Validador {
    // Expresiones regulares para validar cadenas
    private static final String REGEX_NOMBRE_VALIDO = "^[a-zA-Z\\xC0-\\uFFFF][a-zA-Z\\xC0-\\uFFFF-]+( [a-zA-Z\\xC0-\\uFFFF-]+)*$";
    private static final String REGEX_TELEFONO_VALIDO = "^\\d{10}$";
    private static final String REGEX_CADENA_VACIA = "^[\\s.\\-_]*$";
    private static final String REGEX_CORREO_VALIDO = "^[\\w-.]+@[\\w-.]+\\.[\\w.]+$";
    private static final String REGEX_CORREO_INSTITUCIONAL = "^[\\w-.]+@utez\\.edu\\.mx$";
    private static final String REGEX_CORREO_ESTUDIANTE = "^i?20\\d{2}3\\w{2}\\d{3}@utez.edu.mx$";

    // Límites
    private static final int USUARIO_NOMBRE_MAX = 64;
    private static final int USUARIO_APELLIDO_MAX = 32;
    private static final int USUARIO_CORREO_MAX = 64;
    private static final int USUARIO_TELEFONO = 10;
    private static final int USUARIO_CONTRASENA_MIN = 8;
    private static final int USUARIO_CONTRASENA_MAX = 64;
    private static final int USUARIO_CUATRIMESTRE_MAX = 11;
    private static final int INCIDENCIA_DESCRIPCION_MAX = 255;
    private static final int INCIDENCIA_COMENTARIO_MAX = 128;
    private static final int CAPSULA_TITULO_MAX = 128;

    // Coordenadas de puntos del polígono que abarca a la UTEZ en google maps
    private static final double[][] COORDENADAS_UTEZ = {
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

    public static Optional<String> validarNombreUsuario (String nombre) {
        if (nombre == null || nombre.matches(REGEX_CADENA_VACIA)) return Optional.of("Debes ingresar un nombre.");
        if (nombre.length() > USUARIO_NOMBRE_MAX) return Optional.of("Máximo " + USUARIO_NOMBRE_MAX + " caracteres.");
        if (!nombre.matches(REGEX_NOMBRE_VALIDO)) return Optional.of("Ingresa un nombre válido.");
        return Optional.empty();
    }
    public static Optional<String> validarApellido1Usuario (String apellido) {
        if (apellido == null || apellido.matches(REGEX_CADENA_VACIA)) return Optional.of("Debes ingresar un apellido.");
        if (apellido.length() > USUARIO_APELLIDO_MAX) return Optional.of("Máximo " + USUARIO_APELLIDO_MAX + " caracteres.");
        if (!apellido.matches(REGEX_NOMBRE_VALIDO)) return Optional.of("Ingresa un apellido válido.");
        return Optional.empty();
    }
    public static Optional<String> validarApellido2Usuario (String apellido) {
        if (apellido == null) return Optional.of("Debes ingresar un apellido");
        if (apellido.equals("")) return Optional.empty();
        if (apellido.matches(REGEX_CADENA_VACIA)) return Optional.of("Debes ingresar un apellido.");
        if (apellido.length() > USUARIO_APELLIDO_MAX) return Optional.of("Máximo " + USUARIO_APELLIDO_MAX + " caracteres.");
        if (!apellido.matches(REGEX_NOMBRE_VALIDO)) return Optional.of("Ingresa un apellido válido.");
        return Optional.empty();
    }
    public static Optional<String> validarCorreoUsuario (String correo) {
        if (correo == null || correo.matches(REGEX_CADENA_VACIA)) return Optional.of("Debes ingresar un correo electrónico.");
        if (correo.length() > USUARIO_CORREO_MAX) return Optional.of("Máximo " + USUARIO_CORREO_MAX + " caracteres.");
        if (!correo.matches(REGEX_CORREO_VALIDO)) return Optional.of("Ingresa un correo electrónico válido.");
        return Optional.empty();
    }
    public static Optional<String> validarTelefonoUsuario (String telefono) {
        if (telefono == null || telefono.matches(REGEX_CADENA_VACIA)) return Optional.of("Debes ingresar un número de teléfono.");
        if (telefono.length() != USUARIO_TELEFONO) return Optional.of("El número de teléfono debe tener " + USUARIO_TELEFONO + " dígitos.");
        if (!telefono.matches(REGEX_TELEFONO_VALIDO)) return Optional.of("Ingresa un número de teléfono válido.");
        return Optional.empty();
    }
    public static Optional<String> validarContrasenaUsuario (String contrasena) {
        if (contrasena == null || contrasena.matches(REGEX_CADENA_VACIA)) return Optional.of("Debes ingresar una contraseña.");
        if (contrasena.length() < USUARIO_CONTRASENA_MIN) return Optional.of("Mínimo " + USUARIO_CONTRASENA_MIN + " caracteres.");
        if (contrasena.length() > USUARIO_CONTRASENA_MAX) return Optional.of("Máximo " + USUARIO_CONTRASENA_MAX + " caracteres.");
        return Optional.empty();
    }
    public static Optional<String> validarCuatrimestreUsuario (Byte cuatrimestre) {
        if (cuatrimestre == null) return Optional.of("Debes ingresar un cuatrimestre.");
        if (!(cuatrimestre >= 1 && cuatrimestre <= USUARIO_CUATRIMESTRE_MAX)) return Optional.of("Ingresa un cuatrimestre válido (1-" + USUARIO_CUATRIMESTRE_MAX + ").");
        return Optional.empty();
    }
    public static Optional<String> validarGrupoUsuario (Character grupo) {
        if (grupo == null) return Optional.of("Debes ingresar un grupo.");
        if (!(grupo >= 'A' && grupo <= 'Z')) return Optional.of("Ingresa un grupo válido.");
        return Optional.empty();
    }

    public static boolean isCorreoEstudiante (String correo) {
        return correo.matches(REGEX_CORREO_ESTUDIANTE);
    }
    public static boolean isCorreoInstitucional (String correo) {
        return correo.matches(REGEX_CORREO_INSTITUCIONAL);
    }

    public static Optional<String> validarDescripcionIncidencia (String descripcion) {
        if (descripcion == null || descripcion.matches(REGEX_CADENA_VACIA)) return Optional.of("Debes ingresar una descripción.");
        if (descripcion.length() > INCIDENCIA_DESCRIPCION_MAX) return  Optional.of("Máximo " + INCIDENCIA_DESCRIPCION_MAX + " caracteres.");
        return Optional.empty();
    }

    public static Optional<String> validarComentarioIncidencia (String comentario) {
        if (comentario == null) return Optional.of("Debes ingresar un comentario.");
        if (comentario.equals("")) return Optional.empty();
        if (comentario.matches(REGEX_CADENA_VACIA)) return Optional.of("Debes ingresar un comentario.");
        if (comentario.length() > INCIDENCIA_COMENTARIO_MAX) return  Optional.of("Máximo " + INCIDENCIA_COMENTARIO_MAX + " caracteres.");
        return Optional.empty();
    }

    public static Optional<String> validarUbicacionIncidencia(Double latitud, Double longitud) {
        if (latitud == null || longitud == null) return Optional.of("Debes seleccionar una ubicación.");
        if (!isUbicacionDentroUtez(latitud, longitud)) return Optional.of("Selecciona una ubicación dentro de la UTEZ.");
        return Optional.empty();
    }

    public static Optional<String> validarTituloCapsula (String titulo) {
        if (titulo == null || titulo.matches(REGEX_CADENA_VACIA)) return Optional.of("Debes ingresar un título.");
        if (titulo.length() > CAPSULA_TITULO_MAX) return  Optional.of("Máximo " + CAPSULA_TITULO_MAX + " caracteres.");
        return Optional.empty();
    }

    public static Optional<String> validarContenidoCapsula (String contenido) {
        if (contenido == null || contenido.matches(REGEX_CADENA_VACIA)) return Optional.of("Debes ingresar algo como contenido.");
        return Optional.empty();
    }

    private static boolean isUbicacionDentroUtez(double latitud, double longitud) {
        int interseccionesNorte = 0;
        int interseccionesSur = 0;
        for (int i = 0; i < COORDENADAS_UTEZ.length; i++) {
            // Obtiene un par de puntos de una recta
            double[] punto1 = COORDENADAS_UTEZ[i];
            double[] punto2 = COORDENADAS_UTEZ[i + 1 < COORDENADAS_UTEZ.length ? i + 1 : 0];

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
