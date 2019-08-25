package kazoo.controller;

import kazoo.model.Usuario;
import kazoo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class AutenticacionController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping(path = Endpoints.Autenticacion.REGISTRAR)
    public ResponseEntity crearUsuario(@RequestBody Usuario usuario){
        usuarioService.registrarUsuario(usuario);
        return new ResponseEntity(HttpStatus.OK);
    }
}

