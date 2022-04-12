import mx.edu.utez.SIRIACI_servicio.model.noVerificado.NoVerificadoRepository;

import mx.edu.utez.SIRIACI_servicio.util.Formateador;
import org.testng.annotations.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Date;

public class testasd {
    @Test
    public static void pruebafecha() {
        LocalDateTime fecha = LocalDateTime.of(2020, Month.JANUARY, 3, 1, 22);
        System.out.println(Formateador.getFecha(fecha) + " a las " + Formateador.getHora(fecha));
        System.out.println(Formateador.getFechaYHora(fecha));
        System.out.println(Formateador.getFechaYHora(LocalDateTime.now()));
    }

}
