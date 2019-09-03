package kazoo.controller;

import kazoo.model.Sesion;
import kazoo.model.Usuario;
import kazoo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin
public class AutenticacionController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping(path = Endpoints.Autenticacion.REGISTRAR)
    public ResponseEntity crearUsuario(@RequestBody Usuario usuario){
        usuarioService.registrarUsuario(usuario);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(path = Endpoints.Autenticacion.LOGIN)
    public Sesion login(@RequestBody Usuario usuario){
        return usuarioService.loguearUsuario(usuario);
    }
}

