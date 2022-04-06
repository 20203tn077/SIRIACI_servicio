import mx.edu.utez.SIRIACI_servicio.model.noVerificado.NoVerificadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class testasd {
    @Autowired
    NoVerificadoRepository noVerificadoRepository;

    @Transactional(readOnly = true)
    public String asd() {
        return noVerificadoRepository.findAll().get(0).getCodigo().toString();
    }
    public static void main(String[] args) {
        System.out.println(new testasd().asd());
    }
}
