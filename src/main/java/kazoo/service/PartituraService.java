package kazoo.service;

import kazoo.excepciones.DatosDeLogueoInvalidosException;
import kazoo.model.Partitura;
import kazoo.model.Usuario;
import kazoo.repository.PartituraRepository;
import kazoo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PartituraService {

    @Autowired
    PartituraRepository partituraRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    public void crearPartitura(String usuarioNombre, Partitura partitura) {
        Usuario usuario = usuarioRepository.findByNombre(usuarioNombre).orElseThrow(() -> new DatosDeLogueoInvalidosException("No existe el usuario"));
        usuario.agregarPartitura(partitura);
        usuarioRepository.save(usuario);
    }
}
