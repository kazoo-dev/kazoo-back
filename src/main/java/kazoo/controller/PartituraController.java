package kazoo.controller;

import kazoo.excepciones.UsuarioNoEncontradoException;
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
        try {
            partituraService.guardarPartitura(usuarioNombre, partitura);
            return new ResponseEntity(HttpStatus.OK);
        } catch (UsuarioNoEncontradoException ex) {
            return new ResponseEntity(ex.toString(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity("Error inesperado", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
