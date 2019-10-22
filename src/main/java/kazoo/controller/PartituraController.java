package kazoo.controller;

import kazoo.controller.to.DetallePartitura;
import kazoo.controller.to.PartituraListada;
import kazoo.model.Partitura;
import kazoo.service.PartituraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class PartituraController {


    @Autowired
    PartituraService partituraService;

    @PostMapping(path = Endpoints.Partitura.PARTITURA_BASE)
    public void crearPartitura(@RequestHeader("usuario-nombre") String usuarioNombre, @RequestBody Partitura partitura) {
        partituraService.crearPartitura(usuarioNombre, partitura);
    }

    @GetMapping(path = Endpoints.Partitura.PARTITURA_BASE)
    public List<PartituraListada> getPartiturasPara(@RequestHeader("usuario-nombre") String usuarioNombre) {
        return partituraService.getPartiturasPara(usuarioNombre);
    }

    @GetMapping(path = Endpoints.Partitura.PARTITURA)
    public DetallePartitura getPartitura(@RequestHeader(value = "usuario-nombre", required = false) String nombreUsuario, @PathVariable String id) {
        return partituraService.getPartitura(nombreUsuario, id);
    }

    @PutMapping(path = Endpoints.Partitura.PARTITURA_BASE)
    public void guardarPartitura(@RequestHeader("usuario-nombre") String nombreUsuario, @RequestBody Partitura partitura){
        partituraService.guardarPartitura(nombreUsuario, partitura);
    }

    //Esto no es restful ni en pedo, se aceptan sugerencias
    @PostMapping(path = Endpoints.Partitura.PUBLICAR)
    public void publicarPartitura(@RequestHeader("usuario-nombre") String nombreUsuario, @RequestParam Long partituraId) {
        partituraService.marcarPartituraComoPublica(nombreUsuario, partituraId);
    }

    @DeleteMapping(path = Endpoints.Partitura.PARTITURA)
    public void publicarPartitura(@RequestHeader("usuario-nombre") String nombreUsuario, @PathVariable String id) {
        partituraService.eliminarPartitura(nombreUsuario, id);
    }
}