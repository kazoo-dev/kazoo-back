package kazoo.service;

import kazoo.excepciones.UsuarioNoEncontradoException;
import kazoo.model.Partitura;
import kazoo.model.Usuario;
import kazoo.repository.PartituraRepository;
import kazoo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PartituraService {

    @Autowired
    private PartituraRepository partituraRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void guardarPartitura(String nombreDeUsuario, Partitura partitura) throws UsuarioNoEncontradoException {
        Usuario usuario = usuarioRepository.findByNombre(nombreDeUsuario).orElseThrow(UsuarioNoEncontradoException::new);
        usuario.agregarPartitura(partitura);
        partitura.setUsuario(usuario);
        partituraRepository.save(partitura);
        usuarioRepository.save(usuario);
    }
}
