package mx.edu.utez.SIRIACI_servicio.controller.Capsulas;

import mx.edu.utez.SIRIACI_servicio.controller.Usuarios.UsuarioController;
import mx.edu.utez.SIRIACI_servicio.model.capsula.Capsula;
import mx.edu.utez.SIRIACI_servicio.model.capsula.CapsulaRepository;
import mx.edu.utez.SIRIACI_servicio.util.Mensaje;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CapsulaService {
    @Autowired
    CapsulaRepository capsulaRepository;

    // 3.1 Consultar cápsulas informativas
    @Transactional(readOnly = true)
    public ResponseEntity<Mensaje> obtenerCapsulas(Pageable pageable) {
        return new ResponseEntity<>(new Mensaje(false, null, null, capsulaRepository.findAllByActivoIsTrue(pageable)), HttpStatus.OK);
    }
    @Transactional(readOnly = true)
    public ResponseEntity<Mensaje> obtenerCapsulas(Pageable pageable, String filtro) {
        return new ResponseEntity<>(new Mensaje(false, "OK", null, capsulaRepository.findAllByActivoIsTrueAndTituloContains(filtro, pageable)), HttpStatus.OK);
    }

    // 3.2 Consultar cápsula informativa
    /*@Transactional(readOnly = true)
    public ResponseEntity<Mensaje> obtenerCapsula(long id) {
        Optional<Capsula> capsula = capsulaRepository.findById(id);
        if (capsula.isEmpty()) return new ResponseEntity<>(new Mensaje(true, "Cápsula inexistente"))
    }
    @Transactional(readOnly = true)
    public ResponseEntity<Mensaje> obtenerCapsulaAdministrador(long id) {

    }*/

    // 3.3 Registrar cápsula informativa
//    @Transactional(rollbackFor = {SQLException.class})
//    public ResponseEntity<Mensaje> registrarCapsula() {
//
//    }

    // 3.4 Consultar cápsulas informativas realizadas
    @Transactional(readOnly = true)
    public ResponseEntity<Mensaje> obtenerCapsulasRealizadas(long id, Pageable pageable) {
        return new ResponseEntity<>(new Mensaje(false, "OK", null, capsulaRepository.findAllByActivoIsTrueAndUsuario_Id(id, pageable)), HttpStatus.OK);
    }
    @Transactional(readOnly = true)
    public ResponseEntity<Mensaje> obtenerCapsulasRealizadas(long id, Pageable pageable, String filtro) {
        return new ResponseEntity<>(new Mensaje(false, "OK", null, capsulaRepository.findAllByActivoIsTrueAndUsuario_IdAndTituloContains(id, filtro, pageable)), HttpStatus.OK);
    }
    @Transactional(readOnly = true)
    public ResponseEntity<Mensaje> obtenerCapsulasRealizadasAdministrador(Pageable pageable) {
        return new ResponseEntity<>(new Mensaje(false, "OK", null, capsulaRepository.findAll(pageable)), HttpStatus.OK);
    }
    @Transactional(readOnly = true)
    public ResponseEntity<Mensaje> obtenerCapsulasRealizadasAdministrador(Pageable pageable, String filtro) {
        return new ResponseEntity<>(new Mensaje(false, "OK", null, capsulaRepository.findAllByTituloContains(filtro, pageable)), HttpStatus.OK);
    }

    // 3.5 Modificar cápsula informativa
//    @Transactional(rollbackFor = {SQLException.class})
//    public ResponseEntity<Mensaje> modificarCapsula() {
//
//    }
//    @Transactional(rollbackFor = {SQLException.class})
//    public ResponseEntity<Mensaje> modificarCapsulaAdministrador() {
//
//    }

    // 3.6 Eliminar cápsula informativa
//    @Transactional(rollbackFor = {SQLException.class})
//    public ResponseEntity<Mensaje> eliminarCapsula() {
//
//    }
//    @Transactional(rollbackFor = {SQLException.class})
//    public ResponseEntity<Mensaje> eliminarCapsulaAdministrador() {
//
//    }
}
