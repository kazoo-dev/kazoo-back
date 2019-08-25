package kazoo.service;

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

    public void registrarUsuario(Usuario usuario){
        SHA512Hasher sha512Hasher = new SHA512Hasher();
        usuario.setSalt(sha512Hasher.generateSalt());
        usuario.setClave(sha512Hasher.hash(usuario.getClave(), usuario.getSalt()));
        validarQueNoExisteElUsuario(usuario);
        usuarioRepository.save(usuario);
    }

    private void validarQueNoExisteElUsuario(Usuario usuario) throws RuntimeException {
        obtenerUsuario(usuario)
                .ifPresent((u) -> {throw new RuntimeException("Ya existe un usuario con el nombre indicado");});
    }

    private Optional<Usuario> obtenerUsuario(Usuario usuario) {
        return usuarioRepository.findByNombre(usuario.getNombre());
    }
}
