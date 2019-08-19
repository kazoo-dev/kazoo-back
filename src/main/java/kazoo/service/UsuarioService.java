package kazoo.service;

import kazoo.model.Usuario;
import kazoo.repository.UsuarioRepository;
import kazoo.util.SHA512Hasher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    public void registrarUsuario(Usuario usuario){
        SHA512Hasher sha512Hasher = new SHA512Hasher();
        usuario.setSalt(sha512Hasher.generateSalt());
        usuario.setClave(sha512Hasher.hash(usuario.getClave(), usuario.getSalt()));

        usuarioRepository.save(usuario);
    }

}
