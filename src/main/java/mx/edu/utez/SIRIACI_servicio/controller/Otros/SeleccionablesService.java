package mx.edu.utez.SIRIACI_servicio.controller.Otros;

import mx.edu.utez.SIRIACI_servicio.model.aspecto.AspectoRepository;
import mx.edu.utez.SIRIACI_servicio.model.carrera.CarreraRepository;
import mx.edu.utez.SIRIACI_servicio.model.division.DivisionRepository;
import mx.edu.utez.SIRIACI_servicio.model.estado.EstadoRepository;
import mx.edu.utez.SIRIACI_servicio.model.importancia.Importancia;
import mx.edu.utez.SIRIACI_servicio.model.importancia.ImportanciaRepository;
import mx.edu.utez.SIRIACI_servicio.util.Mensaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SeleccionablesService {
    @Autowired
    ImportanciaRepository importanciaRepository;
    @Autowired
    EstadoRepository estadoRepository;
    @Autowired
    AspectoRepository aspectoRepository;
    @Autowired
    DivisionRepository divisionRepository;
    @Autowired
    CarreraRepository carreraRepository;

    @Transactional(readOnly = true)
    public ResponseEntity<Mensaje> obtenerImportancias() {
        return new ResponseEntity<>(new Mensaje(false, "OK", null, importanciaRepository.findAll()),HttpStatus.OK);
    }
    @Transactional(readOnly = true)
    public ResponseEntity<Mensaje> obtenerEstados() {
        return new ResponseEntity<>(new Mensaje(false, "OK", null, estadoRepository.findAll()),HttpStatus.OK);
    }
    @Transactional(readOnly = true)
    public ResponseEntity<Mensaje> obtenerAspectos() {
        return new ResponseEntity<>(new Mensaje(false, "OK", null, aspectoRepository.findAll()),HttpStatus.OK);
    }
    @Transactional(readOnly = true)
    public ResponseEntity<Mensaje> obtenerDivisiones() {
        return new ResponseEntity<>(new Mensaje(false, "OK", null, divisionRepository.findAll()),HttpStatus.OK);
    }
    @Transactional(readOnly = true)
    public ResponseEntity<Mensaje> obtenerCarreras() {
        return new ResponseEntity<>(new Mensaje(false, "OK", null, carreraRepository.findAll()),HttpStatus.OK);
    }
}
