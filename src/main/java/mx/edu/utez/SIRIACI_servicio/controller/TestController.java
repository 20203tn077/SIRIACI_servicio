package mx.edu.utez.SIRIACI_servicio.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = {"*"})
public class TestController {
    @GetMapping("/")
    public String asd(){
        return "hola";
    }
}
