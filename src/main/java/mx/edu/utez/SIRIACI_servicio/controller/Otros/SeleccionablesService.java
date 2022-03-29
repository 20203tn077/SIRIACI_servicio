package mx.edu.utez.SIRIACI_servicio.controller.Otros;

import mx.edu.utez.SIRIACI_servicio.model.aspecto.AspectoRepository;
import mx.edu.utez.SIRIACI_servicio.model.carrera.CarreraRepository;
import mx.edu.utez.SIRIACI_servicio.model.division.DivisionRepository;
import mx.edu.utez.SIRIACI_servicio.model.estado.Estado;
import mx.edu.utez.SIRIACI_servicio.model.estado.EstadoRepository;
import mx.edu.utez.SIRIACI_servicio.model.importancia.Importancia;
import mx.edu.utez.SIRIACI_servicio.model.importancia.ImportanciaRepository;
import mx.edu.utez.SIRIACI_servicio.util.Mensaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    @Transactional(readOnly = true)
    public ResponseEntity<Mensaje> obtenerImportancias() {
        return new ResponseEntity<>(new Mensaje(false, "OK", null, importanciaRepository.findAll(Sort.by("id"))),HttpStatus.OK);
    }
    @Transactional(readOnly = true)
    public ResponseEntity<Mensaje> obtenerEstados() {
        List<Estado> estados = new ArrayList<>(estadoRepository.findAll(Sort.by("Id")));
        estados.remove(0);
        return new ResponseEntity<>(new Mensaje(false, "OK", null, estados),HttpStatus.OK);
    }
    @Transactional(readOnly = true)
    public ResponseEntity<Mensaje> obtenerAspectos() {
        return new ResponseEntity<>(new Mensaje(false, "OK", null, aspectoRepository.findAll()),HttpStatus.OK);
    }
    @Transactional(readOnly = true)
    public ResponseEntity<Mensaje> obtenerDivisiones() {
        return new ResponseEntity<>(new Mensaje(false, "OK", null, divisionRepository.findAll()),HttpStatus.OK);
    }
}
