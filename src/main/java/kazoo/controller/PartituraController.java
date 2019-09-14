package kazoo.controller;

import kazoo.model.Partitura;
import kazoo.service.PartituraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class PartituraController {

    @Autowired
    private PartituraService partituraService;

    @PostMapping(path = Endpoints.Partitura.PARTITURA_BASE)
    public ResponseEntity guardarPartitura(@RequestHeader("usuario-nombre") String usuarioNombre, @RequestBody Partitura partitura) {
        partituraService.guardarPartitura(usuarioNombre, partitura);
        return new ResponseEntity(HttpStatus.OK);
    }
}
