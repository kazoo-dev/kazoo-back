package kazoo.controller;

import kazoo.model.Partitura;
import kazoo.service.PartituraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class PartituraController {


    @Autowired
    PartituraService partituraService;

    @PostMapping(path = Endpoints.Partitura.PARTITURA_BASE)
    public void crearPartitura(@RequestHeader("usuario-nombre") String usuarioNombre, @RequestBody Partitura partitura) {
        partituraService.crearPartitura(usuarioNombre, partitura);
    }
}