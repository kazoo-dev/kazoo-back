package kazoo.service;

import kazoo.excepciones.usuario.DatosDeLogueoInvalidosException;
import kazoo.excepciones.usuario.DatosDeRegistracionInvalidosException;
import kazoo.model.Sesion;
import kazoo.model.Usuario;
import kazoo.repository.UsuarioRepository;
import kazoo.util.SHA512Hasher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    SHA512Hasher sha512Hasher;

    public void registrarUsuario(Usuario usuario){
        validarUsuarioYContrasenia(usuario);
        usuario.setSalt(sha512Hasher.generateSalt());
        usuario.setClave(sha512Hasher.hash(usuario.getClave(), usuario.getSalt()));
        validarQueNoExisteElUsuario(usuario);
        usuarioRepository.save(usuario);
    }

    private void validarUsuarioYContrasenia(Usuario usuario) {
        if(usuario.getNombre() == null || usuario.getClave()==null){
            throw new DatosDeRegistracionInvalidosException("Ni el nombre de usuario ni la contraseña pueden ser vacíos");
        }
    }

    private void validarQueNoExisteElUsuario(Usuario usuario) throws RuntimeException {
        obtenerUsuario(usuario)
                .ifPresent((u) -> {throw new DatosDeRegistracionInvalidosException("Ya existe un usuario con el nombre indicado");});
    }

    private Optional<Usuario> obtenerUsuario(Usuario usuario) {
        return usuarioRepository.findByNombre(usuario.getNombre());
    }

    public Sesion loguearUsuario(Usuario usuarioIngresado) {
        Usuario usuarioBuscado = usuarioRepository.findByNombre(usuarioIngresado.getNombre())
                .orElseThrow(() -> new DatosDeLogueoInvalidosException("No existe el usuario con el que se desea loguear"));
        Sesion sesion = verificarDatosIngresados(usuarioIngresado, usuarioBuscado);
        return sesion;
    }

    private Sesion verificarDatosIngresados(Usuario usuarioIngresado, Usuario usuarioBuscado) {
        Boolean validarCredenciales = sha512Hasher.checkPassword(usuarioBuscado.getClave(), usuarioIngresado.getClave(), usuarioBuscado.getSalt());
        if(!validarCredenciales){
            throw new DatosDeLogueoInvalidosException("Credenciales ingresadas incorrectas");
        }
        return new Sesion(usuarioBuscado.getNombre());
    }
}
