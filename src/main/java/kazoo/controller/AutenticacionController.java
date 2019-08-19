package kazoo.controller;

import kazoo.model.Usuario;
import kazoo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = AutenticacionController.AutenticacionControllerBasePath)
public class AutenticacionController {

    static final String AutenticacionControllerBasePath = "/usuario";

    @Autowired
    private UsuarioService usuarioService;

    @RequestMapping(value = "/registrar", method = RequestMethod.POST)
    public ResponseEntity crearUsuario(@RequestBody Usuario usuario){
        usuarioService.registrarUsuario(usuario);
        return new ResponseEntity(HttpStatus.OK);
    }
}

