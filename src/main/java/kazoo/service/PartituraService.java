package kazoo.service;

import kazoo.controller.to.DetallePartitura;
import kazoo.controller.to.PartituraListada;
import kazoo.excepciones.partituras.PartituraNoEncontradaException;
import kazoo.excepciones.usuario.DatosDeLogueoInvalidosException;
import kazoo.model.Partitura;
import kazoo.model.Usuario;
import kazoo.repository.PartituraRepository;
import kazoo.repository.UsuarioRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class PartituraService {

    @Autowired
    PartituraRepository partituraRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    public void crearPartitura(String usuarioNombre, Partitura partitura) {
        Usuario usuario = getUsuario(usuarioNombre);
        usuario.agregarPartitura(partitura);
        usuarioRepository.save(usuario);
    }

    public List<PartituraListada> getPartiturasPara(String nombreUsuario) {
        Usuario usuario = getUsuario(nombreUsuario);
        List<Partitura> partiturasDeUsuario = partituraRepository.findByUsuario(usuario).orElse(new ArrayList<>());
        return partiturasDeUsuario.stream().map(PartituraListada::new).collect(toList());
    }

    public DetallePartitura getPartitura(String nombreUsuario, String partituraId) {

        Partitura partitura = validarPartitura(nombreUsuario,Long.parseLong(partituraId));
        return new DetallePartitura(partitura);
    }

    private Partitura validarPartitura(String nombreUsuario, Long id) {
        Partitura partitura = partituraRepository.findById(id)
                .orElseThrow(() -> new PartituraNoEncontradaException("No existe la partitura seleccionada"));
        Usuario usuario = getUsuario(nombreUsuario);

        if(!partitura.getUsuario().equals(usuario)){
            throw new PartituraNoEncontradaException("No existe la partitura par el usuario solicitado");
        }

        return partitura;
    }

    private Usuario getUsuario(String nombreUsuario) {
        return usuarioRepository.findByNombre(nombreUsuario)
                .orElseThrow(() -> new DatosDeLogueoInvalidosException("No existe el usuario"));
    }

    public void guardarPartitura(String nombreUsuario, Partitura partituraRecibida) {
        Partitura partitura = validarPartitura(nombreUsuario, partituraRecibida.getPartitura_id());
        //Copia todas las propiedades modificadas de la partitura menos el usuario que llega con null
        BeanUtils.copyProperties(partituraRecibida, partitura, "usuario");
        partituraRepository.save(partitura);
    }
}
