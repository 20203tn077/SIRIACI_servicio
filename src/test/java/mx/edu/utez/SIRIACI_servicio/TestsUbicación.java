package mx.edu.utez.SIRIACI_servicio;

import mx.edu.utez.SIRIACI_servicio.util.Validador;

import java.util.Scanner;

public class TestsUbicación {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        do {
            System.out.println("Latitud:");
            double longitud = scan.nextDouble();
            System.out.println("Longitud:");
            double latitud = scan.nextDouble();

            System.out.println((Validador.isUbicacionDentroUtez(longitud, latitud) ? "DENTRO" : "FUERA") + " DE LA UTEZ");
            System.out.println("¿Deseas continuar? [S/N]");
        } while (scan.next().toUpperCase().charAt(0) == 'S');
    }
}
