package mx.edu.utez.SIRIACI_servicio;

import mx.edu.utez.SIRIACI_servicio.model.bloqueo.BloqueoRepository;
import mx.edu.utez.SIRIACI_servicio.model.usuario.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SiriaciServicioApplicationTests {

	@Autowired
	BloqueoRepository bloqueoRepository;
	@Test
	void contextLoads() {
		boolean flag = bloqueoRepository.existsUsuarioByTime(1);
		System.out.println(flag);
	}

}
